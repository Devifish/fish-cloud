package cn.devifish.cloud.common.core.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * BeanMapUtil
 * 提供 JavaBean 和 Map 的互相转换
 *
 * @author Devifish
 * @date 2020/7/29 10:37
 */
public class BeanMapUtils {

    /**
     * JavaBean对象转成Map
     *
     * @param source 需要被转换的对象
     * @return map
     */
    public static Map<String, Object> toMap(Object source) {
        return toMap(source, false);
    }

    /**
     * JavaBean对象转成Map
     *
     * @param source 需要被转换的对象
     * @param ignoreNull 是否排除Null值
     * @return map
     */
    public static Map<String, Object> toMap(Object source, final boolean ignoreNull) {
        if (source == null) return Collections.emptyMap();

        var src = new BeanWrapperImpl(source);
        return Stream.of(src.getPropertyDescriptors())
            .collect(HashMap::new, (map, descriptor) -> {
                if (descriptor.getReadMethod() != null && descriptor.getWriteMethod() != null) {
                    var descriptorName = descriptor.getName();

                    var propertyValue = src.getPropertyValue(descriptorName);
                    if (propertyValue != null || !ignoreNull) {
                        map.put(descriptorName, propertyValue);
                    }
                }
            }, HashMap::putAll);
    }

    /**
     * Map<String, Object>转成JavaBean对象
     *
     * @param sourceMap map
     * @param targetClass 要转成的类
     * @param <T> 泛型
     * @return obj
     */
    public static <T> T parseObject(Map<String, Object> sourceMap, Class<T> targetClass) {
        T type = BeanUtils.instantiateClass(targetClass);
        if (CollectionUtils.isEmpty(sourceMap)) return type;

        var src = new BeanWrapperImpl(type);
        Stream.of(src.getPropertyDescriptors())
            .forEach(descriptor -> {
                if (descriptor.getReadMethod() != null && descriptor.getWriteMethod() != null) {
                    var descriptorName = descriptor.getName();

                    if (sourceMap.containsKey(descriptorName)) {
                        var value = sourceMap.get(descriptorName);
                        src.setPropertyValue(descriptorName, value);
                    }
                }
            });

        return type;
    }

}
