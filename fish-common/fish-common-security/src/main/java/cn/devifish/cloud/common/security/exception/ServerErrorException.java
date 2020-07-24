package cn.devifish.cloud.common.security.exception;

/**
 * ServerErrorException
 *
 * @author Devifish
 * @date 2020/7/24 14:54
 */
public class ServerErrorException extends FishCloudOAuth2Exception {

    public ServerErrorException(String msg, Throwable t) {
        super(msg, t);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "server_error";
    }

    @Override
    public int getHttpErrorCode() {
        return 500;
    }

}
