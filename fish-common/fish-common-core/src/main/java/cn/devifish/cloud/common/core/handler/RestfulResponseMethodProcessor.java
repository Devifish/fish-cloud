package cn.devifish.cloud.common.core.handler;

import cn.devifish.cloud.common.core.RestfulEntity;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.io.IOException;
import java.util.List;

/**
 * RestfulResponseMethodProcessor
 * Restful Response转换处理器
 * 重写RequestResponseBodyMethodProcessor
 * 自动将Method返回值转换为 Restful API风格
 *
 * @author Devifish
 * @date 2020/7/6 18:27
 * @see org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor
 * @see org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor
 */
public class RestfulResponseMethodProcessor extends RequestResponseBodyMethodProcessor {

    /**
     * 仅使用handleReturnValue无需使用其他参数
     *
     * @param converters 消息转换器
     */
    public RestfulResponseMethodProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    /**
     * 将Spring MVC {@link ResponseBody}方法的返回数据
     * 使用 {@link RestfulEntity} 进行包装
     *
     * @param returnValue  Object
     * @param returnType   MethodParameter
     * @param mavContainer ModelAndViewContainer
     * @param webRequest   NativeWebRequest
     * @throws IOException                         IOException
     * @throws HttpMediaTypeNotAcceptableException HttpMediaTypeNotAcceptableException
     * @throws HttpMessageNotWritableException     HttpMessageNotWritableException
     */
    @Override
    @SuppressWarnings("NullableProblems")
    public void handleReturnValue(
            Object returnValue, MethodParameter returnType,
            ModelAndViewContainer mavContainer, NativeWebRequest webRequest)
            throws IOException, HttpMediaTypeNotAcceptableException, HttpMessageNotWritableException {

        // 如果返回参数为 RestfulEntity 则不再包装
        if (!(returnValue instanceof RestfulEntity)) {
            returnValue = RestfulEntity.ok(returnValue);
        }
        super.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }

    /**
     * 替换Spring MVC ResponseBody默认处理方式
     * 适用于List.replaceAll(this::replaceDefault)使用
     *
     * @param handler Handler列表Item
     * @return HandlerMethodReturnValueHandler
     */
    public HandlerMethodReturnValueHandler replaceDefault(HandlerMethodReturnValueHandler handler) {
        return handler instanceof RequestResponseBodyMethodProcessor
                ? this
                : handler;
    }

}
