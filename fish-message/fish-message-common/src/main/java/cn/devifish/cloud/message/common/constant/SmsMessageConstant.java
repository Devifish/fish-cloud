package cn.devifish.cloud.message.common.constant;

import cn.devifish.cloud.common.rabbitmq.constant.RabbitConstant;

/**
 * SmsMessageConstant
 * 短信消息常量
 *
 * @author Devifish
 * @date 2020/8/10 17:33
 */
public interface SmsMessageConstant {

    /** 消息服务交换机名称 **/
    String FISH_MESSAGE_EXCHANGE = "fish-message-exchange";

    /** 消息服务短信发送消息 **/
    String FISH_MESSAGE_SMS_SEND_MESSAGE = "fish-message-sms-send";
    String FISH_MESSAGE_SMS_SEND_ROUTING = FISH_MESSAGE_SMS_SEND_MESSAGE + RabbitConstant.ROUTING_NAME_SUFFIX;
    String FISH_MESSAGE_SMS_SEND_QUEUE = FISH_MESSAGE_SMS_SEND_MESSAGE + RabbitConstant.QUEUE_NAME_SUFFIX;

}
