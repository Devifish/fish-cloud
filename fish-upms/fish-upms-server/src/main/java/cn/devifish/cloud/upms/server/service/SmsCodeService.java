package cn.devifish.cloud.upms.server.service;

import cn.devifish.cloud.upms.server.cache.SmsCodeCache;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;

/**
 * SmsCodeService
 * 短信验证码服务
 *
 * @author Devifish
 * @date 2020/8/6 18:15
 */
@Service
@RequiredArgsConstructor
public class SmsCodeService {

    private final SmsCodeCache smsCodeCache;
    private final RedisLockRegistry redisLockRegistry;



}
