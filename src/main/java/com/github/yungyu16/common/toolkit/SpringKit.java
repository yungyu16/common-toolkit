package com.github.yungyu16.common.toolkit;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ResolvableType;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Optional;

/**
 * CreatedDate: 2020/9/17
 * Author: songjialin
 */
public class SpringKit implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static ConfigurableApplicationContext applicationContext;

    public static Optional<ApplicationContext> getApplicationContext() {
        return Optional.ofNullable(applicationContext);
    }

    public static boolean isFactoryDereference(String name) {
        return BeanFactoryUtils.isFactoryDereference(name);
    }

    public static String transformedBeanName(String name) {
        return BeanFactoryUtils.transformedBeanName(name);
    }

    public static boolean isGeneratedBeanName(String name) {
        return BeanFactoryUtils.isGeneratedBeanName(name);
    }

    public static String originalBeanName(String name) {
        return BeanFactoryUtils.originalBeanName(name);
    }

    public static int countBeansIncludingAncestors() {
        ListableBeanFactory lbf = applicationContext;
        return BeanFactoryUtils.countBeansIncludingAncestors(lbf);
    }

    public static String[] beanNamesIncludingAncestors() {
        ListableBeanFactory lbf = applicationContext;
        return BeanFactoryUtils.beanNamesIncludingAncestors(lbf);
    }

    public static String[] beanNamesForTypeIncludingAncestors(ResolvableType type) {
        ListableBeanFactory lbf = applicationContext;
        return BeanFactoryUtils.beanNamesForTypeIncludingAncestors(lbf, type);
    }

    public static String[] beanNamesForTypeIncludingAncestors(Class<?> type) {
        ListableBeanFactory lbf = applicationContext;
        return BeanFactoryUtils.beanNamesForTypeIncludingAncestors(lbf, type);
    }

    public static String[] beanNamesForTypeIncludingAncestors(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
        ListableBeanFactory lbf = applicationContext;
        return BeanFactoryUtils.beanNamesForTypeIncludingAncestors(lbf, type, includeNonSingletons, allowEagerInit);
    }

    public static String[] beanNamesForAnnotationIncludingAncestors(Class<? extends Annotation> annotationType) {
        ListableBeanFactory lbf = applicationContext;
        return BeanFactoryUtils.beanNamesForAnnotationIncludingAncestors(lbf, annotationType);
    }

    public static <T> Map<String, T> beansOfTypeIncludingAncestors(Class<T> type) throws BeansException {
        ListableBeanFactory lbf = applicationContext;
        return BeanFactoryUtils.beansOfTypeIncludingAncestors(lbf, type);
    }

    public static <T> Map<String, T> beansOfTypeIncludingAncestors(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
        ListableBeanFactory lbf = applicationContext;
        return BeanFactoryUtils.beansOfTypeIncludingAncestors(lbf, type, includeNonSingletons, allowEagerInit);
    }

    public static <T> T beanOfTypeIncludingAncestors(Class<T> type) throws BeansException {
        ListableBeanFactory lbf = applicationContext;
        return BeanFactoryUtils.beanOfTypeIncludingAncestors(lbf, type);
    }

    public static <T> T beanOfTypeIncludingAncestors(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
        ListableBeanFactory lbf = applicationContext;
        return BeanFactoryUtils.beanOfTypeIncludingAncestors(lbf, type, includeNonSingletons, allowEagerInit);
    }

    public static <T> T beanOfType(Class<T> type) throws BeansException {
        ListableBeanFactory lbf = applicationContext;
        return BeanFactoryUtils.beanOfType(lbf, type);
    }

    public static <T> T beanOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
        ListableBeanFactory lbf = applicationContext;
        return BeanFactoryUtils.beanOfType(lbf, type, includeNonSingletons, allowEagerInit);
    }

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        applicationContext = configurableApplicationContext;
    }
}
