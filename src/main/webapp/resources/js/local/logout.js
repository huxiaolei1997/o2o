$(function () {
    $("#log-out").click(function () {
        // 清除 session
        $.ajax({
            url: "/o2o/local/logout",
            type: "POST",
            async: false,
            cache: false,
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    var usertype = $("#log-out").attr("usertype");
                    // 清除成功后退出到登录界面
                    window.location.href = '/o2o/local/login?usertype=' + usertype;
                    return false;
                }
            },
            error: function (data) {
                alert(data);
            }
        });
    });
});