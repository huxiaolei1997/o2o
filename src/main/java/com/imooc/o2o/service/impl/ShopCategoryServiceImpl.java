package com.imooc.o2o.service.impl;

import com.alibaba.fastjson.JSON;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.ShopCategoryDao;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.exceptions.ShopCategoryException;
import com.imooc.o2o.service.ShopCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaolei hu
 * @date 2018/9/8 17:10
 **/
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Autowired
    private JedisUtil.Strings jedisStrings;

    private static final Logger logger = LoggerFactory.getLogger(ShopCategoryServiceImpl.class);

    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        // 定义 redis 的 key 前缀
        String key = SCLISTKEY;
        List<ShopCategory> shopCategoryList;
        if (shopCategoryCondition == null) {
            // 若查询条件为空，则列出所有首页大类，即 parentId 为空的店铺类别
            key = key + "_allfirstlevel";
        } else if (shopCategoryCondition != null && shopCategoryCondition.getParent() != null) {
            // 若 parentId 非空，则列出该 parentId 下的所有子类类别
            key = key + "_parent" + shopCategoryCondition.getParent().getShopCategoryId();
        } else if (shopCategoryCondition != null) {
            // 列出所有的子类别，不管其属于哪个类，都列出来
            key = key + "_allsecondlevel";
        }

        // 判断 key 是否存在
        if (!jedisKeys.exists(key)) {
            // 若不存在，则从数据库里面取出相应的数据
            shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
            // 将相关的实体类集合转换成 string，存入 redis 里面对应的 key 中
            String jsonString = JSON.toJSONString(shopCategoryList);
            try {
                jedisStrings.set(key, jsonString);

            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new ShopCategoryException(e.getMessage());
            }
        } else {
            // 若存在，则直接从 redis 里面取出相应的数据
            String jsonString = jedisStrings.get(key);
            // 指定要将 string 转换成的集合类型
            shopCategoryList = JSON.parseArray(jsonString, ShopCategory.class);
        }
        return shopCategoryList;
    }
}
