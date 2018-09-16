package com.imooc.o2o.service;

import com.imooc.o2o.exceptions.WechatAuthOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.imooc.o2o.dto.WechatAuthExecution;
import com.imooc.o2o.entity.WechatAuth;

public interface WechatAuthService {

    /**
     * 通过 openId 查找对应平台的微信账号
     *
     * @param openId
     * @return
     */
    WechatAuth getWechatAuthByOpenId(String openId);

    /**
     * 注册本平台的微信账号
     *
     * @param wechatAuth
     * @return
     * @throws WechatAuthOperationException
     */
    WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException;

}
