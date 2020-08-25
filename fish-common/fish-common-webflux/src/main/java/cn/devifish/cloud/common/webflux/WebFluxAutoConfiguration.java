package cn.devifish.cloud.common.webflux;

import cn.devifish.cloud.common.webflux.config.WebFluxConfiguration;
import cn.devifish.cloud.common.webflux.handler.WebFluxExceptionAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * WebFluxAutoConfiguration
 * 公共 WebFlux 自动装载配置
 *
 * @author Devifish
 * @date 2020/8/25 17:25
 */
@Import({WebFluxConfiguration.class, WebFluxExceptionAdvice.class})
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.REACTIVE)
public class WebFluxAutoConfiguration {
}
