package cn.devifish.cloud.message.server.controller;

import cn.devifish.cloud.message.server.service.SmsTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SmsTemplateController
 * 短信模板 API
 *
 * @author Devifish
 * @date 2020/8/4 17:30
 */
@RestController
@RequestMapping("/sms/template/")
@RequiredArgsConstructor
public class SmsTemplateController {

    private final SmsTemplateService smsTemplateService;

}
