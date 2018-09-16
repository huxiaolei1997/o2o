package com.imooc.o2o.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @author xiaolei hu
 * @date 2018/9/15 11:46
 **/
public class WechatUser implements Serializable {
    private static final long serialVersionUID = 350707639370941970L;
    // openId,标识该公众号下面的该用户的唯一Id
    @JSONField(name = "openid")
    private String openId;
    // 用户昵称
    @JSONField(name = "nickname")
    private String nickName;
    // 性别
    @JSONField(name = "sex")
    private int sex;
    // 省份
    @JSONField(name = "province")
    private String province;
    // 城市
    @JSONField(name = "city")
    private String city;
    // 区
    @JSONField(name = "country")
    private String country;
    // 头像图片地址
    @JSONField(name = "headimgurl")
    private String headimgurl;
    // 语言
    @JSONField(name = "language")
    private String language;
    // 用户权限，这里没什么作用
    @JSONField(name = "privilege")
    private String[] privilege;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String[] getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String[] privilege) {
        this.privilege = privilege;
    }

    @Override
    public String toString() {
        return "openId:" + this.getOpenId() + ",nikename:" + this.getNickName();
    }
}
