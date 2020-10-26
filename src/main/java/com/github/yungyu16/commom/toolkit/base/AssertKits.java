package com.github.yungyu16.commom.toolkit.base;

import java.util.function.Supplier;

/**
 * CreatedDate: 2020/10/26
 * Author: songjialin
 */
public class AssertKits {

    public static class Base {

    }

    public static class String {
        public static boolean isBlank(String val) {
            return false;
        }
    }

    public static class Array {
        public static boolean isEmpty(Object[] val) {
            return false;
        }
    }

    public static class Collection {
        public static boolean isEmpty(Collection val) {
            return false;
        }
    }

    public static class Map {
        public static boolean isEmpty(Map val) {
            return false;
        }
    }


    public class AssertResult {
        private boolean result;

        public AssertResult onTrue(Runnable action) {
            if (result) {
                action.run();
            }
            return this;
        }

        public AssertResult onFalse(Runnable action) {
            if (!result) {
                action.run();
            }
            return this;
        }

        public void throwOnTrue(Supplier<RuntimeException> supplier) {
            throw supplier.get();
        }

        public void throwOnFalse(Supplier<RuntimeException> supplier) {
            throw supplier.get();
        }
    }
}
