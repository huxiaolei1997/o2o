package com.imooc.o2o.service.impl;

import com.alibaba.fastjson.JSON;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.HeadLineDao;
import com.imooc.o2o.entity.HeadLine;
import com.imooc.o2o.exceptions.HeadLineOperationException;
import com.imooc.o2o.service.HeadLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author xiaolei hu
 * @date 2018/9/13 19:57
 **/
@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;

    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Autowired
    private JedisUtil.Strings jedisStrings;

    private static final Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);

    /**
     * 根据传入的条件返回指定的头条列表
     *
     * @param headLineCondition
     * @return
     * @throws IOException
     */
    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
        // 定义 redis 的 key 前缀
        String key = HILLISTKEY;
        List<HeadLine> headLineList;
        // 拼接出redis的key
        if (headLineCondition != null && headLineCondition.getEnableStatus() != null) {
            key = key + "_" + headLineCondition.getEnableStatus();
        }
        // 判断 key 是否存在
        if (!jedisKeys.exists(key)) {
            // 若不存在，则从数据库里面取出相应数据
            headLineList = headLineDao.queryHeadLine(headLineCondition);
            // 将相关的实体类集合转换成string，存入redis里面对应的key中
            String jsonString;
            try {
                jsonString = JSON.toJSONString(headLineList);
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new HeadLineOperationException(e.getMessage());
            }
            jedisStrings.set(key, jsonString);
        } else {
            String jsonString = jedisStrings.get(key);
            headLineList = JSON.parseArray(jsonString, HeadLine.class);
        }
        return headLineList;
    }
}
