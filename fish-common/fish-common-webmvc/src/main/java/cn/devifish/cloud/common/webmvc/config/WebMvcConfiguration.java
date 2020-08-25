package cn.devifish.cloud.common.webmvc.config;

import cn.devifish.cloud.common.core.constant.DateTimeConstant;
import cn.devifish.cloud.common.core.convert.ConverterEnumFactory;
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
@Configuration(proxyBeanMethods = false)
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * 注册公共的数据格式化组件
     * 包含
     * 日期时间转换格式
     * 枚举转换格式
     *
     * @param registry FormatterRegistry
     */
    @Override
    @SuppressWarnings("NullableProblems")
    public void addFormatters(FormatterRegistry registry) {
        //日期时间转换格式
        var registrar = new DateTimeFormatterRegistrar();
        registrar.setTimeFormatter(DateTimeFormatter.ofPattern(DateTimeConstant.TIME_PATTERN));
        registrar.setDateFormatter(DateTimeFormatter.ofPattern(DateTimeConstant.DATE_PATTERN));
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(DateTimeConstant.DATE_TIME_PATTERN));
        registrar.registerFormatters(registry);

        //枚举转换格式
        var converterEnumFactory = new ConverterEnumFactory();
        registry.addConverterFactory(converterEnumFactory);
    }

}
