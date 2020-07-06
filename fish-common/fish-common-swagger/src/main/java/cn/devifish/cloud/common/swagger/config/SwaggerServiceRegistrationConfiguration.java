package cn.devifish.cloud.common.swagger.config;

import cn.devifish.cloud.common.core.event.ServiceInstanceRegisteredEvent;
import cn.devifish.cloud.common.core.event.ServiceRegistrationEventPublishingAspect;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;

/**
 * SwaggerServiceRegistrationConfiguration
 * Swagger服务注册配置
 *
 * @author Devifish
 * @date 2020/7/6 14:34
 */
@Configuration(proxyBeanMethods = false)
@Import({ServiceRegistrationEventPublishingAspect.class})
public class SwaggerServiceRegistrationConfiguration {

    /**
     * 监听ServiceInstanceRegisteredEvent服务注册事件
     * 将Swagger相关信息注册到注册中心
     *
     * @param event 服务注册事件
     */
    @EventListener(ServiceInstanceRegisteredEvent.class)
    public void onServiceInstancePreRegistered(ServiceInstanceRegisteredEvent event) {
        Registration registration = event.getSource();
        /* TODO 待实现服务注册需要的参数 */
    }
}
