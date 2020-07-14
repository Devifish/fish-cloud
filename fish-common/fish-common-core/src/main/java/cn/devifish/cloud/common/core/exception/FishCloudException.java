package cn.devifish.cloud.common.core.exception;

import cn.devifish.cloud.common.core.StatusCode;

/**
 * FishCloudException
 * 框架基础异常，框架派生的异常均需继承
 *
 * @author Devifish
 * @date 2020/7/2 10:15
 */
public class FishCloudException extends RuntimeException {

    private StatusCode statusCode;

    public FishCloudException() {
    }

    public FishCloudException(String message) {
        super(message);
    }

    public FishCloudException(StatusCode statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public FishCloudException(String message, Throwable cause) {
        super(message, cause);
    }

    public FishCloudException(StatusCode statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public FishCloudException(Throwable cause) {
        super(cause);
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }


}
