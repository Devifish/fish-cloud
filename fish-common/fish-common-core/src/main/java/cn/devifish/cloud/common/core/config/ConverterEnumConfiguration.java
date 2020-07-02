package cn.devifish.cloud.common.core.config;

import cn.devifish.cloud.common.core.convert.ConverterEnumFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;

import javax.annotation.PostConstruct;

/**
 * ConverterEnumConfiguration
 * 注册枚举转换工厂
 *
 * @author Devifish
 * @date 2020/7/2 11:11
 */
@Configuration
@RequiredArgsConstructor
public class ConverterEnumConfiguration {

    private final FormatterRegistry formatterRegistry;

    @PostConstruct
    private void init() {
        ConverterEnumFactory converterEnumFactory = new ConverterEnumFactory();
        formatterRegistry.addConverterFactory(converterEnumFactory);
    }
}
