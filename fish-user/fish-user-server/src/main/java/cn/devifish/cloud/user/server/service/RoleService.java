package cn.devifish.cloud.user.server.service;

import cn.devifish.cloud.user.common.entity.Role;
import cn.devifish.cloud.user.server.cache.RoleCache;
import cn.devifish.cloud.user.server.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * RoleService
 * 角色服务
 *
 * @author Devifish
 * @date 2020/7/11 20:28
 */
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleMapper roleMapper;
    private final RoleCache roleCache;

    /**
     * 根据角色ID查询角色数据
     *
     * @param roleId 角色ID
     * @return 角色
     */
    public Role selectById(Long roleId) {
        return roleCache.getIfAbsent(roleId, roleMapper::selectById);
    }
}
