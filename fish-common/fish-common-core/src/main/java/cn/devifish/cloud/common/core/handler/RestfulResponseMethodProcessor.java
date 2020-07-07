package cn.devifish.cloud.common.core.handler;

import cn.devifish.cloud.common.core.RestfulEntity;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor;

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
@SuppressWarnings("NullableProblems")
public class RestfulResponseMethodProcessor extends AbstractMessageConverterMethodProcessor {

    public RestfulResponseMethodProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return null;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ResponseBody.class) ||
                returnType.hasMethodAnnotation(ResponseBody.class));
    }

    /**
     * 将Spring MVC {@link ResponseBody}方法的返回数据
     * 使用 {@link RestfulEntity} 进行包装
     *
     * @param returnValue  Object
     * @param returnType   MethodParameter
     * @param mavContainer ModelAndViewContainer
     * @param webRequest   NativeWebRequest
     * @throws Exception Exception
     */
    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);
        ServletServerHttpRequest inputMessage = createInputMessage(webRequest);
        ServletServerHttpResponse outputMessage = createOutputMessage(webRequest);

        // 如果返回参数为 RestfulEntity 则不再包装
        if (!(returnValue instanceof RestfulEntity)) {
            returnValue = RestfulEntity.ok(returnValue);
        }

        // Try even with null return value. ResponseBodyAdvice could get involved.
        writeWithMessageConverters(returnValue, returnType, inputMessage, outputMessage);
    }
}
