package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author xiaolei hu
 * @date 2018/9/8 16:57
 **/
public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void queryShopCategory() {
        // 查询参数为 null ,查找出所有的父类商铺类别
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(null);
        System.out.println("shopCategoryList" + shopCategoryList.toString());
        //assertEquals(2, shopCategoryList.size());
        //ShopCategory testCategory = new ShopCategory();
        //ShopCategory parentCateory = new ShopCategory();
        //parentCateory.setShopCategoryId(1L);
        //testCategory.setParentId(1L);
        //shopCategoryList = shopCategoryDao.queryShopCategory(testCategory);
        //System.out.println("shopCategoryList" + shopCategoryList.get(0).getShopCategoryName());
        //assertEquals(1, shopCategoryList.size());
    }
}