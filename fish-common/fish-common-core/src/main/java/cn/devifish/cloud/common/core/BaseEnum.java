package cn.devifish.cloud.common.core;

import java.io.Serializable;

/**
 * BaseEnum
 * 用于实现 Spring ConverterFactory 与 JPA AttributeConverter接口
 * 通过 param 实现到 Enum 的转换
 *
 * @param <V> param 类型
 * @author Devifish
 * @date 2020/7/2 10:22
 */
public interface BaseEnum<V extends Serializable> {

    V getParam();

}
