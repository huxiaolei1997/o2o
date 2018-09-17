package com.imooc.o2o.service.impl;

import com.alibaba.fastjson.JSON;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.AreaDao;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.exceptions.AreaOpeartionException;
import com.imooc.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xiaolei hu
 * @date 2018/9/7 20:14
 **/
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;

    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Autowired
    private JedisUtil.Strings jedisStrings;

    private static final Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);


    @Override
    @Transactional
    public List<Area> getAreaList() {
        String key = AREALISTKEY;
        List<Area> areaList;
        try {
            if (!jedisKeys.exists(key)) {
                areaList = areaDao.queryArea();
                String jsonString = JSON.toJSONString(areaList);
                jedisStrings.set(key, jsonString);
            } else {
                String jsonString = jedisStrings.get(key);
                areaList = JSON.parseArray(jsonString, Area.class);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AreaOpeartionException(e.getMessage());
        }
        return areaList;
    }
}
