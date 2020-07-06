package cn.devifish.cloud.common.core.event;

import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.context.ApplicationEvent;

/**
 * ServiceInstanceRegisteredEvent
 * 服务注册事件
 * 在{@link ServiceRegistry#register(Registration) register}方法触发前调用
 *
 * @author Devifish
 * @date 2020/7/6 15:04
 */
public class ServiceInstanceRegisteredEvent extends ApplicationEvent {

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public ServiceInstanceRegisteredEvent(Object source) {
        super(source);
    }

    @Override
    public Registration getSource() {
        return (Registration) super.getSource();
    }

}
