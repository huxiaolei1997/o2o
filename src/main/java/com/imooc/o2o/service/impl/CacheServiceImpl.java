package com.imooc.o2o.service.impl;

import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.xml.ws.WebFault;
import java.util.Set;


@Service
public class CacheServiceImpl implements CacheService {
    @Autowired
    private JedisUtil.Keys jedisKeys;

    /**
     * 根据 key 前缀删除匹配该模式下的所有 key-value 如传入 shopcategory
     * 则以 shopcategory 开头的 key_value 都会被清空
     *
     * @param keyPrefix
     */
    @Override
    public void removeFromCache(String keyPrefix) {
        Set<String> keySet = jedisKeys.keys(keyPrefix + "*");
        keySet.forEach(x -> jedisKeys.del(x));
//        for (String key : keySet) {
//            jedisKeys.del(key);
//        }
    }
}
