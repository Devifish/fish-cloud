package cn.devifish.cloud.common.core;

import cn.devifish.cloud.common.core.exception.FishCloudException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

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
        return new RestfulEntity<>(MessageCode.Ok);
    }

    public static <T> RestfulEntity<T> ok(T data) {
        return new RestfulEntity<>(data, MessageCode.Ok);
    }

    public static <T> RestfulEntity<T> error(StatusCode statusCode) {
        return new RestfulEntity<>(statusCode);
    }

    public static <T> RestfulEntity<T> error(StatusCode statusCode, String message) {
        return new RestfulEntity<>(null, statusCode.getCode(), message);
    }

    public static <T> RestfulEntity<T> error(FishCloudException exception) {
        StatusCode statusCode = exception.getStatusCode();
        String message = exception.getMessage();

        return StringUtils.isEmpty(message)
                ? error(statusCode)
                : error(statusCode, message);
    }

}
