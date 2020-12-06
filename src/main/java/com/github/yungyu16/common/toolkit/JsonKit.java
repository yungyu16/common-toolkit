package com.github.yungyu16.common.toolkit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

/**
 * CreatedDate: 2020/12/5
 * Author: songjialin
 */
public final class JsonKit {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonKit() {
    }

    @SneakyThrows
    public static JsonNode parseObject(String text) {
        return OBJECT_MAPPER.readTree(text);
    }

    @SneakyThrows
    public static <T> T parseObject(String text, Class<T> type) {
        return OBJECT_MAPPER.readValue(text, type);
    }

    @SneakyThrows
    public static <T> T parseObject(String text, TypeReference<T> type) {
        return OBJECT_MAPPER.readValue(text, type);
    }

    @SneakyThrows
    public static JsonNode parseObject(byte[] bytes) {
        return OBJECT_MAPPER.readTree(bytes);
    }

    @SneakyThrows
    public static <T> T parseObject(byte[] bytes, Class<T> type) {
        return OBJECT_MAPPER.readValue(bytes, type);
    }

    @SneakyThrows
    public static <T> T parseObject(byte[] bytes, TypeReference<T> type) {
        return OBJECT_MAPPER.readValue(bytes, type);
    }

    @SneakyThrows
    public static String toJSONString(Object object) {
        return toJSONString(object, false);
    }

    @SneakyThrows
    public static String toJSONString(Object object, boolean pretty) {
        if (pretty) {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        }
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    @SneakyThrows
    public static byte[] toJSONBytes(Object object) {
        return OBJECT_MAPPER.writeValueAsBytes(object);
    }
}
