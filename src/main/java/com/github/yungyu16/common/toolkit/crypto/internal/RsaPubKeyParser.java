package com.github.yungyu16.common.toolkit.crypto.internal;




import com.github.yungyu16.common.toolkit.crypto.KeyParser;

import java.security.Key;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * CreatedDate: 2020/10/17
 * Author: songjialin
 */
public class RsaPubKeyParser implements KeyParser {

    @Override
    public Key parse(byte[] keyData) throws InvalidKeySpecException {
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(keyData);
        return RsaKeyFactoryHolder.keyFactory.generatePublic(publicKeySpec);
    }
}
