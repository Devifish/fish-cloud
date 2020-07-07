package cn.devifish.cloud.common.core.config;

import cn.devifish.cloud.common.core.constant.DateTimeConstant;
import cn.devifish.cloud.common.core.convert.ConverterEnumFactory;
import cn.devifish.cloud.common.core.handler.RestfulResponseMethodProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * WebMvcConfiguration
 * Spring MVC配置
 *
 * @author Devifish
 * @date 2020/7/2 15:20
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    @SuppressWarnings("NullableProblems")
    public void addFormatters(FormatterRegistry registry) {
        //日期时间转换格式
        DateTimeFormatterRegistrar dateTimeFormatterRegistrar = new DateTimeFormatterRegistrar();
        dateTimeFormatterRegistrar.setTimeFormatter(DateTimeFormatter.ofPattern(DateTimeConstant.TIME_PATTERN));
        dateTimeFormatterRegistrar.setDateFormatter(DateTimeFormatter.ofPattern(DateTimeConstant.DATE_PATTERN));
        dateTimeFormatterRegistrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(DateTimeConstant.DATE_TIME_PATTERN));
        dateTimeFormatterRegistrar.registerFormatters(registry);

        //枚举转换格式
        ConverterEnumFactory converterEnumFactory = new ConverterEnumFactory();
        registry.addConverterFactory(converterEnumFactory);
    }

    @Bean
    @ConditionalOnBean(RequestMappingHandlerAdapter.class)
    public InitializingBean restfulResponseMethodProcessorBean(RequestMappingHandlerAdapter adapter) {
        return () -> {
            List<HandlerMethodReturnValueHandler> returnValueHandlers = adapter.getReturnValueHandlers();
            returnValueHandlers = new ArrayList<>(Objects.requireNonNull(returnValueHandlers));
            returnValueHandlers.replaceAll(handlerMethodReturnValueHandler -> {
                //替换Spring MVC ResponseBody默认处理方式
                if (handlerMethodReturnValueHandler instanceof RequestResponseBodyMethodProcessor) {
                    List<HttpMessageConverter<?>> messageConverters = adapter.getMessageConverters();
                    return new RestfulResponseMethodProcessor(messageConverters);
                }
                return handlerMethodReturnValueHandler;
            });

            adapter.setReturnValueHandlers(returnValueHandlers);
        };
    }
}
