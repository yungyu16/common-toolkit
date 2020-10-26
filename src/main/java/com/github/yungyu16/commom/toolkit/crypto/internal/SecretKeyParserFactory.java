package com.github.yungyu16.commom.toolkit.crypto.internal;


import com.github.yungyu16.commom.toolkit.base.AssertKits;
import com.github.yungyu16.commom.toolkit.crypto.KeyParser;

import javax.crypto.spec.SecretKeySpec;

/**
 * CreatedDate: 2020/10/17
 * Author: songjialin
 */
public class SecretKeyParserFactory {
    public static KeyParser create(String algName) {
        AssertKits.Strings.isBlank(algName).throwOnTrue(() -> new NullPointerException("algName"));
        return it -> new SecretKeySpec(it, algName);
    }
}
