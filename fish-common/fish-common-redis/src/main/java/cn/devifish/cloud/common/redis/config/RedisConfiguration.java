package cn.devifish.cloud.common.redis.config;

import cn.devifish.cloud.common.redis.constant.RedisConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * RedisConfiguration
 * Redis配置
 *
 * @author Devifish
 * @date 2020/6/30 15:58
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class RedisConfiguration {

    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        log.info("Initializing Redis Template");

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());
        return redisTemplate;
    }

    /**
     * 注册 Redis分布式锁 到 Spring Context
     *
     * @return RedisLockRegistry
     */
    @Bean
    public RedisLockRegistry redisLockRegistry() {
        log.info("Initializing Redis Lock");

        return new RedisLockRegistry(redisConnectionFactory, RedisConstant.REDIS_LOCK_KEY_PREFIX);
    }

}
