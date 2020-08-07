package cn.devifish.cloud.common.core.exception;

import cn.devifish.cloud.common.core.MessageCode;
import cn.devifish.cloud.common.core.StatusCode;

/**
 * BizException
 * 业务异常
 *
 * @author Devifish
 * @date 2020/7/11 17:11
 */
public class BizException extends FishCloudException {

    public BizException(String message) {
        this(MessageCode.BadRequest, message);
    }

    public BizException(String message, Throwable cause) {
        this(MessageCode.BadRequest, message, cause);
    }

    public BizException(StatusCode statusCode, String message) {
        super(statusCode, message);
    }

    public BizException(StatusCode statusCode, String message, Throwable cause) {
        super(statusCode, message, cause);
    }
}
