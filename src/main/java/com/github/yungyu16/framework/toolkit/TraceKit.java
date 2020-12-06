package com.github.yungyu16.framework.toolkit;

import com.google.common.base.Strings;
import org.slf4j.MDC;

import java.util.Optional;
import java.util.UUID;

/**
 * CreatedDate: 2020/12/3
 * Author: songjialin
 */
public class TraceKit {
    public static final String MDC_TRACE_ID_KEY = "traceId";

    public static Optional<String> getTraceId() {
        return Optional.ofNullable(Strings.emptyToNull(MDC.get(MDC_TRACE_ID_KEY)));
    }

    public static String resetTraceId() {
        return resetTraceId(null);
    }

    public static String resetTraceId(String traceId) {
        if (StringKit.isBlank(traceId)) {
            traceId = UUID.randomUUID().toString().replace("-", "");
        } else {
            traceId = traceId.trim();
        }
        MDC.put(MDC_TRACE_ID_KEY, traceId);
        return traceId;
    }

    public static void clearTraceId() {
        MDC.remove(MDC_TRACE_ID_KEY);
    }
}
