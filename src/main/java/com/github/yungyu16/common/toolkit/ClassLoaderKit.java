package com.github.yungyu16.common.toolkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * CreatedDate: 2020/12/3
 * Author: songjialin
 */
public class ClassLoaderKit {
    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class loadClass(String className) throws ClassNotFoundException {
        return loadClass(className, getContextClassLoader());
    }

    public static Class loadClass(String className, Class referrer) throws ClassNotFoundException {
        ClassLoader classLoader = getReferrerClassLoader(referrer);
        return loadClass(className, classLoader);
    }

    public static Class<?> loadClass(String className, ClassLoader classLoader)
            throws ClassNotFoundException {
        if (className == null) {
            return null;
        } else {
            Class clazz = null;

            try {
                if (classLoader == null) {
                    clazz = Class.forName(className);
                } else {
                    clazz = Class.forName(className, true, classLoader);
                }
            } catch (ClassNotFoundException var4) {
                clazz = ClassLoaderKit.class.getClassLoader().loadClass(className);
            }

            return clazz;
        }
    }

    public static Class loadServiceClass(String serviceId) throws ClassNotFoundException {
        return loadServiceClass(serviceId, getContextClassLoader());
    }

    public static Class loadServiceClass(String serviceId, Class referrer)
            throws ClassNotFoundException {
        ClassLoader classLoader = getReferrerClassLoader(referrer);
        return loadServiceClass(serviceId, classLoader);
    }

    public static Class loadServiceClass(String serviceId, ClassLoader classLoader)
            throws ClassNotFoundException {
        if (serviceId == null) {
            return null;
        } else {
            serviceId = "META-INF/services/" + serviceId;
            InputStream istream = getResourceAsStream(serviceId, classLoader);
            if (istream == null) {
                throw new RuntimeException("Could not find " + serviceId);
            } else {
                String serviceClassName;
                try {
                    serviceClassName = StringKit
                            .trimToEmpty(StreamKit.readText(istream, "UTF-8"));
                } catch (IOException var5) {
                    throw new RuntimeException("Failed to load " + serviceId, var5);
                }

                return loadClass(serviceClassName, classLoader);
            }
        }
    }

    public static Class loadServiceClass(String className, String serviceId)
            throws ClassNotFoundException {
        return loadServiceClass(className, serviceId, getContextClassLoader());
    }

    public static Class loadServiceClass(String className, String serviceId, Class referrer)
            throws ClassNotFoundException {
        ClassLoader classLoader = getReferrerClassLoader(referrer);
        return loadServiceClass(className, serviceId, classLoader);
    }

    public static Class loadServiceClass(String className, String serviceId, ClassLoader classLoader)
            throws ClassNotFoundException {
        try {
            if (className != null) {
                return loadClass(className, classLoader);
            }
        } catch (ClassNotFoundException var4) {
            throw new RuntimeException(var4);
        }

        return loadServiceClass(serviceId, classLoader);
    }

    private static ClassLoader getReferrerClassLoader(Class referrer) {
        ClassLoader classLoader = null;
        if (referrer != null) {
            classLoader = referrer.getClassLoader();
            if (classLoader == null) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }

        return classLoader;
    }

    public static Object newInstance(String className) throws ClassNotFoundException {
        return newInstance(loadClass(className));
    }

    public static Object newInstance(String className, Class referrer)
            throws ClassNotFoundException {
        return newInstance(loadClass(className, referrer));
    }

    public static Object newInstance(String className, ClassLoader classLoader)
            throws ClassNotFoundException {
        return newInstance(loadClass(className, classLoader));
    }

    private static Object newInstance(Class clazz) {
        if (clazz == null) {
            return null;
        } else {
            try {
                return clazz.newInstance();
            } catch (InstantiationException var2) {
                throw new RuntimeException("Failed to instantiate class: " + clazz.getName(), var2);
            } catch (IllegalAccessException var3) {
                throw new RuntimeException("Failed to instantiate class: " + clazz.getName(), var3);
            } catch (Exception var4) {
                throw new RuntimeException("Failed to instantiate class: " + clazz.getName(), var4);
            }
        }
    }

    public static Object newServiceInstance(String serviceId) throws ClassNotFoundException {
        return newInstance(loadServiceClass(serviceId));
    }

    public static Object newServiceInstance(String serviceId, Class referrer)
            throws ClassNotFoundException {
        return newInstance(loadServiceClass(serviceId, referrer));
    }

    public static Object newServiceInstance(String serviceId, ClassLoader classLoader)
            throws ClassNotFoundException {
        return newInstance(loadServiceClass(serviceId, classLoader));
    }

    public static Object newServiceInstance(String className, String serviceId)
            throws ClassNotFoundException {
        return newInstance(loadServiceClass(className, serviceId));
    }

    public static Object newServiceInstance(String className, String serviceId, Class referrer)
            throws ClassNotFoundException {
        return newInstance(loadServiceClass(className, serviceId, referrer));
    }

    public static Object newServiceInstance(String className, String serviceId,
                                            ClassLoader classLoader) throws ClassNotFoundException {
        return newInstance(loadServiceClass(className, serviceId, classLoader));
    }

    public static URL[] getResources(String resourceName) {
        LinkedList urls = new LinkedList();
        boolean found = false;
        found = getResources(urls, resourceName, getContextClassLoader(), false);
        if (!found) {
            getResources(urls, resourceName, ClassLoaderKit.class.getClassLoader(), false);
        }

        if (!found) {
            getResources(urls, resourceName, (ClassLoader) null, true);
        }

        return getDistinctURLs(urls);
    }

    public static URL[] getResources(String resourceName, Class referrer) {
        ClassLoader classLoader = getReferrerClassLoader(referrer);
        LinkedList urls = new LinkedList();
        getResources(urls, resourceName, classLoader, classLoader == null);
        return getDistinctURLs(urls);
    }

    public static URL[] getResources(String resourceName, ClassLoader classLoader) {
        LinkedList urls = new LinkedList();
        getResources(urls, resourceName, classLoader, classLoader == null);
        return getDistinctURLs(urls);
    }

    private static boolean getResources(List urlSet, String resourceName, ClassLoader classLoader,
                                        boolean sysClassLoader) {
        if (resourceName == null) {
            return false;
        } else {
            Enumeration i = null;

            try {
                if (classLoader != null) {
                    i = classLoader.getResources(resourceName);
                } else if (sysClassLoader) {
                    i = ClassLoader.getSystemResources(resourceName);
                }
            } catch (IOException var6) {
                throw new RuntimeException(var6);
            }

            if (i != null && i.hasMoreElements()) {
                while (i.hasMoreElements()) {
                    urlSet.add(i.nextElement());
                }

                return true;
            } else {
                return false;
            }
        }
    }

    private static URL[] getDistinctURLs(LinkedList urls) {
        if (urls != null && urls.size() != 0) {
            Set urlSet = new HashSet(urls.size());
            Iterator i = urls.iterator();

            while (i.hasNext()) {
                URL url = (URL) i.next();
                if (urlSet.contains(url)) {
                    i.remove();
                } else {
                    urlSet.add(url);
                }
            }

            return (URL[]) ((URL[]) urls.toArray(new URL[urls.size()]));
        } else {
            return new URL[0];
        }
    }

    public static URL getResource(String resourceName) {
        if (resourceName == null) {
            return null;
        } else {
            ClassLoader classLoader = null;
            URL url = null;
            classLoader = getContextClassLoader();
            if (classLoader != null) {
                url = classLoader.getResource(resourceName);
                if (url != null) {
                    return url;
                }
            }

            classLoader = ClassLoaderKit.class.getClassLoader();
            if (classLoader != null) {
                url = classLoader.getResource(resourceName);
                if (url != null) {
                    return url;
                }
            }

            return ClassLoader.getSystemResource(resourceName);
        }
    }

    public static URL getResource(String resourceName, Class referrer) {
        if (resourceName == null) {
            return null;
        } else {
            ClassLoader classLoader = getReferrerClassLoader(referrer);
            return classLoader == null ? ClassLoaderKit.class.getClassLoader().getResource(
                    resourceName) : classLoader.getResource(resourceName);
        }
    }

    public static URL getResource(String resourceName, ClassLoader classLoader) {
        return resourceName == null ? null : (classLoader == null ? ClassLoaderKit.class
                .getClassLoader().getResource(resourceName) : classLoader.getResource(resourceName));
    }

    public static InputStream getResourceAsStream(String resourceName) {
        URL url = getResource(resourceName);

        try {
            if (url != null) {
                return url.openStream();
            }
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }

        return null;
    }

    public static InputStream getResourceAsStream(String resourceName, Class referrer) {
        URL url = getResource(resourceName, referrer);

        try {
            if (url != null) {
                return url.openStream();
            }
        } catch (IOException var4) {
            throw new RuntimeException(var4);
        }

        return null;
    }

    public static InputStream getResourceAsStream(String resourceName, ClassLoader classLoader) {
        URL url = getResource(resourceName, classLoader);

        try {
            if (url != null) {
                return url.openStream();
            }
        } catch (IOException var4) {
            throw new RuntimeException(var4);
        }

        return null;
    }

    public static URL[] whichClasses(String className) {
        return getResources(ClassKit.getClassNameAsResource(className));
    }

    public static URL[] whichClasses(String className, Class referrer) {
        return getResources(ClassKit.getClassNameAsResource(className), referrer);
    }

    public static URL[] whichClasses(String className, ClassLoader classLoader) {
        return getResources(ClassKit.getClassNameAsResource(className), classLoader);
    }

    public static URL whichClass(String className) {
        return getResource(ClassKit.getClassNameAsResource(className));
    }

    public static URL whichClass(String className, Class referrer) {
        return getResource(ClassKit.getClassNameAsResource(className), referrer);
    }

    public static URL whichClass(String className, ClassLoader classLoader) {
        return getResource(ClassKit.getClassNameAsResource(className), classLoader);
    }
}
