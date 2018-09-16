package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.WechatAuth;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author xiaolei hu
 * @date 2018/9/16 13:10
 **/
public class WechatAuthDaoTest extends BaseTest {

    @Autowired
    private WechatAuthDao wechatAuthDao;

    @Test
    @Ignore
    public void insertWechatAuth() {
        WechatAuth wechatAuth = new WechatAuth();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(1L);
        // 给微信账号绑定上用户信息
        wechatAuth.setPersonInfo(personInfo);
        // 随意设置 openid
        wechatAuth.setOpenId("fjdsfjladsjfldasfjlds");
        wechatAuth.setCreateTime(new Date());
        int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
        assertEquals(1, effectedNum);
    }

    @Test
    public void queryWechatInfoByOpenId() {
       WechatAuth wechatAuth = wechatAuthDao.queryWechatInfoByOpenId("fjdsfjladsjfldasfjlds");
       assertEquals("test", wechatAuth.getPersonInfo().getName());
    }

}