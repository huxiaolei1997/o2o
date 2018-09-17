package com.imooc.o2o.service;

import com.imooc.o2o.exceptions.LocalAuthOpeartionException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.imooc.o2o.dto.LocalAuthExecution;
import com.imooc.o2o.entity.LocalAuth;

public interface LocalAuthService {
    /**
     * 通过账号和密码获取平台账号信息
     *
     * @param userName
     * @return
     */
    LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password);

    /**
     * 通过userId 获取平台账号信息
     *
     * @param userId
     * @return
     */
    LocalAuth getLocalAuthByUserId(long userId);

    /**
     * @param localAuth
     * @param profileImg
     * @return
     * @throws RuntimeException
     */
    LocalAuthExecution register(LocalAuth localAuth,
                                CommonsMultipartFile profileImg) throws LocalAuthOpeartionException;

    /**
     * 绑定微信，生成平台专属的账号
     *
     * @param localAuth
     * @return
     * @throws RuntimeException
     */
    LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
            throws LocalAuthOpeartionException;

    /**
     * 修改平台账号的登录密码
     *
     * @param userId
     * @param userName
     * @param password
     * @param newPassword
     * @return
     */
    LocalAuthExecution modifyLocalAuth(Long userId, String userName,
                                       String password, String newPassword);
}
