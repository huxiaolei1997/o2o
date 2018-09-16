package com.imooc.o2o.util.wechat;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author xiaolei hu
 * @date 2018/9/15 11:52
 **/
public class MyX509TrustManager implements X509TrustManager {
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
