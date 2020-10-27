package com.github.yungyu16.framework.toolkit.crypto;

import lombok.SneakyThrows;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

/**
 * String signResult = SignKits.SHA256withRSA.key(key).data(data).sign().base64String();
 * boolean verifyResult = SignKits.SHA256withRSA.key(pub).data(data).verify(sign);
 * CreatedDate: 2020/10/17
 * Author: songjialin
 */
public enum SignKits {
    MD2withRSA,
    MD5withRSA,
    SHA1WithRSA,
    SHA256withRSA,
    SHA384withRSA,
    SHA512withRSA;

    public SignatureContext key(Key key) {
        return new SignatureContext(key);
    }

    public class SignatureContext {
        private final Key key;
        private byte[] data;
        private byte[] result;

        public SignatureContext(Key key) {
            this.key = key;
        }

        public ActualAction data(byte[] rawData) {
            this.data = rawData;
            return new ActualAction();
        }

        public class ActualAction {
            @SneakyThrows
            public FormatAction sign() {
                Signature signature = Signature.getInstance(name());
                if ((!(key instanceof PrivateKey))) {
                    throw new RuntimeException("请使用私钥签名");
                }
                signature.initSign(((PrivateKey) key));
                signature.update(data);
                result = signature.sign();
                return new FormatAction();
            }

            @SneakyThrows
            public boolean verify(byte[] signData) {
                Signature signature = Signature.getInstance(name());
                if ((!(key instanceof PublicKey))) {
                    throw new RuntimeException("请使用公钥验签");
                }
                signature.initVerify(((PublicKey) key));
                signature.update(data);
                return signature.verify(signData);
            }
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
                return Base64.getEncoder().encodeToString(result);
            }
        }
    }
}

