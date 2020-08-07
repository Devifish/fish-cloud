package cn.devifish.cloud.common.redis.constant;

import java.time.Duration;

/**
 * RedisConstant
 * Redis常量
 *
 * @author Devifish
 * @date 2020/6/30 16:18
 */
public interface RedisConstant {

    /** KEY分割符 **/
    String KEY_SEPARATOR = ":";

    /** KEY后缀分割符 **/
    String KEY_SUFFIX_SEPARATOR = "@";

    /** KEY名分割符 **/
    String KEY_NAME_SEPARATOR = "_";

    /** 分布式锁前缀 **/
    String REDIS_LOCK_KEY_PREFIX = "distributed_locks";

    /** 默认缓存超时时间 (1小时) **/
    Duration DEFAULT_CACHE_TIMEOUT = Duration.ofHours(1);

}
