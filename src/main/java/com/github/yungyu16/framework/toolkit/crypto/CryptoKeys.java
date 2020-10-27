package com.github.yungyu16.framework.toolkit.crypto;

import com.github.yungyu16.framework.toolkit.crypto.internal.RsaPriKeyParser;
import com.github.yungyu16.framework.toolkit.crypto.internal.RsaPubKeyParser;
import com.github.yungyu16.framework.toolkit.crypto.internal.SecretKeyParserFactory;
import com.google.common.base.Verify;
import lombok.SneakyThrows;

import java.security.Key;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Key theirRsaPublicKey = CryptoKeys.RSA_PUB_DEFAULT.parseKey(publicKeyStr);
 * CreatedDate: 2020/10/17
 * Author: songjialin
 */
public enum CryptoKeys {
    /**
     * X509
     */
    RSA_PUB_DEFAULT(new RsaPubKeyParser()),
    /**
     * PKCS8
     */
    RSA_PRI_DEFAULT(new RsaPriKeyParser()),
    AES_DEFAULT(SecretKeyParserFactory.create("AES")),
    DES_DEFAULT(SecretKeyParserFactory.create("DES")),
    DESede_DEFAULT(SecretKeyParserFactory.create("DESede"));

    private final KeyParser keyParser;

    @SneakyThrows
    CryptoKeys(KeyParser keyParser) {
        Verify.verifyNotNull(keyParser, "keyParser");
        this.keyParser = keyParser;
    }

    public Key parseKey(byte[] keyBytes) throws InvalidKeySpecException {
        Verify.verifyNotNull(keyBytes, "keyBytes");
        return keyParser.parse(keyBytes);
    }

    public Key parseKey(String base64Str) throws InvalidKeySpecException {
        Verify.verifyNotNull(base64Str, "base64Str");
        return keyParser.parse(Base64.getDecoder().decode(base64Str));
    }
}
