package com.imooc.o2o.exceptions;

/**
 * @author xiaolei hu
 * @date 2018/9/11 20:18
 **/
public class ProductCategoryOperationException extends RuntimeException {
    private static final long serialVersionUID = 1182563719599527969L;

    public ProductCategoryOperationException(String msg) {
        super(msg);
    }
}
