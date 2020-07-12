package cn.devifish.cloud.common.core.handler;

import cn.devifish.cloud.common.core.MessageCode;
import cn.devifish.cloud.common.core.RestfulEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * GlobalExceptionAdvice
 * Spring MVC 全局异常处理
 *
 * @author Devifish
 * @date 2020/7/1 18:29
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionAdvice {

    private final HttpServletRequest request;

    /**
     * 全局异常默认处理
     *
     * @param exception exception
     * @return error
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestfulEntity<Void> exception(Exception exception) {
        log.error(exception.getMessage(), exception);
        return RestfulEntity.error(MessageCode.InternalServerError);
    }

    /**
     * 请求类型不支持异常
     *
     * @param exception exception
     * @return error
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestfulEntity<Void> requestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        log.warn("URI: {} 不支持 [{}] 请求", request.getRequestURI(), exception.getMethod());
        return RestfulEntity.error(MessageCode.BadRequest);
    }

    /**
     * 消息无法读取异常
     *
     * @param exception exception
     * @return error
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public RestfulEntity<Void> messageNotReadableException(HttpMessageNotReadableException exception) {
        log.warn(exception.getMessage());
        return RestfulEntity.error(MessageCode.PreconditionFailed, "参数不匹配");
    }

    /**
     * 请求缺少必要参数异常
     *
     * @param exception exception
     * @return error
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public RestfulEntity<Void> missingServletRequestParameterException(MissingServletRequestParameterException exception) {
        log.warn(exception.getMessage());
        return RestfulEntity.error(MessageCode.PreconditionFailed, "缺少必要参数: " + exception.getParameterName());
    }

}
