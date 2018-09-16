package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.PersonInfoDao;
import com.imooc.o2o.dao.WechatAuthDao;
import com.imooc.o2o.dto.WechatAuthExecution;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.WechatAuth;
import com.imooc.o2o.enums.WechatAuthStateEnum;
import com.imooc.o2o.exceptions.WechatAuthOperationException;
import com.imooc.o2o.service.WechatAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class WechatAuthServiceImpl implements WechatAuthService {
    private static Logger log = LoggerFactory
            .getLogger(WechatAuthServiceImpl.class);
    @Autowired
    private WechatAuthDao wechatAuthDao;
    @Autowired
    private PersonInfoDao personInfoDao;

//	@Override
//	public WechatAuth getWechatAuthByOpenId(String openId) {
//		return wechatAuthDao.queryWechatInfoByOpenId(openId);
//	}
//
//	@Override
//	@Transactional
//	public WechatAuthExecution register(WechatAuth wechatAuth,
//			CommonsMultipartFile profileImg) throws RuntimeException {
//		if (wechatAuth == null || wechatAuth.getOpenId() == null) {
//			return new WechatAuthExecution(WechatAuthStateEnum.NULL_AUTH_INFO);
//		}
//		try {
//			wechatAuth.setCreateTime(new Date());
//			if (wechatAuth.getPersonInfo() != null
//					&& wechatAuth.getPersonInfo().getUserId() == null) {
//				if (profileImg != null) {
//					try {
//						addProfileImg(wechatAuth, profileImg);
//					} catch (Exception e) {
//						log.debug("addUserProfileImg error:" + e.toString());
//						throw new RuntimeException("addUserProfileImg error: "
//								+ e.getMessage());
//					}
//				}
//				try {
//					wechatAuth.getPersonInfo().setCreateTime(new Date());
//					wechatAuth.getPersonInfo().setLastEditTime(new Date());
//					wechatAuth.getPersonInfo().setCustomerFlag(1);
//					wechatAuth.getPersonInfo().setShopOwnerFlag(1);
//					wechatAuth.getPersonInfo().setAdminFlag(0);
//					wechatAuth.getPersonInfo().setEnableStatus(1);
//					PersonInfo personInfo = wechatAuth.getPersonInfo();
//					int effectedNum = personInfoDao
//							.insertPersonInfo(personInfo);
//					wechatAuth.setUserId(personInfo.getUserId());
//					if (effectedNum <= 0) {
//						throw new RuntimeException("添加用户信息失败");
//					}
//				} catch (Exception e) {
//					log.debug("insertPersonInfo error:" + e.toString());
//					throw new RuntimeException("insertPersonInfo error: "
//							+ e.getMessage());
//				}
//			}
//			int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
//			if (effectedNum <= 0) {
//				throw new RuntimeException("帐号创建失败");
//			} else {
//				return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS,
//						wechatAuth);
//			}
//		} catch (Exception e) {
//			log.debug("insertWechatAuth error:" + e.toString());
//			throw new RuntimeException("insertWechatAuth error: "
//					+ e.getMessage());
//		}
//	}

//	private void addProfileImg(WechatAuth wechatAuth,
//			CommonsMultipartFile profileImg) {
//		String dest = FileUtil.getPersonInfoImagePath();
//		String profileImgAddr = ImageUtil.generateThumbnail(profileImg, dest);
//		wechatAuth.getPersonInfo().setProfileImg(profileImgAddr);
//	}

    /**
     * 通过 openId 查找对应平台的微信账号
     *
     * @param openId
     * @return
     */
    @Override
    public WechatAuth getWechatAuthByOpenId(String openId) {
        return wechatAuthDao.queryWechatInfoByOpenId(openId);
    }

    /**
     * 注册本平台的微信账号
     *
     * @param wechatAuth
     * @return
     * @throws WechatAuthOperationException
     */
    @Override
    @Transactional
    public WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException {
        if (wechatAuth == null || wechatAuth.getOpenId() == null) {
            return new WechatAuthExecution(WechatAuthStateEnum.NULL_AUTH_INFO);
        }
        try {
            // 设置创建时间
            wechatAuth.setCreateTime(new Date());
            // 如果微信账号里夹带着用户信息并且用户 id 为空，则认为该用户第一次使用平台（且通过微信登录）
            // 则自动创建用户信息
            if (wechatAuth.getPersonInfo() != null && wechatAuth.getPersonInfo().getUserId() == null) {
                try {
                    wechatAuth.getPersonInfo().setCreateTime(new Date());
                    wechatAuth.getPersonInfo().setEnableStatus(1);
                    PersonInfo personInfo = wechatAuth.getPersonInfo();
                    int effectedNum = personInfoDao.insertPersonInfo(personInfo);
                    if (effectedNum <= 0) {
                        throw new WechatAuthOperationException("添加用户信息失败");
                    }
                } catch (Exception e) {
                    log.error("insertPersonInfo error:" + e.toString());
                    throw new WechatAuthOperationException("insertPersonInfo error:" + e.toString());
                }
            }
            // 创建专属于本平台的微信账号
            int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
            if (effectedNum <= 0) {
                throw new WechatAuthOperationException("账号创建失败");
            } else {
                return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS, wechatAuth);
            }
        } catch (Exception e) {
            log.error("insertWechatAuth error:" + e.toString());
            throw new WechatAuthOperationException("insertWechatAuth error:" + e.toString());
        }
    }
}
