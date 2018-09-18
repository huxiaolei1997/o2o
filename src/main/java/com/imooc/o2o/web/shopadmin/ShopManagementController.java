package com.imooc.o2o.web.shopadmin;

import com.alibaba.fastjson.JSON;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaolei hu
 * @date 2018/9/8 14:41
 **/
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId <= 0) {
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if (currentShopObj == null) {
                modelMap.put("redirect", true);
                modelMap.put("url", "/o2o/shopadmin/shoplist");
            } else {
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }
        } else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop", currentShop);
            modelMap.put("redirect", false);
        }
        return modelMap;
    }

    @RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        PersonInfo user = new PersonInfo();
//        user.setUserId(1L);
//        user.setName("test");
        //request.getSession().setAttribute("user", user);
        user = (PersonInfo) request.getSession().getAttribute("user");
        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
            // 列出店铺成功之后，将店铺放入 session 中作为权限验证依据，即该账号只能操作他自己的店铺
            request.getSession().setAttribute("shopList", se.getShopList());
            modelMap.put("success", true);
            modelMap.put("shopList", se.getShopList());
            modelMap.put("user", user);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId > -1) {
            try {
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }


    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList;
        List<Area> areaList;
        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "registershop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        //ObjectMapper mapper = new ObjectMapper();
        Shop shop;
        try {
            //shop = mapper.readValue(shopStr, Shop.class);
            shop = JSON.parseObject(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().
                getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传的图片不能为空");
            return modelMap;
        }

        if (shop != null && shopImg != null) {
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
            shop.setOwner(owner);
            ShopExecution shopExecution;
            try {
                shopExecution = shopService.addShop(shop, shopImg);
                if (shopExecution.getState() == ShopStateEnum.CHECK.getState()) {
                    modelMap.put("success", true);
                    // 该用户可以操作的店铺列表
                    List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                    if (shopList == null || shopList.size() == 0) {
                        shopList = new ArrayList<>();
                    }
                    shopList.add(shopExecution.getShop());
                    request.getSession().setAttribute("shopList", shopList);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", shopExecution.getStateInfo());
                }
            } catch (ShopOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息");
            return modelMap;
        }
    }

    @RequestMapping(value = "modifyshop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        //ObjectMapper mapper = new ObjectMapper();
        Shop shop;
        try {
            //shop = mapper.readValue(shopStr, Shop.class);
            shop = JSON.parseObject(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().
                getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }
        // 修改店铺信息
        if (shop != null && shop.getShopId() != null) {
            PersonInfo owner = new PersonInfo();
            owner.setUserId(1L);
            shop.setOwner(owner);
            ShopExecution shopExecution;
            try {
                if (shopImg == null) {
                    shopExecution = shopService.modifyShop(shop, null);
                } else {
                    shopExecution = shopService.modifyShop(shop, shopImg);
                }
                if (shopExecution.getState() == ShopStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", shopExecution.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺Id");
            return modelMap;
        }
    }

    private static void inputStreamToFile(InputStream inputStream, File file) {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            throw new RuntimeException("调用inputStreamToFile产生异常：" + e.getMessage());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("inputStreamToFile关闭io产生异常: " + e.getMessage());
            }
        }
    }
}
