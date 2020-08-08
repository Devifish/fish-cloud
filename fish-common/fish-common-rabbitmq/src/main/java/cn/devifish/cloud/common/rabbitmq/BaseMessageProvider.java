package cn.devifish.cloud.common.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * BaseMessageProvider
 * 基础消息提供者
 *
 * @author Devifish
 * @date 2020/8/8 17:02
 */
public abstract class BaseMessageProvider<T> implements InitializingBean {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private String defaultExchange;
    private String defaultRoutingKey;

    @Override
    public void afterPropertiesSet() {
        this.defaultExchange = rabbitTemplate.getExchange();
        this.defaultRoutingKey = rabbitTemplate.getRoutingKey();
    }

    /**
     * 获取交换机名称
     * 可重写该方法，自定义交换机名称
     *
     * @return 交换机名称
     */
    protected String getExchange() {
        return defaultExchange;
    }

    /**
     * 获取路由Key
     * 可重写该方法，自定义路由Key
     *
     * @return 路由Key
     */
    protected String getRoutingKey() {
        return defaultRoutingKey;
    }

    /**
     * 推送消息
     *
     * @param content 内容
     */
    public void push(T content) {
        rabbitTemplate.convertAndSend(getExchange(), getRoutingKey(), content);
    }

}
