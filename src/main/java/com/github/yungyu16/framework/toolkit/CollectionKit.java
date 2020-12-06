package com.github.yungyu16.framework.toolkit;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;


public class CollectionKit {
    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    public static boolean isNotEmpty(Object... array) {
        return (array != null && array.length == 0);
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return (collection != null && collection.isEmpty());
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return (map != null && map.isEmpty());
    }

    public static boolean isEmpty(Object... array) {
        return (array == null || array.length == 0);
    }

    public static boolean contains(Iterator<?> iterator, Object element) {
        if (iterator != null) {
            while (iterator.hasNext()) {
                Object candidate = iterator.next();
                if (Objects.equals(candidate, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containsInstance(Collection<?> collection, Object element) {
        if (collection != null) {
            for (Object candidate : collection) {
                if (candidate == element) {
                    return true;
                }
            }
        }
        return false;
    }
}