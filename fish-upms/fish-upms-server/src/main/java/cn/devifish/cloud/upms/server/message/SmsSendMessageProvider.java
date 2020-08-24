package cn.devifish.cloud.upms.server.message;

import cn.devifish.cloud.common.rabbitmq.BaseMessageProvider;
import cn.devifish.cloud.message.common.constant.SmsMessageConstant;
import cn.devifish.cloud.message.common.dto.SmsSendDTO;
import org.springframework.stereotype.Component;

/**
 * SmsMessageProvider
 * 短信发送消息 Provider
 *
 * @author Devifish
 * @date 2020/8/24 17:33
 */
@Component
public class SmsSendMessageProvider extends BaseMessageProvider<SmsSendDTO> {

    @Override
    protected String getRoutingKey() {
        return SmsMessageConstant.FISH_MESSAGE_SMS_SEND_ROUTING;
    }

    @Override
    protected String getExchange() {
        return SmsMessageConstant.FISH_MESSAGE_EXCHANGE;
    }

}
