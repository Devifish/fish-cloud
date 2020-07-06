package cn.devifish.cloud.common.core.event;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * ServiceRegistrationEventPublishingAspect
 * 用于发布ServiceRegistrationEvent事件
 *
 * @author Devifish
 * @date 2020/7/6 15:44
 */
@Aspect
public class ServiceRegistrationEventPublishingAspect implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 在Spring ServiceRegistry注册前获取Registration
     *
     * @param registration 服务注册参数
     */
    @Before("execution(* org.springframework.cloud.client.serviceregistry.ServiceRegistry.register(*)) && args(registration)")
    public void beforeRegister(Registration registration) {
        applicationEventPublisher
                .publishEvent(new ServiceInstanceRegisteredEvent(registration));
    }

}
