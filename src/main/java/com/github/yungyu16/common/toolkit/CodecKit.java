package com.github.yungyu16.common.toolkit;

import com.google.common.base.Verify;
import com.google.common.collect.Sets;
import com.google.common.io.BaseEncoding;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;
import java.util.function.Function;

/**
 * CreatedDate: 2020/9/17
 * Author: songjialin
 */
public class CodecKit {
    private static final Set<Character> BASE64_CHS = Sets.newHashSet('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/', '-', '_');
    private static final Set<Character> HEX_CHS = Sets.newHashSet('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F');
    //base64
    private static final Function<byte[], byte[]> base64Encoder = buffer -> Base64.getEncoder().encode(buffer);
    private static final Function<byte[], byte[]> urlBase64Encoder = buffer -> Base64.getUrlEncoder().encode(buffer);
    private static final Function<byte[], byte[]> base64Decoder = buffer -> Base64.getDecoder().decode(buffer);
    private static final Function<byte[], byte[]> urlBase64Decoder = buffer -> Base64.getUrlDecoder().decode(buffer);

    public static boolean isHexString(String input) {
        if (StringKit.isEmpty(input)) {
            return false;
        }
        for (char ch : input.toCharArray()) {
            if (!HEX_CHS.contains(ch)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isBase64String(String input) {
        if (StringKit.isEmpty(input)) {
            return false;
        }
        for (char ch : input.toCharArray()) {
            if (!BASE64_CHS.contains(ch)) {
                return false;
            }
        }
        return true;
    }

    public static String encodeWithHex(byte[] input) {
        Verify.verifyNotNull(input);
        return BaseEncoding.base16().encode(input);
    }

    public static byte[] decodeWithHex(String input) {
        Verify.verifyNotNull(input);
        return BaseEncoding.base16().decode(input);
    }

    public static byte[] encodeWithUrlBase64(String input) {
        Verify.verifyNotNull(input);
        return encodeWithUrlBase64(input.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] encodeWithBase64(String input) {
        Verify.verifyNotNull(input);
        return encodeWithBase64(input.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] decodeWithUrlBase64(String input) {
        Verify.verifyNotNull(input);
        return decodeWithUrlBase64(input.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] decodeWithBase64(String input) {
        Verify.verifyNotNull(input);
        return decodeWithBase64(input.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] encodeWithUrlBase64(byte[] input) {
        return doWithBuffer(input, urlBase64Encoder);
    }

    public static byte[] encodeWithBase64(byte[] input) {
        return doWithBuffer(input, base64Encoder);
    }

    public static byte[] decodeWithUrlBase64(byte[] input) {
        return doWithBuffer(input, urlBase64Decoder);
    }

    public static byte[] decodeWithBase64(byte[] input) {
        return doWithBuffer(input, base64Decoder);
    }

    @SuppressWarnings("all")
    private static byte[] doWithBuffer(byte[] input, Function<byte[], byte[]> encoder) {
        Verify.verifyNotNull(input);
        return encoder.apply(input);
    }
}
