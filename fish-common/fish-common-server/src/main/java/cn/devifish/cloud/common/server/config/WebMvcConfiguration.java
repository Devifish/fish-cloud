package cn.devifish.cloud.common.server.config;

import cn.devifish.cloud.common.core.constant.DateTimeConstant;
import cn.devifish.cloud.common.server.convert.ConverterEnumFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.format.DateTimeFormatter;

/**
 * WebMvcConfiguration
 * Spring MVC配置
 *
 * @author Devifish
 * @date 2020/7/2 15:20
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    @SuppressWarnings("NullableProblems")
    public void addFormatters(FormatterRegistry registry) {
        //日期时间转换格式
        DateTimeFormatterRegistrar dateTimeFormatterRegistrar = new DateTimeFormatterRegistrar();
        dateTimeFormatterRegistrar.setTimeFormatter(DateTimeFormatter.ofPattern(DateTimeConstant.TIME_PATTERN));
        dateTimeFormatterRegistrar.setDateFormatter(DateTimeFormatter.ofPattern(DateTimeConstant.TIME_PATTERN));
        dateTimeFormatterRegistrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(DateTimeConstant.TIME_PATTERN));
        dateTimeFormatterRegistrar.registerFormatters(registry);

        //枚举转换格式
        ConverterEnumFactory converterEnumFactory = new ConverterEnumFactory();
        registry.addConverterFactory(converterEnumFactory);
    }
}
