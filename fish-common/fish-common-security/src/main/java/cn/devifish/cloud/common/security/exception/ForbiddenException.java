package cn.devifish.cloud.common.security.exception;

/**
 * ForbiddenException
 *
 * @author Devifish
 * @date 2020/7/24 14:53
 */
public class ForbiddenException extends FishCloudOAuth2Exception {

    public ForbiddenException(String msg, Throwable t) {
        super(msg, t);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "access_denied";
    }

    @Override
    public int getHttpErrorCode() {
        return 403;
    }

}
