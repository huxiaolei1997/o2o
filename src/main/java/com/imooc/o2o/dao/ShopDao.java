package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Shop;

/**
 * @author xiaolei hu
 * @date 2018/9/8 9:43
 **/
public interface ShopDao {
    /**
     * 通过 shop id 查询店铺
     * @param shopId
     * @return
     */
    Shop queryByShopId(long shopId);

    /**
     * 新增店铺
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺信息
     * @param shop
     * @return
     */
    int updateShop(Shop shop);


}
