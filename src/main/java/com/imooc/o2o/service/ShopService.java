package com.imooc.o2o.service;

import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;

/**
 * @author xiaolei hu
 * @date 2018/9/8 11:44
 **/
public interface ShopService {
    /**
     * 查询指定店铺信息
     * @return Shop shop
     */
    Shop getByShopId(long shopId);

    /**
     * 更新店铺信息（从店家角度）
     * @param shop
     * @param shopImg
     * @return
     * @throws RuntimeException
     */
    ShopExecution modifyShop(Shop shop, CommonsMultipartFile shopImg) throws RuntimeException;


    /**
     * 创建商铺
     * @param shop
     * @param shopImg
     * @return
     */
    ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg);
}
