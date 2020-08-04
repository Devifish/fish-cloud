package cn.devifish.cloud.message.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * SmsService
 * 短信服务
 *
 * @author Devifish
 * @date 2020/8/4 17:32
 */
@Service
@RequiredArgsConstructor
public class SmsService {

    private SmsTemplateService smsTemplateService;

}
