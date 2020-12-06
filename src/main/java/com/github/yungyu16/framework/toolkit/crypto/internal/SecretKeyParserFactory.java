package com.github.yungyu16.framework.toolkit.crypto.internal;





import com.github.yungyu16.framework.toolkit.StringKit;
import com.github.yungyu16.framework.toolkit.crypto.KeyParser;

import javax.crypto.spec.SecretKeySpec;

/**
 * CreatedDate: 2020/10/17
 * Author: songjialin
 */
public class SecretKeyParserFactory {
    public static KeyParser create(String algName) {
        if (StringKit.isBlank(algName)) {
            throw new IllegalArgumentException("algName");
        }
        return it -> new SecretKeySpec(it, algName);
    }
}
