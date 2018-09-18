package com.imooc.o2o.interceptor.shopadmin;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.imooc.o2o.entity.PersonInfo;

/**
 * 店家管理系统登录验证拦截器
 */
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {
    /**
     * 主要做事前拦截，即用户操作发生前，改写 preHandle 里的逻辑，进行拦截
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        // 从 session 中取出用户信息
        Object userObj = request.getSession().getAttribute("user");
        if (userObj != null) {
            PersonInfo user = (PersonInfo) userObj;
            if (user != null && user.getUserId() != null
                    && user.getUserId() > 0 && user.getEnableStatus() == 1)
                // 若通过验证则返回true, 拦截器返回 true 之后，用户接下来的操作得以进行
                return true;
        }
        // 若未通过登录验证，则直接跳转到账号登录页面
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<script>");
        out.println("window.open ('" + request.getContextPath()
                + "/local/login?usertype=2','_self')");
        out.println("</script>");
        out.println("</html>");
        return false;
    }
}