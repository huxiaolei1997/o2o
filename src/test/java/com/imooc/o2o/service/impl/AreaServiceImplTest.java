package com.imooc.o2o.service.impl;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.CacheService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class AreaServiceImplTest extends BaseTest {
    @Autowired
    private AreaService areaService;

    @Autowired
    private CacheService cacheService;

    @Test
    public void getAreaList() {
        List<Area> areaList = areaService.getAreaList();
        System.out.println("areaList = " + areaList.toString());
        cacheService.removeFromCache(areaService.AREALISTKEY);
        areaList = areaService.getAreaList();
        System.out.println(areaList.toString());
    }
}