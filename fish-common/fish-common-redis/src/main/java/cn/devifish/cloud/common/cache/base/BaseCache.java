package cn.devifish.cloud.common.cache.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.PostConstruct;
import java.io.Serializable;

/**
 * BaseCache
 * 公共缓存基础类
 *
 * @author Devifish
 * @date 2020/6/30 15:47
 */
public abstract class BaseCache<V, K extends Serializable> {

    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    protected ValueOperations<String, Object> valueOperations;

    @PostConstruct
    private void init() {
        valueOperations = redisTemplate.opsForValue();
    }



}
