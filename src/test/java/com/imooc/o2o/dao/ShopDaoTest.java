package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service.ShopService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author xiaolei hu
 * @date 2018/9/8 9:52
 **/
public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;

    @Autowired
    private ShopService shopService;

    @Test
    public void testGetShopList() {
        Shop shopCondition = new Shop();
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(2L);
        shopCondition.setShopCategory(shopCategory);
        ShopExecution se = shopService.getShopList(shopCondition, 0 ,5);
        System.out.println(se.getShopList().toString());
    }
    @Test
    @Ignore
    public void testQueryShopListAndCount() {
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(2L);
        shopCondition.setShopCategory(shopCategory);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
        int shopListCount = shopDao.queryShopCount(shopCondition);
        System.out.println(shopList.size() + ", " + shopListCount);
    }

    @Test
    @Ignore
    public void testInsertShop() {
        Shop shop = new Shop();
        //PersonInfo personInfo = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(1L);
        shop.setOwner(personInfo);
        //shop.setOwnerId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setShopCategory(shopCategory);
        shop.setArea(area);
        shop.setShopName("测试的店铺666");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1, effectedNum);
    }

    @Test
    @Ignore
    public void updateShop() {
        Shop shop = new Shop();
        //PersonInfo personInfo = new PersonInfo();
        //Area area = new Area();
        //ShopCategory shopCategory = new ShopCategory();
        shop.setShopId(1L);
        shop.setShopName("测试更新的店铺");
        shop.setLastEditTime(new Date());
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1, effectedNum);
    }

    @Test
    @Ignore
    public void queryByShopId() {
        long shopId = 1L;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println(shop.toString());
    }
}
