package cn.devifish.cloud.common.core;

import lombok.Data;

import java.io.Serializable;

/**
 * RestfulEntity
 * Restful请求的响应结果
 *
 * @author Devifish
 * @date 2020/7/7 14:38
 */
@Data
public class RestfulEntity<E> implements Serializable {

    private E data;
    private int status;
    private String message;

    public RestfulEntity(E data, int status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public RestfulEntity(E data, StatusCode statusCode) {
        this(data, statusCode.getCode(), statusCode.getDesc());
    }

    public RestfulEntity(StatusCode statusCode) {
        this(null, statusCode);
    }

    public static <T> RestfulEntity<T> ok() {
        return new RestfulEntity<>(ErrorStatus.Ok);
    }

    public static <T> RestfulEntity<T> ok(T data) {
        return new RestfulEntity<>(data, ErrorStatus.Ok);
    }
}
