package com.imooc.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xiaolei hu
 * @date 2018/9/13 20:22
 **/
@Controller
@RequestMapping(value = "/frontend")
public class FrontendController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    private String index() {
        return "frontend/index";
    }

    /**
     * 店铺列表路由
     * @return
     */
    @RequestMapping(value = "/shoplist", method = RequestMethod.GET)
    private String showShopList() {
        return "frontend/shoplist";
    }

    /**
     * 店铺详情路由
     * @return
     */
    @RequestMapping(value = "/shopdetail", method = RequestMethod.GET)
    private String showShopDetail() {
        return "frontend/shopdetail";
    }

    /**
     * 商品详情页路由
     * @return
     */
    @RequestMapping(value = "/productdetail", method = RequestMethod.GET)
    private String showProductDetail() {
        return "frontend/productdetail";
    }
}
