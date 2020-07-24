package cn.devifish.cloud.common.security.error;

import cn.devifish.cloud.common.security.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.io.IOException;

/**
 * OAuth2SecurityExceptionTranslator
 * OAuth2安全异常内容转换器
 *
 * @author Devifish
 * @date 2020/7/24 11:21
 * @see org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator
 */
@Component
public class OAuth2SecurityExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    private final ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {

        // Try to extract a SpringSecurityException from the stacktrace
        var causeChain = throwableAnalyzer.determineCauseChain(e);
        var ase = throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        if (ase instanceof OAuth2Exception) {
            return handleOAuth2Exception((OAuth2Exception) ase);
        }

        // UnauthorizedException
        ase = throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);
        if (ase instanceof AuthenticationException) {
            return handleOAuth2Exception(new UnauthorizedException(e.getMessage(), e));
        }

        // ForbiddenException
        ase = throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
        if (ase instanceof AccessDeniedException) {
            return handleOAuth2Exception(new ForbiddenException(ase.getMessage(), ase));
        }

        // MethodNotAllowed
        ase = throwableAnalyzer.getFirstThrowableOfType(HttpRequestMethodNotSupportedException.class, causeChain);
        if (ase instanceof HttpRequestMethodNotSupportedException) {
            return handleOAuth2Exception(new MethodNotAllowed(ase.getMessage(), ase));
        }

        return handleOAuth2Exception(new ServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));
    }

    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception exception) throws IOException {
        var status = exception.getHttpErrorCode();
        var headers = new HttpHeaders();
        if (status == HttpStatus.UNAUTHORIZED.value() || (exception instanceof InsufficientScopeException))
            headers.set(HttpHeaders.WWW_AUTHENTICATE,
                    String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, exception.getSummary()));

        // 统一异常类型为FishCloudOAuth2Exception
        if (!(exception instanceof FishCloudOAuth2Exception))
            exception = new FishCloudOAuth2Exception(exception.getMessage(), status, exception);

        return ResponseEntity.status(status)
                .headers(headers)
                .body(exception);
    }



}
