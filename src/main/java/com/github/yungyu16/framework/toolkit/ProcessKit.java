package com.github.yungyu16.framework.toolkit;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * CreatedDate: 2020/12/3
 * Author: songjialin
 */
public class ProcessKit {
    public static final String DEFAULT_PROCESSID = "-";
    private static volatile String processId;

    public static String getProcessId() {
        try {
            if (!StringKit.isBlank(processId)) {
                return processId;
            }
            // LOG4J2-2126 use reflection to improve compatibility with Android Platform which does not support JMX extensions
            Class<?> managementFactoryClass = Class
                    .forName("java.lang.management.ManagementFactory");
            Method getRuntimeMXBean = managementFactoryClass.getDeclaredMethod("getRuntimeMXBean");
            Class<?> runtimeMXBeanClass = Class.forName("java.lang.management.RuntimeMXBean");
            Method getName = runtimeMXBeanClass.getDeclaredMethod("getName");

            Object runtimeMXBean = getRuntimeMXBean.invoke(null);
            String name = (String) getName.invoke(runtimeMXBean);
            //String name = ManagementFactory.getRuntimeMXBean().getName(); //JMX not allowed on Android
            processId = name.split("@")[0]; // likely works on most platforms
            return processId;
        } catch (final Exception ex) {
            try {
                processId = new File("/proc/self").getCanonicalFile().getName(); // try a Linux-specific way
                return processId;
            } catch (final IOException ignoredUseDefault) {
                // Ignore exception.
            }
        }
        return DEFAULT_PROCESSID;
    }
}
