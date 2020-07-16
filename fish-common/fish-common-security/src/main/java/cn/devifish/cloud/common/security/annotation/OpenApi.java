package cn.devifish.cloud.common.security.annotation;

import java.lang.annotation.*;

/**
 * OpenApi
 * 开放接口
 * 添加该注解接口加入鉴权白名单
 *
 * @author Devifish
 * @date 2020/7/16 14:44
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OpenApi {
}
