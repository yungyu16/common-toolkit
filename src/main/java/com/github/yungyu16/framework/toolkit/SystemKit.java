package com.github.yungyu16.framework.toolkit;

import com.google.common.base.Verify;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Function;

/**
 * CreatedDate: 2020/12/5
 * Author: songjialin
 */
@Slf4j
public final class SystemKit {
    private SystemKit() {
    }

    public static Optional<Integer> getInteger(String configKey) {
        return getConfig(configKey, Integer::valueOf);
    }

    public static Integer getInteger(String configKey, Integer def) {
        return getConfig(configKey, Integer::valueOf, def);
    }

    public static <T> T getConfig(String configKey, Function<String, T> mapper, T def) {
        return getConfig(configKey, mapper).orElse(def);
    }

    public static <T> Optional<T> getConfig(String configKey, Function<String, T> mapper) {
        Verify.verifyNotNull(configKey);
        Verify.verifyNotNull(mapper);
        String configVal = Optional.ofNullable(System.getenv(configKey))
                .orElseGet(() -> System.getProperty(configKey));
        log.info("{} : {}", configKey, configVal);
        if (StringKit.isBlank(configVal)) {
            return Optional.empty();
        }
        T result = mapper.apply(configVal.trim());
        return Optional.ofNullable(result);
    }
}
