package cn.devifish.cloud.common.core.handler;

import cn.devifish.cloud.common.core.MessageCode;
import cn.devifish.cloud.common.core.RestfulEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionAdvice
 * Spring MVC 全局异常处理
 *
 * @author Devifish
 * @date 2020/7/1 18:29
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

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
