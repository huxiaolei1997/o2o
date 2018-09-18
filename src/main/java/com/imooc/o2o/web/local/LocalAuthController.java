package com.imooc.o2o.web.local;

import com.imooc.o2o.dto.LocalAuthExecution;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.enums.LocalAuthStateEnum;
import com.imooc.o2o.exceptions.LocalAuthOpeartionException;
import com.imooc.o2o.service.LocalAuthService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * LocalAuthController
 *
 * @create 2018-09-18 10:00
 * @copyright huxiaolei1997@gmail.com
 */
@Controller
@RequestMapping(value = "local", method = {RequestMethod.GET, RequestMethod.POST})
public class LocalAuthController {
    @Autowired
    private LocalAuthService localAuthService;


    @RequestMapping(value = "bindlocalauth", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> bindLocalAuth(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        // 获取输入的账号
        String userName = HttpServletRequestUtil.getString(request, "userName");
        // 获取用户输入的密码
        String password = HttpServletRequestUtil.getString(request, "password");
        // 从 session 中获取当前用户信息（一旦通过微信登录后，便能获取到用户信息）
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        // 非空判断，要求账号密码以及当前的用户 session 非空
        if (userName != null && password != null && user != null && user.getUserId() != null) {
            // 创建 LocalAuth 对象并赋值
            LocalAuth localAuth = new LocalAuth();
            localAuth.setUserName(userName);
            localAuth.setPassword(password);
            localAuth.setPersonInfo(user);

            // 绑定账号
            LocalAuthExecution le = localAuthService.bindLocalAuth(localAuth);
            if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", le.getStateInfo());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码均不能为空");
        }
        return modelMap;
    }

    @RequestMapping(value = "/changelocalpwd", method = RequestMethod.POST)
    @ResponseBody
    /**
     * 修改密码
     */
    private Map<String, Object> changeLocalPwd(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 校验验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        // 获取账号
        String userName = HttpServletRequestUtil.getString(request, "userName");
        // 获取原密码
        String password = HttpServletRequestUtil.getString(request, "password");
        // 获取新密码
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
        // 从 session 中获取当前用户信息（一旦通过微信登录后，便能获取到用户信息）
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");

        // 非空判断，要求账号密码以及当前的用户 session 非空，且新旧密码不能相同
        if (userName != null && password != null && newPassword != null &&
                user != null && user.getUserId() != null && !password.equals(newPassword)) {
            try {
                // 查看原先账号，是否与输入的账号一致，不一致则认为是非法操作
                LocalAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());
                if (localAuth == null || !localAuth.getUserName().equals(userName)) {
                    // 不一致则直接退出
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "输入的账号非本次登录的账号");
                    return modelMap;
                }
                // 修改平台账号的用户密码
                LocalAuthExecution le = localAuthService.modifyLocalAuth(user.getUserId(), userName, password, newPassword);
                if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", le.getStateInfo());
                }
            } catch (LocalAuthOpeartionException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入密码");
        }
        return modelMap;
    }

    @RequestMapping(value = "/logincheck", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> loginCheck(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 获取是否需要进行验证码校验的标识符
        boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }

        // 获取输入的账号
        String userName = HttpServletRequestUtil.getString(request, "userName");
        // 获取输入的密码
        String password = HttpServletRequestUtil.getString(request, "password");
        // 非空校验
        if (userName != null && password != null) {
            // 传入账号和密码无法获取平台信息
            LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(userName, password);
            if (localAuth != null) {
                // 若能获取到账号信息则登录成功
                modelMap.put("success", true);
                // 同时在 session 里设置用户信息
                request.getSession().setAttribute("user", localAuth.getPersonInfo());
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户名或密码错误");
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码不能为空");
        }
        return modelMap;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    /**
     * 当用户点击退出按钮的时候注销 session
     */
    private Map<String, Object> logout(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 将用户 session 置为空
        request.getSession().setAttribute("user", null);
        modelMap.put("success", true);
        return modelMap;
    }
}
