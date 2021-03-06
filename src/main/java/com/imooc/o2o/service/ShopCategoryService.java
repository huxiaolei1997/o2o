package com.imooc.o2o.service;

import com.imooc.o2o.entity.ShopCategory;

import java.util.List;

/**
 * @author xiaolei hu
 * @date 2018/9/8 17:08
 **/
public interface ShopCategoryService {
    public static final String SCLISTKEY = "shopcategorylist";

    /**
     * 根据查询条件获取 ShopCategory 列表
     *
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
