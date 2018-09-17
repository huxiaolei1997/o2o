package com.imooc.o2o.service;

import com.imooc.o2o.entity.Area;

import java.util.List;

/**
 * @author xiaolei hu
 * @date 2018/9/7 20:13
 **/
public interface AreaService {
    public static final String AREALISTKEY = "arealist";

    /**
     * 获取区域列表，优先从缓存中获取
     *
     * @return
     */
    List<Area> getAreaList();

}
