package com.github.yungyu16.framework.toolkit;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Verify;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

/**
 * CreatedDate: 2020/12/5
 * Author: songjialin
 */
@Slf4j
public final class HttpKit {
    public static final MediaType STR_JSON_UTF_8 = MediaType.parse(com.google.common.net.MediaType.JSON_UTF_8.toString());
    public static final MediaType MEDIA_TYPE_JSON_UTF_8 = STR_JSON_UTF_8;
    private static OkHttpClient HTTP_CLIENT;

    private HttpKit() {
    }

    public static OkHttpClient getHttpClient() {
        if (HTTP_CLIENT == null) {
            synchronized (HttpKit.class) {
                if (HTTP_CLIENT == null) {
                    HttpLoggingInterceptor loggingInterceptor = newLoggingInterceptor();
                    HTTP_CLIENT = new OkHttpClient.Builder()
                            .connectTimeout(SystemKit.getInteger(configKey("connectTimeout"), 5), TimeUnit.SECONDS)
                            .readTimeout(SystemKit.getInteger(configKey("readTimeout"), 5), TimeUnit.SECONDS)
                            .writeTimeout(SystemKit.getInteger(configKey("writeTimeout"), 5), TimeUnit.SECONDS)
                            .addInterceptor(loggingInterceptor)
                            .build();
                }
            }
        }
        return HTTP_CLIENT;
    }

    public static Request.Builder newRequest(String url) {
        return newRequest(url, null, null);
    }

    public static Request.Builder newRequest(String url, Map<String, String> queryParams) {
        return newRequest(url, queryParams, null);
    }

    public static Request.Builder newRequest(String url, Map<String, String> queryParams, Map<String, String> headers) {
        Verify.verifyNotNull(url);
        url = url.trim();
        Request.Builder builder = new Request.Builder();
        if (!CollectionKit.isEmpty(headers)) {
            headers.forEach(builder::header);
        }
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (httpUrl == null) {
            throw new IllegalArgumentException("url不合法");
        }
        if (!CollectionKit.isEmpty(queryParams)) {
            HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
            queryParams.forEach(urlBuilder::addQueryParameter);
            httpUrl = urlBuilder.build();
        }
        return builder.url(httpUrl);
    }

    public static RequestBody newJsonRequestBody(Object object) {
        Verify.verifyNotNull(object);
        byte[] bytes = JsonKit.toJSONBytes(object);
        return RequestBody.create(MEDIA_TYPE_JSON_UTF_8, bytes);
    }

    public static Optional<JsonNode> get(String url) {
        return invoke(newRequest(url).get());
    }

    public static <T> Optional<T> get(String url, Class<T> type) {
        return invoke(newRequest(url).get(), type);
    }

    public static Optional<JsonNode> post(String url, Object body) {
        Request.Builder post = newRequest(url).post(newJsonRequestBody(body));
        return invoke(post);
    }

    public static <T> Optional<T> post(String url, Object body, Class<T> type) {
        Request.Builder post = newRequest(url).post(newJsonRequestBody(body));
        return invoke(post, type);
    }

    @SneakyThrows
    public static Optional<JsonNode> invoke(Request.Builder request) {
        return invoke(request, JsonNode.class);
    }

    @SneakyThrows
    public static <T> Optional<T> invoke(Request.Builder request, Class<T> type) {
        return invoke(request, response -> {
            if (response.isSuccessful()) {
                byte[] bytes = response.body().bytes();
                T obj = JsonKit.parseObject(bytes, type);
                return Optional.ofNullable(obj);
            }
            return Optional.empty();
        });
    }

    @SneakyThrows
    public static <T> T invoke(Request.Builder request, ResponseMapper<T> mapper) {
        Verify.verifyNotNull(request);
        Verify.verifyNotNull(mapper);
        try (Response response = getHttpClient().newCall(request.build()).execute()) {
            return mapper.apply(response);
        }
    }

    private static HttpLoggingInterceptor newLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(log::info);
        HttpLoggingInterceptor.Level level = BODY;
        String configKey = configKey("logLevel");
        try {
            level = SystemKit.getConfig(configKey, HttpLoggingInterceptor.Level::valueOf)
                    .orElse(BODY);
        } catch (Exception e) {
            log.error("读取{}配置异常,允许的配置值有：{}", configKey, Arrays.toString(HttpLoggingInterceptor.Level.values()));
        }
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }

    private static String configKey(String key) {
        return HttpKit.class.getCanonicalName() + "." + key;
    }

    public static interface ResponseMapper<T> {
        T apply(Response response) throws IOException;
    }
}
