package cn.devifish.cloud.common.core.config;

import cn.devifish.cloud.common.core.constant.DateTimeConstant;
import cn.devifish.cloud.common.core.convert.ConverterEnumFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {

    }
}
