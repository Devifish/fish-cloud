package cn.devifish.cloud.common.redis;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * HashCache
 * Hash数据结构缓存
 *
 * @author Devifish
 * @date 2020/6/30 15:48
 */
public abstract class HashCache<V, K extends Serializable> extends BaseCache<V, K> {

    protected HashOperations<String, K, V> hashOperations;

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        hashOperations = redisTemplate.opsForHash();
    }

    /**
     * Hash Key生成器实现
     *
     * @return Hash Key生成器
     */
    public abstract Function<V, K> keyMapper();

    /**
     * 生成缓存KEY
     *
     * @return Key
     */
    public String generatorCacheKey() {
        return super.generatorCacheKey(null);
    }

    @Override
    public V get(K key) {
        return hashOperations.get(generatorCacheKey(), key);
    }

    /**
     * 获取所有缓存数据
     *
     * @return 数据集合
     */
    public List<V> getAll() {
        return hashOperations.values(generatorCacheKey());
    }

    @Override
    public V getIfAbsent(K key, Function<K, ? extends V> mappingFunction) {
        V value = get(key);
        if (value == null) {
            value = mappingFunction.apply(key);
        }
        return value;
    }

    public List<V> getAllIfAbsent(Supplier<List<V>> listSupplier) {
        var value = getAll();
        if (CollectionUtils.isEmpty(value) && (value = listSupplier.get()) != null) {
            setAll(value);
        }
        return value;
    }

    /**
     * 更新设置缓存对象
     *
     * @param key Key
     * @param value Value
     */
    @Override
    public void set(K key, V value) {
        var cacheKey = generatorCacheKey();
        hashOperations.put(cacheKey, key, value);
        redisTemplate.expire(cacheKey, getTimeout());
    }

    /**
     * 集合方式更新设置缓存对象
     *
     * @param values 数据集合
     */
    public void setAll(Collection<V> values) {
        if (CollectionUtils.isEmpty(values)) return;
        var cacheKey = generatorCacheKey();
        var menuMap = values.stream()
                .collect(Collectors.toMap(keyMapper(), menu -> menu));

        hashOperations.putAll(cacheKey, menuMap);
        redisTemplate.expire(cacheKey, getTimeout());
    }

    /**
     * 删除缓存数据
     *
     */
    public void delete() {
        super.delete(null);
    }

    /**
     * 删除指定 key 数据
     * 该方法已弃用
     *
     * @param key Hash key
     */
    @Deprecated
    @Override
    public void delete(K key) {
        super.delete(null);
    }

    /**
     * 删除全部数据
     * 该方法已弃用
     */
    @Deprecated
    @Override
    public void deleteAll() {
        super.delete(null);
    }
}
