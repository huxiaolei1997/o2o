package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Area;

import java.util.List;

/**
 * @author xiaolei hu
 * @date 2018/9/7 19:52
 **/
public interface AreaDao {
    /**
     * 列出区域列表
     * @return areatList
     */
    List<Area> queryArea();
}
