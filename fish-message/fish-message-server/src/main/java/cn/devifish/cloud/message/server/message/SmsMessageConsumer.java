package cn.devifish.cloud.message.server.message;

import cn.devifish.cloud.message.common.constant.SmsMessageConstant;
import cn.devifish.cloud.message.common.dto.SmsSendDTO;
import cn.devifish.cloud.message.server.service.SmsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * SmsMessageConsumer
 * 短信消息消费
 *
 * @author Devifish
 * @date 2020/8/11 16:00
 */
@Component
@RequiredArgsConstructor
public class SmsMessageConsumer {

    private final ObjectMapper objectMapper;
    private final SmsService smsService;

    /**
     * 短信发送消息监听
     *
     * @param message 信息
     */
    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(SmsMessageConstant.FISH_MESSAGE_SMS_SEND_QUEUE),
        exchange = @Exchange(value = SmsMessageConstant.FISH_MESSAGE_EXCHANGE),
        key = SmsMessageConstant.FISH_MESSAGE_SMS_SEND_ROUTING
    ))
    public void smsSendMessageListener(Message message) throws IOException {
        var body = message.getBody();
        if (!ArrayUtils.isEmpty(body)) {
            var smsSendDTO = objectMapper.readValue(body, SmsSendDTO.class);

            // 开始发送
            smsService.send(smsSendDTO);
        }
    }

}
