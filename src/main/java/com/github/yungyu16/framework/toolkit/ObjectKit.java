package com.github.yungyu16.framework.toolkit;


import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * CreatedDate: 2020/12/3
 * Author: songjialin
 */
public class ObjectKit {
    public static final Object NULL = new Serializable() {
        private static final long serialVersionUID = 7092611880189329093L;

        private Object readResolve() {
            return ObjectKit.NULL;
        }
    };

    public static Object defaultIfNull(Object object, Object defaultValue) {
        return object != null ? object : defaultValue;
    }

    public static boolean equals(Object object1, Object object2) {
        return ArrayKit.equals(object1, object2);
    }

    public static int hashCode(Object object) {
        return ArrayKit.hashCode(object);
    }

    public static int identityHashCode(Object object) {
        return object == null ? 0 : System.identityHashCode(object);
    }

    public static String identityToString(Object object) {
        return object == null ? null : appendIdentityToString((StringBuffer) null, object)
                .toString();
    }

    public static String identityToString(Object object, String nullStr) {
        return object == null ? nullStr : appendIdentityToString((StringBuffer) null, object)
                .toString();
    }

    public static StringBuffer appendIdentityToString(StringBuffer buffer, Object object) {
        if (object == null) {
            return null;
        } else {
            if (buffer == null) {
                buffer = new StringBuffer();
            }

            buffer.append(ClassKit.getClassNameForObject(object));
            return buffer.append('@').append(Integer.toHexString(identityHashCode(object)));
        }
    }

    public static Object clone(Object array) {
        if (array == null) {
            return null;
        } else if (array instanceof Object[]) {
            return ArrayKit.clone((Object[]) ((Object[]) array));
        } else if (array instanceof long[]) {
            return ArrayKit.clone((long[]) ((long[]) array));
        } else if (array instanceof int[]) {
            return ArrayKit.clone((int[]) ((int[]) array));
        } else if (array instanceof short[]) {
            return ArrayKit.clone((short[]) ((short[]) array));
        } else if (array instanceof byte[]) {
            return ArrayKit.clone((byte[]) ((byte[]) array));
        } else if (array instanceof double[]) {
            return ArrayKit.clone((double[]) ((double[]) array));
        } else if (array instanceof float[]) {
            return ArrayKit.clone((float[]) ((float[]) array));
        } else if (array instanceof boolean[]) {
            return ArrayKit.clone((boolean[]) ((boolean[]) array));
        } else if (array instanceof char[]) {
            return ArrayKit.clone((char[]) ((char[]) array));
        } else if (!(array instanceof Cloneable)) {
            throw new RuntimeException("Object of class " + array.getClass().getName()
                    + " is not Cloneable");
        } else {
            Class clazz = array.getClass();

            try {
                Method cloneMethod = clazz.getMethod("clone", ArrayKit.EMPTY_CLASS_ARRAY);
                return cloneMethod.invoke(array, ArrayKit.EMPTY_OBJECT_ARRAY);
            } catch (NoSuchMethodException var3) {
                throw new RuntimeException(var3);
            } catch (IllegalArgumentException var4) {
                throw new RuntimeException(var4);
            } catch (IllegalAccessException var5) {
                throw new RuntimeException(var5);
            } catch (InvocationTargetException var6) {
                throw new RuntimeException(var6);
            }
        }
    }

    public static boolean isSameType(Object object1, Object object2) {
        return object1 != null && object2 != null ? object1.getClass().equals(object2.getClass())
                : true;
    }

    public static String toString(Object object) {
        return object == null ? "" : (object.getClass().isArray() ? ArrayKit.toString(object)
                : object.toString());
    }

    public static String toString(Object object, String nullStr) {
        return object == null ? nullStr : (object.getClass().isArray() ? ArrayKit.toString(object)
                : object.toString());
    }
}
