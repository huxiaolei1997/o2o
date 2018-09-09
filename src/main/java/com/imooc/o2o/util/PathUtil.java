package com.imooc.o2o.util;

/**
 * @author xiaolei hu
 * @date 2018/9/8 10:43
 **/
public class PathUtil {
    private static final String seperator = System.getProperty("file.separator");

    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "D:/Intellij WorkSpaces/o2o";
        } else {
            basePath = "/usr/local/image";
        }
        basePath = basePath.replace("/", seperator);
        return basePath;
    }

    public static String getShopImagePath(long shopId) {
        String imagePath = "/upload/item/shop/" + shopId + "/";
        return imagePath.replace("/", seperator);
    }
}
