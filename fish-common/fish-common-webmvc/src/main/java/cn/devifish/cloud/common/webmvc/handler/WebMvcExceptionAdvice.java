package cn.devifish.cloud.common.webmvc.handler;

import cn.devifish.cloud.common.core.MessageCode;
import cn.devifish.cloud.common.core.RestfulEntity;
import cn.devifish.cloud.common.core.exception.BaseExceptionAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.Arrays;

/**
 * WebMvcExceptionAdvice
 * WebMvc异常处理
 *
 * @author Devifish
 * @date 2020/7/1 18:29
 */
@Slf4j
@RestControllerAdvice
public class WebMvcExceptionAdvice extends BaseExceptionAdvice {

    /**
     * 请求未授权访问异常
     *
     * @return error
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public RestfulEntity<?> accessDeniedException() {
        return RestfulEntity.error(MessageCode.Forbidden);
    }

    /**
     * 请求类型不支持异常
     *
     * @param exception exception
     * @return error
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestfulEntity<?> requestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        var supportedMethods = exception.getSupportedMethods();
        return RestfulEntity.error(MessageCode.BadRequest, "请求方式仅支持: " + Arrays.toString(supportedMethods));
    }

    /**
     * 请求缺少必要参数异常
     *
     * @param exception exception
     * @return error
     */
    @ExceptionHandler({MissingServletRequestParameterException.class, MissingServletRequestPartException.class})
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public RestfulEntity<?> missingServletRequestParameterException(Exception exception) {
        var parameterName = exception instanceof MissingServletRequestParameterException
            ? ((MissingServletRequestParameterException) exception).getParameterName()
            : exception instanceof MissingServletRequestPartException
            ? ((MissingServletRequestPartException) exception).getRequestPartName()
            : "null";

        return RestfulEntity.error(MessageCode.PreconditionFailed, "缺少必要参数: " + parameterName);
    }

}
