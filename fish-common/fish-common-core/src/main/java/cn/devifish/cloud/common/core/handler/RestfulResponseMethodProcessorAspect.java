package cn.devifish.cloud.common.core.handler;

import cn.devifish.cloud.common.core.RestfulEntity;
import cn.devifish.cloud.common.core.exception.UtilException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * RestfulResponseMethodProcessor
 * Restful Response转换处理器
 * 重写RequestResponseBodyMethodProcessor
 * 自动将Method返回值转换为 Restful API风格
 *
 * @author Devifish
 * @date 2020/7/6 18:27
 * @see org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor
 * @see org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver
 */
@Aspect
public class RestfulResponseMethodProcessorAspect {

    /**
     * 在Spring ServiceRegistry注册前获取Registration
     *
     * @param point ProceedingJoinPoint
     */
    @Around("execution(* org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.handleReturnValue(*))")
    public Object beforeRegister(ProceedingJoinPoint point) {
        Object[] args = point.getArgs();
        Object returnValue = args[0];
        args[0] = RestfulEntity.ok(returnValue);

        try {
            return point.proceed(args);
        } catch (Throwable throwable) {
            throw new UtilException(throwable);
        }
    }
}
