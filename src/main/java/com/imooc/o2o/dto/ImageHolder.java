package com.imooc.o2o.dto;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author xiaolei hu
 * @date 2018/9/12 20:55
 **/
public class ImageHolder {
    private String imageName;
    private CommonsMultipartFile image;

    public ImageHolder(String imageName, CommonsMultipartFile image) {
        this.imageName = imageName;
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public CommonsMultipartFile getImage() {
        return image;
    }

    public void setImage(CommonsMultipartFile image) {
        this.image = image;
    }
}
