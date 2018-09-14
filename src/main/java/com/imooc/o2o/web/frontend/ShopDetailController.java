package com.imooc.o2o.web.frontend;

import com.imooc.o2o.dto.Pager;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ShopDetailController
 *
 * @create 2018-09-14 11:04
 * @copyright huxiaolei1997@gmail.com
 */
@Controller
@RequestMapping(value = "/frontend")
public class ShopDetailController {
    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 获取店铺信息以及该店铺列表下面的商品类别列表
     */
    @RequestMapping(value = "listshopdetailpageinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listShopDetailPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 获取前台传过来的 shopId
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        Shop shop = null;
        List<ProductCategory> productCategoryList;
        if (shopId != -1) {
            // 获取店铺 Id 为shopId 的店铺信息
            shop = shopService.getByShopId(shopId);
            // 获取店铺下面的商品类别列表
            productCategoryList = productCategoryService.getProductCategoryList(shopId);
            modelMap.put("shop", shop);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    /**
     * 根据查询条件分页列出该店铺下面的所有商品
     */
    @RequestMapping(value = "listproductsbyshop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> listProductsByShop(@RequestBody Pager<Product> productCondition) {
        Map<String, Object> modelMap = new HashMap<>();
//        // 获取页码
//        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
//        // 获取一页需要显示的条数
//        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
//        // 获取店铺 Id
//        long shopId = HttpServletRequestUtil.getLong(request, "shopId");

        // 获取页码
        Integer pageIndex = productCondition.getPageIndex();
        // 获取一页需要显示的条数
        Integer pageSize = productCondition.getPageSize();
        // 获取店铺 id
        Long shopId = productCondition.getDataCondition().getShop().getShopId();

        // 空值判断
        if ((pageIndex > -1) && (pageSize > -1) && (shopId > -1)) {
//            // 尝试获取商品类别 Id
//            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            //Long productCategoryId = productCondition.getDataCondition().getProductCategory().getProductCategoryId();
//            // 尝试获取模糊查找的商品名
//            String productName = HttpServletRequestUtil.getString(request, "productName");
            //String productName = productCondition.getDataCondition().getProductCategory().getProductCategoryName();
            // 组合查询条件
            //Product productCondition2 = compactProductCondition(shopId, productCategoryId, productName);
            // 根据传入的查询条件以及分页信息返回相应的商品列表以及总数
            ProductExecution pe = productService.getProductList(productCondition.getDataCondition(), pageIndex, pageSize);

            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    /**
     * 组合查询条件，并将条件封装到 ProductCondition 对象里返回（这里改成 前端发送 json 数据到后台，所以注释掉这个方法）
     *
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
//    private Product compactProductCondition(long shopId, Long productCategoryId, String productName) {
//        Product productCondition = new Product();
//        Shop shop = new Shop();
//        shop.setShopId(shopId);
//        productCondition.setShop(shop);
//        if (productCategoryId != -1L) {
//            // 查询某个商品类别下面的商品列表
//            ProductCategory productCategory = new ProductCategory();
//            productCategory.setProductCategoryId(productCategoryId);
//            productCondition.setProductCategory(productCategory);
//        }
//        if (productName != null) {
//            // 查询名字里包含 productName 的店铺列表
//            productCondition.setProductName(productName);
//        }
//        // 只允许选出状态为上架的商品
//        productCondition.setEnableStatus(1);
//        return productCondition;
//    }

}
