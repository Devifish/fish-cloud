package cn.devifish.cloud.upms.server.cache;

import cn.devifish.cloud.common.redis.BaseCache;
import cn.devifish.cloud.upms.common.entity.Dept;
import org.springframework.stereotype.Repository;

/**
 * DeptCache
 * 部门缓存
 *
 * @author Devifish
 * @date 2020/8/1 16:43
 */
@Repository
public class DeptCache extends BaseCache<Dept, Long> {
}
