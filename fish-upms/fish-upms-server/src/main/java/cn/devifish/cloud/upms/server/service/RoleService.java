package cn.devifish.cloud.upms.server.service;

import cn.devifish.cloud.upms.common.entity.Role;
import cn.devifish.cloud.upms.server.cache.RoleCache;
import cn.devifish.cloud.upms.server.mapper.RoleMapper;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
    private final UserService userService;

    /**
     * 根据角色ID查询角色数据
     *
     * @param roleId 角色ID
     * @return 角色
     */
    public Role selectById(Long roleId) {
        return roleCache.getIfAbsent(roleId, roleMapper::selectById);
    }

    /**
     * 根据用户ID查询角色数据
     *
     * @param userId 用户ID
     * @return List<Role>
     */
    public List<Role> selectByUserId(Long userId) {
        if (!userService.existById(userId))
            return Collections.emptyList();

        return roleMapper.selectByUserId(userId);
    }

    /**
     * 根据用户ID查询用户所有角色授权码
     *
     * @param userId 用户ID
     * @return 授权码
     */
    public String[] selectAuthoritiesByUserId(Long userId) {
        var roles = selectByUserId(userId);
        if (CollectionUtils.isEmpty(roles)) return null;

        //将RoleList转换为AuthoritiesArray
        return roles.stream()
                .map(role -> {
                    var code = role.getCode();
                    var authorities = JSON.parseArray(role.getAuthorities(), String.class);
                    authorities.add(code);
                    return authorities;
                })
                .flatMap(Collection::stream)
                .distinct()
                .toArray(String[]::new);
    }

}
