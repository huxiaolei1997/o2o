package com.imooc.o2o.web.frontend;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopListController {
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private ShopService shopService;

    /**
     * 返回商品列表页里的 ShopCategory 列表（二级或者一级），一级区域信息列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopspageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 试着从前端请求中获取 parentId
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        if (parentId != -1) {
            // 如果 parentId 存在，则取出该一级 ShopCategory 下的二级 ShopCategory 列表
            try {
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parentCategory = new ShopCategory();
                parentCategory.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parentCategory);

                shopCategoryList = shopCategoryService
                        .getShopCategoryList(shopCategoryCondition);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            try {
                // 如果 parentId 不存在，则取出所有一级 ShopCategory（用户在首页选择的是全部商店列表）
                shopCategoryList = shopCategoryService
                        .getShopCategoryList(null);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        }
        modelMap.put("shopCategoryList", shopCategoryList);
        List<Area> areaList = null;
        try {
            // 获取区域信息列表
            areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }

    /**
     * 获取指定查询条件下的列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshops", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if ((pageIndex > -1) && (pageSize > -1)) {
            // 试着获取一级类别 id
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            // 试着获取特定二级类别 Id
            long shopCategoryId = HttpServletRequestUtil.getLong(request,
                    "shopCategoryId");
            // 试着获取区域 id
            int areaId = HttpServletRequestUtil.getInt(request, "areaId");
            // 试着获取模糊查询的名字
            String shopName = HttpServletRequestUtil.getString(request,
                    "shopName");
            // 获取组合之后的查询条件
            Shop shopCondition = compactShopCondition(parentId,
                    shopCategoryId, areaId, shopName);
            // 获取组合之后的查询条件
            ShopExecution se = shopService.getShopList(shopCondition,
                    pageIndex, pageSize);
            modelMap.put("shopList", se.getShopList());
            // 该分类下的 店铺总数
            modelMap.put("count", se.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex");
        }

        return modelMap;
    }

    private Shop compactShopCondition(long parentId,
                                      long shopCategoryId, int areaId, String shopName) {
        Shop shopCondition = new Shop();
        if (parentId != -1L) {
            // 查询某个一级 ShopCategory 下面所有二级 ShopCategory 里面的店铺
            ShopCategory parentCategory = new ShopCategory();
            ShopCategory childCategory = new ShopCategory();
            parentCategory.setShopCategoryId(parentId);
            childCategory.setParent(parentCategory);
            shopCondition.setShopCategory(childCategory);
        }
        if (shopCategoryId != -1L) {
            // 查询某个二级 ShopCategory 下面的店铺列表
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if (areaId != -1L) {
            // 查询位于某个区域 Id 下面的店铺列表
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }

        if (shopName != null) {
            // 查询名字里包含 shopName 的店铺列表
            shopCondition.setShopName(shopName);
        }
        // 前端展示的店铺都是审核成功的店铺
        shopCondition.setEnableStatus(1);
        return shopCondition;
    }
}
