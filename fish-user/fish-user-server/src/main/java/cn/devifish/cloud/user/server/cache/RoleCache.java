package cn.devifish.cloud.user.server.cache;

import cn.devifish.cloud.common.redis.BaseCache;
import cn.devifish.cloud.user.common.entity.Role;
import org.springframework.stereotype.Repository;

/**
 * RoleCache
 * 角色缓存
 *
 * @author Devifish
 * @date 2020/7/13 10:51
 */
@Repository
public class RoleCache extends BaseCache<Role, Long> {
}
