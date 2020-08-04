package cn.devifish.cloud.message.server.service;

import cn.devifish.cloud.message.server.mapper.SmsTemplateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * SmsTemplateService
 * 短信模板服务
 *
 * @author Devifish
 * @date 2020/8/4 17:58
 */
@Service
@RequiredArgsConstructor
public class SmsTemplateService {

    private final SmsTemplateMapper smsTemplateMapper;

}
