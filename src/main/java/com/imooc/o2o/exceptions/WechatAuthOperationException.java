package com.imooc.o2o.exceptions;

/**
 * @author xiaolei hu
 * @date 2018/9/16 13:37
 **/
public class WechatAuthOperationException extends RuntimeException {
    private static final long serialVersionUID = 4315866839048210269L;

    public WechatAuthOperationException(String msg) {
        super(msg);
    }
}
