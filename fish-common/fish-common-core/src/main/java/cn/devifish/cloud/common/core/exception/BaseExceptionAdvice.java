package cn.devifish.cloud.common.core.exception;

import cn.devifish.cloud.common.core.MessageCode;
import cn.devifish.cloud.common.core.RestfulEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import java.text.MessageFormat;

/**
 * BaseExceptionAdvice
 * 基础异常处理器
 *
 * @author Devifish
 * @date 2020/7/1 18:29
 */
@Slf4j
public abstract class BaseExceptionAdvice {

    /**
     * 全局异常默认处理
     *
     * @param exception exception
     * @return error
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestfulEntity<?> exception(Exception exception) {
        log.error(exception.getMessage(), exception);
        return RestfulEntity.error(MessageCode.InternalServerError);
    }

    /**
     * 系统框架异常信息拦截
     *
     * @param exception exception
     * @return error
     */
    @ExceptionHandler(FishCloudException.class)
    public ResponseEntity<RestfulEntity<?>> illegalArgumentException(FishCloudException exception) {
        var statusCode = exception.getStatusCode();
        if (statusCode == null || statusCode == MessageCode.Other)
            statusCode = MessageCode.InternalServerError;

        return ResponseEntity.status(statusCode.getCode())
            .body(RestfulEntity.error(exception));
    }

    /**
     * 消息无法读取异常
     *
     * @return error
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public RestfulEntity<?> messageNotReadableException() {
        return RestfulEntity.error(MessageCode.PreconditionFailed, "参数不匹配");
    }

    /**
     * 数据校验异常
     *
     * @param exception exception
     * @return error
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public RestfulEntity<?> validExceptionHandler(Exception exception) {
        BindingResult bindingResult = exception instanceof MethodArgumentNotValidException
            ? ((MethodArgumentNotValidException) exception).getBindingResult()
            : ((BindException) exception).getBindingResult();

        return bindingResult.getFieldErrors()
            .stream()
            .map(error -> {
                String message = error.getField() + ": " + error.getDefaultMessage();
                return RestfulEntity.error(MessageCode.PreconditionFailed, message);
            })
            .findFirst()
            .orElse(RestfulEntity.error(MessageCode.PreconditionFailed));
    }

    /**
     * Multipart表单异常
     *
     * @param exception exception
     * @return error
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MultipartException.class)
    public RestfulEntity<?> multipartExceptionHandle(MultipartException exception) {
        if (exception instanceof MaxUploadSizeExceededException) {
            var maxUploadSize = ((MaxUploadSizeExceededException) exception).getMaxUploadSize();
            var message = MessageFormat.format("超过最大上传文件大小 {0} bytes",
                maxUploadSize > 0 ? maxUploadSize : "unknown");

            return RestfulEntity.error(MessageCode.BadRequest, message);
        }

        // 未知异常返回异常消息
        return RestfulEntity.error(MessageCode.BadRequest, exception.getMessage());
    }

}
