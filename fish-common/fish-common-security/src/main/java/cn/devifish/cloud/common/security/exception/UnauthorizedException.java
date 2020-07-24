package cn.devifish.cloud.common.security.exception;

/**
 * UnauthorizedException
 *
 * @author Devifish
 * @date 2020/7/24 14:55
 */
public class UnauthorizedException extends FishCloudOAuth2Exception {

    public UnauthorizedException(String msg, Throwable t) {
        super(msg, t);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "unauthorized";
    }

    @Override
    public int getHttpErrorCode() {
        return 401;
    }

}
