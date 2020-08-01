package cn.devifish.cloud.upms.server.cache;

import cn.devifish.cloud.common.redis.BaseCache;
import cn.devifish.cloud.upms.common.entity.Dictionary;
import org.springframework.stereotype.Repository;

/**
 * DictionaryCache
 * 字典缓存
 *
 * @author Devifish
 * @date 2020/8/1 16:52
 */
@Repository
public class DictionaryCache extends BaseCache<Dictionary, Long> {
}
