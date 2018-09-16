package com.imooc.o2o.service.impl;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.WechatAuthExecution;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.WechatAuth;
import com.imooc.o2o.service.WechatAuthService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author xiaolei hu
 * @date 2018/9/16 13:50
 **/
public class WechatAuthServiceImplTest extends BaseTest {

    @Autowired
    private WechatAuthService wechatAuthService;

    @Test
    public void register() {
        // 新增一条微信账号
        WechatAuth wechatAuth = new WechatAuth();
        PersonInfo personInfo = new PersonInfo();
        String openId = "fjljf;adsfadfdsffadf";
        // 给微信账号设置上用户信息，但不设置上用户id
        // 希望创建微信账号的时候自动创建用户信息
        personInfo.setCreateTime(new Date());
        personInfo.setName("测试用户");
        personInfo.setUserType(1);
        wechatAuth.setPersonInfo(personInfo);
        wechatAuth.setOpenId(openId);
        wechatAuth.setCreateTime(new Date());
        WechatAuthExecution wechatAuthExecution = wechatAuthService.register(wechatAuth);
        // 通过 opendId 找到新增的 wechatAuth
        wechatAuth = wechatAuthService.getWechatAuthByOpenId(openId);
        System.out.println(wechatAuth.getPersonInfo().getName());
    }
}