package com.imooc.o2o.exceptions;

/**
 * @author xiaolei hu
 * @date 2018/9/11 20:18
 **/
public class ProductOperationException extends RuntimeException {

    private static final long serialVersionUID = 5076172298827469013L;

    public ProductOperationException(String msg) {
        super(msg);
    }
}
