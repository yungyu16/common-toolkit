package com.github.yungyu16.framework.toolkit;

import com.google.common.base.Verify;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CreatedDate: 2020/9/17
 * Author: songjialin
 */
public class PhoneNoKit {
    /**
     * 111|2222|3333
     */
    private static final Pattern PATTERN = Pattern.compile("^(1\\d{2})(\\d{4})(\\d{4})$");
    /**
     * ***22223333
     */
    private static final String PATTERN_1 = "***$2$3";
    /**
     * 111****3333
     */
    private static final String PATTERN_2 = "$1****$3";
    /**
     * 1112222****
     */
    private static final String PATTERN_3 = "$1$2****";

    public static Optional<String> hide(String phoneNo) {
        return hide2(phoneNo);
    }

    public static Optional<String> hide1(String phoneNo) {
        return hide(phoneNo, PATTERN_1);
    }

    public static Optional<String> hide2(String phoneNo) {
        return hide(phoneNo, PATTERN_2);
    }

    public static Optional<String> hide3(String phoneNo) {
        return hide(phoneNo, PATTERN_3);
    }

    private static Optional<String> hide(String phoneNo, String pattern) {
        Verify.verifyNotNull(pattern, "pattern");
        return verify(phoneNo).map(it -> PATTERN.matcher(it).replaceAll(pattern));
    }

    public static Optional<String> verify(String phoneNo) {
        if (StringKit.isBlank(phoneNo)) {
            return Optional.empty();
        }
        phoneNo = phoneNo.trim();
        Matcher matcher = PATTERN.matcher(phoneNo);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        return Optional.of(phoneNo);
    }
}
