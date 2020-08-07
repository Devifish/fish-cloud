package cn.devifish.cloud.upms.common.constant;

import java.util.concurrent.TimeUnit;

/**
 * SmsCodeConstant
 * 短信验证码常量
 *
 * @author Devifish
 * @date 2020/8/6 18:23
 */
public interface SmsCodeConstant {

    /** 短信验证码缓存前缀 **/
    String SMS_CODE_CACHE_PREFIX = "sms_code";

    /** 短信验证码超时时间 **/
    long SHORT_MESSAGE_CAPTCHA_TIMEOUT = TimeUnit.MINUTES.toSeconds(5);

    /** 短信验证码再次尝试时间 **/
    long SHORT_MESSAGE_CAPTCHA_RETRY = TimeUnit.MINUTES.toSeconds(2);

    /** 短信验证码长度 **/
    int SHORT_MESSAGE_CAPTCHA_LENGTH = 6;

}
