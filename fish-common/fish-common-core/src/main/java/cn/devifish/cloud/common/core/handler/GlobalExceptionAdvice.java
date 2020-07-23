package cn.devifish.cloud.common.core.handler;

import cn.devifish.cloud.common.core.MessageCode;
import cn.devifish.cloud.common.core.RestfulEntity;
import cn.devifish.cloud.common.core.exception.FishCloudException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

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
@ConditionalOnClass(HttpServletRequest.class)
public class GlobalExceptionAdvice {

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
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestfulEntity<?> illegalArgumentException(FishCloudException exception) {
        return RestfulEntity.error(exception);
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
     * 请求缺少必要参数异常
     *
     * @param exception exception
     * @return error
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public RestfulEntity<?> missingServletRequestParameterException(MissingServletRequestParameterException exception) {
        var parameterName = exception.getParameterName();
        return RestfulEntity.error(MessageCode.PreconditionFailed, "缺少必要参数: " + parameterName);
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

}
