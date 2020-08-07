package cn.devifish.cloud.upms.server.cache;

import cn.devifish.cloud.common.redis.BaseCache;
import cn.devifish.cloud.upms.common.constant.SmsCodeConstant;
import org.springframework.stereotype.Repository;

import java.time.Duration;

/**
 * SmsCodeCache
 * 短信验证码缓存
 *
 * @author Devifish
 * @date 2020/8/6 18:15
 */
@Repository
public class SmsCodeCache extends BaseCache<String, String> {

    @Override
    public String getBaseCacheKey() {
        return SmsCodeConstant.SMS_CODE_CACHE_PREFIX;
    }

    @Override
    public Duration getTimeout() {
        return Duration.ofSeconds(SmsCodeConstant.SHORT_MESSAGE_CAPTCHA_TIMEOUT);
    }
}
