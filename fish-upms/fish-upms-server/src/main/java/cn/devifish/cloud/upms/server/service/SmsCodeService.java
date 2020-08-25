package cn.devifish.cloud.upms.server.service;

import cn.devifish.cloud.common.core.exception.BizException;
import cn.devifish.cloud.upms.common.constant.SmsCodeConstant;
import cn.devifish.cloud.upms.common.enums.SmsCodeType;
import cn.devifish.cloud.upms.server.cache.SmsCodeCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cn.devifish.cloud.common.core.MessageCode.PreconditionFailed;

/**
 * SmsCodeService
 * 短信验证码服务
 *
 * @author Devifish
 * @date 2020/8/6 18:15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SmsCodeService {

    private final SmsCodeCache smsCodeCache;
    private final RedisLockRegistry redisLockRegistry;

    /**
     * 获取短信验证码信息
     *
     * @param telephone 电话号码
     * @param type 短信类型
     * @return 验证码
     */
    public String get(String telephone, SmsCodeType type) {
        return smsCodeCache.get(telephone, type.getParam());
    }

    /**
     * 删除短信验证码信息
     *
     * @param telephone 电话号码
     * @param type 短信类型
     */
    public void delete(String telephone, SmsCodeType type) {
        smsCodeCache.delete(telephone, type.getParam());
    }

    /**
     * 快速构建短信验证码
     * 当缓存内存在验证码或该号码未在可重新构建时 返回NUll
     * （同步执行，防止并发调用）
     *
     * @param telephone 手机号
     * @return 验证码
     */
    public String generate(String telephone, SmsCodeType type) {
        var suffix = type.getParam();
        var key = smsCodeCache.generatorCacheKey(telephone, suffix);
        var lock = redisLockRegistry.obtain(key);

        if (!lock.tryLock()) {
            log.warn("阻止用户生成验证码, 检测到[telephone：{}]存在并发调用接口行为", telephone);
            return null;
        }

        try {
            long expire = Optional.ofNullable(smsCodeCache.getExpire(telephone, suffix))
                .orElse(NumberUtils.LONG_ZERO);

            //当缓存内没有当前号码的验证码 或 达到验证码可重试时间要求 发送验证码给用户
            if (SmsCodeConstant.SHORT_MESSAGE_CAPTCHA_TIMEOUT - SmsCodeConstant.SHORT_MESSAGE_CAPTCHA_RETRY > expire) {
                var captcha = Stream.generate(Math::random)
                    .map(random -> Integer.toString((int) (random * 10)))
                    .limit(SmsCodeConstant.SHORT_MESSAGE_CAPTCHA_LENGTH)
                    .collect(Collectors.joining());

                //缓存验证码到Redis
                log.debug("生成短信验证码成功 telephone: {}, captcha: {}", telephone, captcha);
                smsCodeCache.set(telephone, suffix, captcha);
                return captcha;
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    /**
     * 获取短信验证码信息
     *
     * @param telephone 电话号码
     * @param type 短信类型
     * @param code 验证码
     * @return 是否正确
     */
    public boolean verify(String telephone, SmsCodeType type, String code) {
        if (StringUtils.isEmpty(telephone)) throw new BizException(PreconditionFailed, "手机号不能为空");
        if (StringUtils.isEmpty(code)) throw new BizException(PreconditionFailed, "验证码不能为空");

        var cache_code = get(telephone, type);
        if (cache_code == null)
            throw new BizException("验证码已失效, 请重新获取验证码");

        return Objects.equals(cache_code, code);
    }

}
