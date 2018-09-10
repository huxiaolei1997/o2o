package com.imooc.o2o.util;

/**
 * @author xiaolei hu
 * @date 2018/9/10 21:20
 **/
public class PageCalculator {
    public static int calculateRowIndex(int pageIndex, int pageSize) {
        return (pageIndex > 0 ? (pageIndex - 1) * pageSize : 0);
    }
}
