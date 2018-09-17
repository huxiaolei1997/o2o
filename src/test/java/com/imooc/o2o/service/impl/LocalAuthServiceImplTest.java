package com.imooc.o2o.service.impl;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.LocalAuthExecution;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.enums.WechatAuthStateEnum;
import com.imooc.o2o.service.LocalAuthService;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author xiaolei hu
 * @date 2018/9/17 22:01
 **/
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthServiceImplTest extends BaseTest {
    @Autowired
    private LocalAuthService localAuthService;

    @Test
    @Ignore
    public void testAbindLocalAuth() {
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        String username = "testusername";
        String password = "testpassword";
        personInfo.setUserId(1L);
        localAuth.setPersonInfo(personInfo);
        localAuth.setUserName(username);
        localAuth.setPassword(password);
        // 绑定账号
        LocalAuthExecution lae = localAuthService.bindLocalAuth(localAuth);
        localAuth = localAuthService.getLocalAuthByUserId(personInfo.getUserId());
        System.out.println("用户昵称：" + localAuth.getPersonInfo().getName());
        System.out.println("平台账号密码：" + localAuth.getPassword());
    }

    @Test
    public void testBmodifyLocalAuth() {
        long userId = 1;
        String username = "testusername";
        String password = "testpassword";
        String newPassword = "testnewpassword";
        // 修改该账号对应的密码
        LocalAuthExecution lae = localAuthService.modifyLocalAuth(userId, username, password, newPassword);
        assertEquals(WechatAuthStateEnum.SUCCESS.getState(), lae.getState());
        // 通过账号密码找到修改后的 localAuth
        LocalAuth localAuth = localAuthService.getLocalAuthByUserId(userId);
        System.out.println(localAuth.getPersonInfo().getName());
    }
}