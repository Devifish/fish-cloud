package cn.devifish.cloud.common.core.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * WebFluxConfiguration
 * Spring WebFlux配置
 *
 * @author Devifish
 * @date 2020/7/23 17:23
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.REACTIVE)
public class WebFluxConfiguration extends WebConfigurerAdapter implements WebFluxConfigurer {

    @Override
    @SuppressWarnings("NullableProblems")
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
    }

}
