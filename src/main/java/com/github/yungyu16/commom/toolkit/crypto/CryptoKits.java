package com.github.yungyu16.commom.toolkit.crypto;

import cn.xiaoshidai.common.toolkit.base.ConditionTools;
import cn.xiaoshidai.common.toolkit.base.DigestTools;
import cn.xiaoshidai.common.toolkit.base.StringTools;
import lombok.SneakyThrows;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * String decryptResult = CryptoKits.RSA.key(null).decrypt(null).base64String();
 * String encryptResult = CryptoKits.RSA.key(null).encrypt(null).base64String();
 * CreatedDate: 2020/10/17
 * Author: songjialin
 */
public enum CryptoKits {
    RSA,
    AES_DEFAULT("AES"),
    AES_ECB_PKCS7("AES/ECB/PKCS7Padding"),
    DES_DEFAULT("DES"),
    DESede_DEFAULT("DESede");
    private String cryptoPattern;

    CryptoKits() {
    }

    @SneakyThrows
    CryptoKits(String cryptoPattern) {
        this.cryptoPattern = cryptoPattern;
    }

    private String getCryptoPattern() {
        return StringTools.isBlank(cryptoPattern) ? name() : cryptoPattern;
    }

    public CryptoContext key(Key key) {
        return new CryptoContext(key);
    }

    public class CryptoContext {
        private final Key key;
        private byte[] result;

        public CryptoContext(Key key) {
            this.key = key;
        }

        @SneakyThrows
        public FormatAction encrypt(byte[] rawData) {
            ConditionTools.checkNotNull(rawData, "rawData");
            Cipher cipher = Cipher.getInstance(getCryptoPattern());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            result = cipher.doFinal(rawData);
            return new FormatAction();
        }

        @SneakyThrows
        public FormatAction decrypt(byte[] rawData) {
            ConditionTools.checkNotNull(rawData, "rawData");
            Cipher cipher = Cipher.getInstance(getCryptoPattern());
            cipher.init(Cipher.DECRYPT_MODE, key);
            result = cipher.doFinal(rawData);
            return new FormatAction();
        }

        public class FormatAction {
            public byte[] bytes() {
                return result;
            }

            public String string() {
                return string(StandardCharsets.UTF_8);
            }

            public String string(Charset charset) {
                return new String(result, charset);
            }

            public String base64String() {
                return DigestTools.encodeBase64String(result);
            }

            public String urlSafeBase64String() {
                return DigestTools.encodeBase64URLSafeString(result);
            }

            public String lowerHexString() {
                return DigestTools.encodeHexString(result, true);
            }

            public String upperHexString() {
                return DigestTools.encodeHexString(result, false);
            }
        }
    }
}

