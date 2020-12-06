package com.github.yungyu16.framework.toolkit.crypto.internal;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;

/**
 * CreatedDate: 2020/10/17
 * Author: songjialin
 */
public class RsaKeyFactoryHolder {
    public static KeyFactory keyFactory;

    static {
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
