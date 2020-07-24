package cn.devifish.cloud.common.security.exception;

/**
 * MethodNotAllowed
 *
 * @author Devifish
 * @date 2020/7/24 14:56
 */
public class MethodNotAllowed extends FishCloudOAuth2Exception {

    public MethodNotAllowed(String msg, Throwable t) {
        super(msg, t);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "method_not_allowed";
    }

    @Override
    public int getHttpErrorCode() {
        return 405;
    }

}
