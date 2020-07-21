package cn.devifish.cloud.common.redis.config;

import cn.devifish.cloud.common.redis.constant.RedisConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurationSelector;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * CachingConfiguration
 * 缓存配置
 * 仅在添加 {@link org.springframework.cache.annotation.EnableCaching} 后加载
 *
 * @author Devifish
 * @date 2020/7/21 10:41
 * @see CachingConfigurationSelector
 * @see org.springframework.cache.annotation.CachingConfigurerSupport
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(CachingConfigurationSelector.class)
public class CachingConfiguration extends CachingConfigurerSupport {

    private final RedisConnectionFactory redisConnectionFactory;
    private final GenericJackson2JsonRedisSerializer jsonRedisSerializer;

    @Override
    public CacheManager cacheManager() {
        var redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(RedisConstant.DEFAULT_CACHE_TIMEOUT)
                .serializeKeysWith(SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(SerializationPair.fromSerializer(jsonRedisSerializer))
                .disableCachingNullValues();

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }
}
