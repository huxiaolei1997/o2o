package com.imooc.o2o.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author xiaolei hu
 * @date 2018/9/8 10:23
 **/
public class ImageUtil {
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat(
            "yyyyMMddHHmmss"); // 时间格式化的格式
    private static final Random r = new Random();
    public static void main(String[] args) throws IOException {
        //String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        URL basePath = ImageUtil.class.getResource("/");
        System.out.println(basePath.toString().substring(6));
        Thumbnails.of(new File("C:\\Users\\Mystery\\Desktop\\1.jpg"))
                .size(200, 200).watermark(Positions.BOTTOM_RIGHT,
                ImageIO.read(new File("C:\\Users\\Mystery\\Desktop\\2.jpg")), 0.25f).outputQuality(0.8f).toFile("C:\\Users\\Mystery\\Desktop\\1_new.jpg");

    }

    public static String generateThumbnail(CommonsMultipartFile thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail);
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getInputStream()).size(200, 200).outputQuality(0.25f).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }
        return relativeAddr;
    }

    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    private static String getFileExtension(CommonsMultipartFile cFile) {
        String originalFileName = cFile.getOriginalFilename();
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }

    public static String getRandomFileName() {
        // 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
        int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
        String nowTimeStr = sDateFormat.format(new Date()); // 当前时间
        return nowTimeStr + rannum;
    }

    /**
     * storePath 是文件的路径还是目录
     * 如果storePath 是文件路径则删除文件
     * 如果storePath 是目录则删除该目录下所有的文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath) {
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()) {
            // 如果是目录则递归删除目录下的文件
            if (fileOrPath.isDirectory()) {
                // 列出目录下所有文件
                File[] files = fileOrPath.listFiles();
                for (int i = 0,length = files.length; i < length; i++) {
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }
}
