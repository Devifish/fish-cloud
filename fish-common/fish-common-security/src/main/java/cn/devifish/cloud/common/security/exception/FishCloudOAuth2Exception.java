package cn.devifish.cloud.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * FishCloudOAuth2Exception
 *
 * @author Devifish
 * @date 2020/7/24 15:24
 */
@JsonSerialize(using = FishCloudOAuth2ExceptionSerializer.class)
public class FishCloudOAuth2Exception extends OAuth2Exception {

    private final int httpErrorCode;

    public FishCloudOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
        this.httpErrorCode = 400;
    }

    public FishCloudOAuth2Exception(String msg) {
        super(msg);
        this.httpErrorCode = 400;
    }

    public FishCloudOAuth2Exception(String msg, int httpErrorCode, Throwable t) {
        super(msg, t);
        this.httpErrorCode = httpErrorCode;
    }

    @Override
    public int getHttpErrorCode() {
        return httpErrorCode;
    }
}
