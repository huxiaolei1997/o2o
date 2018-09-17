package com.imooc.o2o.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaolei hu
 * @date 2018/9/5 20:06
 **/
public class WechatAuth implements Serializable {
    private static final long serialVersionUID = -6772032506056288565L;
    // 主键 Id
    private Long wechatAuthId;
    // 微信获取用户信息的凭证，对于某个公众号具有唯一性
    private String openId;
    // 创建时间
    private Date createTime;
    // 用户信息
    private PersonInfo personInfo;

    public Long getWechatAuthId() {
        return wechatAuthId;
    }

    public void setWechatAuthId(Long wechatAuthId) {
        this.wechatAuthId = wechatAuthId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }
}
