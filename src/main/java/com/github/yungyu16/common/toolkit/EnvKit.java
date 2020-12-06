package com.github.yungyu16.common.toolkit;

import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;

import java.util.Arrays;
import java.util.Optional;

/**
 * CreatedDate: 2020/9/17
 * Author: songjialin
 */
public final class EnvKit {
    private EnvKit() {
    }

    public static Optional<Environment> getSpringEnv() {
        return SpringKit.getApplicationContext().map(EnvironmentCapable::getEnvironment);
    }

    public static boolean acceptsProfiles(Env... envs) {
        if (ArrayKit.isEmpty(envs)) {
            return false;
        }
        String[] profiles = Arrays.stream(envs).map(Enum::name).toArray(String[]::new);
        return getSpringEnv().map(it -> it.acceptsProfiles(profiles)).orElse(false);
    }

    public static boolean isLocal() {
        return acceptsProfiles(Env.local);
    }

    public static boolean isDev() {
        return acceptsProfiles(Env.dev);
    }

    public static boolean isTest() {
        return acceptsProfiles(Env.test);
    }

    public static boolean isPre() {
        return acceptsProfiles(Env.pre);
    }

    public static boolean isPrd() {
        return acceptsProfiles(Env.prd);
    }

    public static enum Env {
        local, dev, test, pre, prd;
    }
}
