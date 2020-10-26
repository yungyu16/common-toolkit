package com.github.yungyu16.commom.toolkit.base;

import java.util.Collection;
import java.util.Map;

/**
 * CreatedDate: 2020/10/26
 * Author: songjialin
 */
public final class AssertKits {
    private AssertKits() {
    }

    public static class Base {
        public static AssertResult notNull(Object val) {
            return AssertResult.valueOf(val != null);
        }

        public static AssertResult isNull(Object val) {
            return AssertResult.valueOf(val == null);
        }
    }

    public static class Strings {
        public static AssertResult isEmpty(String val) {
            return AssertResult.valueOf(val == null || val.length() == 0);
        }

        public static AssertResult isBlank(String val) {
            AssertResult empty = isEmpty(val);
            if (empty.value()) {
                return empty;
            }
            for (char ch : val.toCharArray()) {
                if (!Character.isWhitespace(ch)) {
                    return AssertResult.valueOf(false);
                }
            }
            return AssertResult.valueOf(true);
        }
    }

    public static class Arrays {
        public static boolean isEmpty(Object[] val) {
            return false;
        }
    }

    public static class Collections {
        public static boolean isEmpty(Collection val) {
            return false;
        }
    }

    public static class Maps {
        public static boolean isEmpty(Map val) {
            return false;
        }
    }
}
