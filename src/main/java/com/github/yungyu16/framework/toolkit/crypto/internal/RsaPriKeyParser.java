package com.github.yungyu16.framework.toolkit.crypto.internal;


import com.github.yungyu16.framework.toolkit.crypto.KeyParser;

import java.security.Key;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * CreatedDate: 2020/10/17
 * Author: songjialin
 */
public class RsaPriKeyParser implements KeyParser {
    @Override
    public Key parse(byte[] keyData) throws InvalidKeySpecException {
        PKCS8EncodedKeySpec publicKeySpec = new PKCS8EncodedKeySpec(keyData);
        return RsaKeyFactoryHolder.keyFactory.generatePrivate(publicKeySpec);
    }
}
