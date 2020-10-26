package com.github.yungyu16.commom.toolkit.crypto;

import cn.xiaoshidai.common.toolkit.base.ConditionTools;
import cn.xiaoshidai.common.toolkit.base.DigestTools;
import cn.xiaoshidai.web.app.boot.framework.crypto.internal.RsaPriKeyParser;
import cn.xiaoshidai.web.app.boot.framework.crypto.internal.RsaPubKeyParser;
import cn.xiaoshidai.web.app.boot.framework.crypto.internal.SecretKeyParserFactory;
import lombok.SneakyThrows;

import java.security.Key;
import java.security.spec.InvalidKeySpecException;

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
        ConditionTools.checkNotNull(keyParser, "keyParser");
        this.keyParser = keyParser;
    }

    public Key parseKey(byte[] keyBytes) throws InvalidKeySpecException {
        ConditionTools.checkNotNull(keyBytes);
        return keyParser.parse(keyBytes);
    }

    public Key parseKey(String base64Str) throws InvalidKeySpecException {
        ConditionTools.checkNotBlank(base64Str);
        return keyParser.parse(DigestTools.decodeBase64(base64Str));
    }
}
