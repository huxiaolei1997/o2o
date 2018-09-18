$(function () {
    // 登录验证的 url
    var loginUrl = '/o2o/local/logincheck';
    // 从地址栏的url里获取 usertype，1 为 customer，2 为 shopowner
    var usertype = getQueryString("usertype");
    // 登录次数，累计登录三次失败之后自动弹出验证码要求输入
    var loginCount = 0;

    $('#submit').click(function () {
        var userName = $('#username').val();
        var password = $('#psw').val();
        var verifyCodeActual = $('#j_captcha').val();
        // 是否需要及校验验证码，默认不需要
        var needVerify = false;
        if (loginCount >= 3) {
            if (!verifyCodeActual) {
                $.toast('请输入验证码！');
                return;
            } else {
                needVerify = true;
            }
        }
        $.ajax({
            url: loginUrl,
            async: false,
            cache: false,
            type: "post",
            dataType: 'json',
            data: {
                userName: userName,
                password: password,
                verifyCodeActual: verifyCodeActual,
                needVerify: needVerify
            },
            success: function (data) {
                if (data.success) {
                    $.toast('登录成功！');
                    if (usertype == 1) {
                        // 若用户在前端展示系统页面则自动链接到前端展示系统首页
                        window.location.href = "/o2o/frontend/index";
                    } else {
                        // 若用户是在店家管理系统页面则自动链接到店铺列表中
                        window.location.href = "/o2o/shopadmin/shoplist";
                    }
                } else {
                    $.toast('登录失败！' + data.errMsg);
                    if (needVerify) {
                        $("#captcha_img").click();
                    } else {
                        loginCount++;
                        if (loginCount >= 3) {
                            $('#verifyPart').show();
                        }
                    }
                }
            }
        });
    });

    // $('#register').click(function () {
    //     window.location.href = '/o2o/shop/register';
    // });
});