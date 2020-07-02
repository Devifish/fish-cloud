package cn.devifish.cloud.common.server.convert;

import cn.devifish.cloud.common.core.base.BaseEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.io.Serializable;

/**
 * ConverterEnumFactory
 * 实现 Spring MVC 转换枚举类型
 * 使用枚举类型需要实现 Converter Enum 接口
 * String getValue() -> Enum
 *
 * @author Devifish
 * @date 2020/7/2 10:19
 */
public class ConverterEnumFactory implements ConverterFactory<String, BaseEnum<?>> {

    /**
     * 获取 对应枚举类型的 转换器
     *
     * @param targetType 枚举 Class对象
     * @param <E>        枚举
     * @return 转换器
     */
    @Override
    @SuppressWarnings("NullableProblems")
    public <E extends BaseEnum<?>> Converter<String, E> getConverter(Class<E> targetType) {
        return key -> {
            final E[] enumConstants = targetType.getEnumConstants();
            for (E enumConstant : enumConstants) {
                Serializable param = enumConstant.param();
                if (StringUtils.equals(key, String.valueOf(param))) {
                    return enumConstant;
                }
            }
            return null;
        };
    }
}
