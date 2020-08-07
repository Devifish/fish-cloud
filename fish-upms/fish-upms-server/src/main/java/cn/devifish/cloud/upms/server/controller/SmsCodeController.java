package cn.devifish.cloud.upms.server.controller;

import cn.devifish.cloud.upms.server.service.SmsCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * SmsCodeController
 * 短信验证码API
 *
 * @author Devifish
 * @date 2020/8/7 11:19
 */
@RestController
@RequestMapping("/sms-code")
@RequiredArgsConstructor
public class SmsCodeController {

    private final SmsCodeService smsCodeService;

    /**
     * 发送用户登录验证码
     *
     * @param telephone 电话号码
     * @return 是否成功
     */
    @PostMapping("/send/user-login")
    public Boolean sendByUserLogin(@RequestParam String telephone) {
        return smsCodeService.sendByUserLogin(telephone);
    }



}
