package com.imooc.o2o.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author xiaolei hu
 * @date 2018/9/15 11:43
 **/
public class UserAccessToken {
    // 获取到的凭证
    @JSONField(name = "access_token")
    private String accessToken;
    // 凭证有效时间，单位：秒
    @JSONField(name = "expires_in")
    private String expiresIn;
    // 表示更新令牌，用来获取下一次的访问令牌，这里没太大用处
    @JSONField(name = "refresh_token")
    private String refreshToken;
    // 该用户在此公众号下的身份标识，对于此微信号具有唯一性
    @JSONField(name = "openid")
    private String openId;
    // 表示权限范围，这里可省略
    @JSONField(name = "scope")
    private String scope;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "accessToken:" + this.getAccessToken() + ",openId:" + this.getOpenId();
    }
}
