package com.imooc.o2o.service;

import com.imooc.o2o.entity.HeadLine;

import java.io.IOException;
import java.util.List;

/**
 * @author xiaolei hu
 * @date 2018/9/13 19:55
 **/
public interface HeadLineService {
    public static final String HILLISTKEY = "headlinelist";

    /**
     * 根据传入的条件返回指定的头条列表
     *
     * @param headLineCondition
     * @return
     * @throws IOException
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;
}
