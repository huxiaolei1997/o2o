package com.imooc.o2o.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xiaolei hu
 * @date 2018/9/9 12:05
 **/
public class DynamicDataSourceHolder {
    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceHolder.class);
    private static ThreadLocal<String> contextHoldere = new ThreadLocal<>();
    public static final String DB_MASTER = "master";
    public static final String DB_SLAVE = "slave";

    public static String getDbType() {
        String db = contextHoldere.get();
        if (db == null) {
            db = DB_MASTER;
        }
        return db;
    }

    // 设置线程的 dbType
    public static void setDbType(String str) {
        logger.debug("所使用的数据源是：" + str);
        contextHoldere.set(str);
    }

    // 清理连接类型
    public static void clearDbType() {
        contextHoldere.remove();
    }
}
