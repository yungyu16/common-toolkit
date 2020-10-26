package com.github.yungyu16.commom.toolkit.base;

import java.util.Optional;
import java.util.function.Supplier;

public class AssertResult {
    private boolean result;

    private AssertResult(boolean result) {
        this.result = result;
    }

    static AssertResult valueOf(boolean result) {
        return new AssertResult(result);
    }

    public boolean value() {
        return result;
    }

    public AssertResult onTrue(Runnable action) {
        if (result) {
            action.run();
        }
        return this;
    }

    public <T> Optional<T> onTrue(Supplier<T> action) {
        if (result) {
            return Optional.ofNullable(action.get());
        }
        return Optional.empty();
    }

    public AssertResult onFalse(Runnable action) {
        if (!result) {
            action.run();
        }
        return this;
    }

    public <T> Optional<T> onFalse(Supplier<T> action) {
        if (!result) {
            return Optional.ofNullable(action.get());
        }
        return Optional.empty();
    }

    public void throwOnTrue(Supplier<RuntimeException> supplier) {
        throw supplier.get();
    }

    public void throwOnFalse(Supplier<RuntimeException> supplier) {
        throw supplier.get();
    }
}