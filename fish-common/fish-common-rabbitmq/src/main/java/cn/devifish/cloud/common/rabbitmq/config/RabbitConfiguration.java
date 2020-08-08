package cn.devifish.cloud.common.rabbitmq.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitConfiguration
 * RabbitMQ配置
 *
 * @author Devifish
 * @date 2020/8/8 14:28
 */
@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class RabbitConfiguration {

    private final CachingConnectionFactory connectionFactory;
    private final RabbitTemplateConfigurer configurer;

    /**
     * 注册 RabbitTemplate
     *
     * @return RabbitTemplate
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        var template = new RabbitTemplate(connectionFactory);
        configurer.configure(template, connectionFactory);

        template.setMessageConverter(new Jackson2JsonMessageConverter());
        template.setConfirmCallback(this::confirmCallback);
        template.setReturnCallback(this::returnCallback);
        return template;
    }

    /**
     * 消息发送成功回调
     *
     * @param data  确认数据
     * @param ack   ACK
     * @param cause Cause
     */
    private void confirmCallback(CorrelationData data, boolean ack, String cause) {
        if (log.isDebugEnabled())
            log.debug("消息发送成功{} ack:{} cause:{}", data, ack, cause);
    }

    /**
     * 消息发送失败回调
     *
     * @param message    消息
     * @param replyCode  回复码
     * @param replyText  回复内容
     * @param exchange   交换机
     * @param routingKey 路由Key
     */
    private void returnCallback(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.warn("消息发送失败{} replyCode:{} replyText: {} exchange: {} routingKey:{} ",
            message, replyCode, replyText, exchange, routingKey);
    }

}
