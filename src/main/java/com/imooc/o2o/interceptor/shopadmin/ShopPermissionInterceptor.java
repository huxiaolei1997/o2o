package com.imooc.o2o.interceptor.shopadmin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.imooc.o2o.entity.Shop;

/**
 * 店家管理系统操作验证拦截器
 */
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        // 从 session 中 获取当前选择的店铺
        Shop currentShop = (Shop) request.getSession().getAttribute(
                "currentShop");
        List<Shop> shopList = (List<Shop>) request.getSession().getAttribute(
                "shopList");
        // 非空判断
        if (currentShop != null && shopList != null) {
            for (Shop shop : shopList) {
                // 如果当前店铺在可操作的列表里则返回 true，进行接下来的用户操作
                if (shop.getShopId() == currentShop.getShopId()) {
                    return true;
                }
            }
        }
        return false;
    }
}