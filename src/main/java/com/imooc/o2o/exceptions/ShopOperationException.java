package com.imooc.o2o.exceptions;

/**
 * @author xiaolei hu
 * @date 2018/9/8 11:59
 **/
public class ShopOperationException extends RuntimeException {
    private static final long serialVersionUID = 2361446884822298905L;
    public ShopOperationException(String msg) {
        super(msg);
    }
}
