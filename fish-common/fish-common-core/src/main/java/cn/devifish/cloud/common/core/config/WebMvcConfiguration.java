package cn.devifish.cloud.common.core.config;

import cn.devifish.cloud.common.core.handler.RestfulResponseMethodProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.Objects;

/**
 * WebMvcConfiguration
 * Spring MVC配置
 *
 * @author Devifish
 * @date 2020/7/2 15:20
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
public class WebMvcConfiguration extends WebConfigurerAdapter implements WebMvcConfigurer {

    @Override
    @SuppressWarnings("NullableProblems")
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
    }

    @Bean
    @ConditionalOnBean(RequestMappingHandlerAdapter.class)
    public InitializingBean restfulResponseMethodProcessorBean(RequestMappingHandlerAdapter adapter) {
        return () -> {
            var returnValueHandlers = adapter.getReturnValueHandlers();
            var messageConverters = adapter.getMessageConverters();
            var processor = new RestfulResponseMethodProcessor(messageConverters);

            //构建新的returnValueHandlers集合
            returnValueHandlers = new ArrayList<>(Objects.requireNonNull(returnValueHandlers));
            returnValueHandlers.replaceAll(processor::replaceDefault);
            adapter.setReturnValueHandlers(returnValueHandlers);
        };
    }
}
