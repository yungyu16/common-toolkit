package com.github.yungyu16.common.toolkit.crypto;






import com.github.yungyu16.common.toolkit.CodecKit;
import com.github.yungyu16.common.toolkit.StringKit;
import com.github.yungyu16.common.toolkit.crypto.internal.RsaPriKeyParser;
import com.github.yungyu16.common.toolkit.crypto.internal.RsaPubKeyParser;
import com.github.yungyu16.common.toolkit.crypto.internal.SecretKeyParserFactory;
import com.google.common.base.Preconditions;
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
        Preconditions.checkNotNull(keyParser, "keyParser");
        this.keyParser = keyParser;
    }

    public Key parseKey(byte[] keyBytes) throws InvalidKeySpecException {
        Preconditions.checkNotNull(keyBytes);
        return keyParser.parse(keyBytes);
    }

    public Key parseKey(String base64Str) throws InvalidKeySpecException {
        if (StringKit.isBlank(base64Str)) {
            throw new IllegalArgumentException();
        }
        return keyParser.parse(CodecKit.decodeWithBase64(base64Str));
    }
}
