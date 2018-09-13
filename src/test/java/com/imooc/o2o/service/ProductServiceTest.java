package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import org.junit.Test;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author xiaolei hu
 * @date 2018/9/12 22:12
 **/
public class ProductServiceTest extends BaseTest {

    @Test
    public void addProduct() {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(1L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品1描述");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());

        CommonsMultipartFile commonsMultipartFile = null;
        //ImageHolder thumbnail = new ImageHolder();
    }
}