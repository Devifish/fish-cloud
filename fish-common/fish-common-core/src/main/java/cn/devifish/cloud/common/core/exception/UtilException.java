package cn.devifish.cloud.common.core.exception;

/**
 * UtilException
 * 框架工具异常
 *
 * @author Devifish
 * @date 2020/7/3 10:51
 */
public class UtilException extends FishCloudException {

    public UtilException() {
    }

    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilException(Throwable cause) {
        super(cause);
    }
}
