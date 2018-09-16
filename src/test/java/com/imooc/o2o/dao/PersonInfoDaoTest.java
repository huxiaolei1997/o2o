package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.PersonInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author xiaolei hu
 * @date 2018/9/16 12:56
 **/
public class PersonInfoDaoTest extends BaseTest {
    @Autowired
    private PersonInfoDao personInfoDao;

    @Test
    public void insertPersonInfo() {
        // 设置新增的用户信息
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("测试");
        personInfo.setGender("男");
        personInfo.setUserType(1);
        personInfo.setCreateTime(new Date());
        personInfo.setLastEditTime(new Date());
        personInfo.setEnableStatus(1);
        int effectedNum = personInfoDao.insertPersonInfo(personInfo);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testQueryPersonInfoById() {
        long userId = 1;
        // 查询 Id 为1的用户信息
        PersonInfo personInfo = personInfoDao.queryPersonInfoById(userId);
        System.out.println(personInfo.getName());
    }
}