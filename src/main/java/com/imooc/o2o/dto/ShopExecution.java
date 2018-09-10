package com.imooc.o2o.dto;

import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;

import java.util.List;

/**
 * @author xiaolei hu
 * @date 2018/9/8 11:00
 **/
public class ShopExecution {
    // 结果状态
    private int state;

    // 状态标记
    private String stateInfo;

    // 操作的店铺
    private Shop shop;

    // 店铺数量
    private int count;

    // shop 列表（查询店铺列表的时候使用）
    private List<Shop> shopList;

    public ShopExecution() {

    }

    // 店铺操作失败的时候使用的构造器
    public ShopExecution(ShopStateEnum shopSateEnum) {
        this.state = shopSateEnum.getState();
        this.stateInfo = shopSateEnum.getStateInfo();
    }

    //  店铺操作成功的时候使用的构造器
    public ShopExecution(ShopStateEnum shopSateEnum, Shop shop) {
        this.state = shopSateEnum.getState();
        this.stateInfo = shopSateEnum.getStateInfo();
        this.shop = shop;
    }

    // 店铺操作成功的时候使用的构造器
    public ShopExecution(ShopStateEnum shopSateEnum, List<Shop> shopList) {
        this.state = shopSateEnum.getState();
        this.stateInfo = shopSateEnum.getStateInfo();
        this.shopList = shopList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
