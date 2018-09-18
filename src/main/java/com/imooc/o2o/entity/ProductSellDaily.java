package com.imooc.o2o.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaolei hu
 * @date 2018/9/18 19:33
 **/
public class ProductSellDaily implements Serializable {
    private static final long serialVersionUID = 6862532893237928901L;
    // 哪天的销量，精确到天
    private Date createTime;
    // 销量
    private Integer total;
    // 商品信息实体类
    private Product product;
    // 店铺信息实体类
    private Shop shop;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
