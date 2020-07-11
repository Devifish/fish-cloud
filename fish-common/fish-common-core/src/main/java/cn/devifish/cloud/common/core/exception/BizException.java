package cn.devifish.cloud.common.core.exception;

/**
 * BizException
 * 业务异常
 *
 * @author Devifish
 * @date 2020/7/11 17:11
 */
public class BizException extends FishCloudException {

    public BizException() {
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }
}
