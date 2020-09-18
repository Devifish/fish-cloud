package cn.devifish.cloud.upms.server.service;

import cn.devifish.cloud.common.core.exception.BizException;
import cn.devifish.cloud.common.mybatis.Page;
import cn.devifish.cloud.common.security.constant.SecurityConstant;
import cn.devifish.cloud.upms.common.dto.RolePageDTO;
import cn.devifish.cloud.upms.common.entity.Role;
import cn.devifish.cloud.upms.server.cache.RoleCache;
import cn.devifish.cloud.upms.server.mapper.RoleMapper;
import cn.devifish.cloud.upms.server.mapper.UserRoleRelationMapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static cn.devifish.cloud.common.core.MessageCode.PreconditionFailed;

/**
 * RoleService
 * 角色服务
 *
 * @author Devifish
 * @date 2020/7/11 20:28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleMapper roleMapper;
    private final RoleCache roleCache;
    private final UserRoleRelationMapper userRoleRelationMapper;

    // 防止IOC循环依赖
    private final ObjectFactory<MenuService> menuServiceFactory;
    private final ObjectFactory<UserService> userServiceFactory;

    /**
     * 根据角色ID查询角色数据
     *
     * @param roleId 角色ID
     * @return 角色
     */
    public Role selectById(Long roleId) {
        if (roleId == null)
            throw new BizException(PreconditionFailed, "角色ID不能为空");

        return roleCache.getIfAbsent(roleId, roleMapper::selectById);
    }

    /**
     * 分页查询角色数据
     *
     * @param param 参数
     * @return Page<Role>
     */
    public Page<Role> selectPage(RolePageDTO param) {
        return roleMapper.selectPage(Page.of(param), param);
    }

    /**
     * 根据用户ID查询角色数据
     *
     * @param userId 用户ID
     * @return List<Role>
     */
    public List<Role> selectByUserId(Long userId) {
        var userService = userServiceFactory.getObject();
        if (!userService.existById(userId))
            return Collections.emptyList();

        // 查询用户角色数据
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
        if (CollectionUtils.isEmpty(roles))
            return ArrayUtils.EMPTY_STRING_ARRAY;

        // 将RoleList转换为AuthoritiesArray
        return roles.stream()
            .map(role -> {
                var code = role.getCode();
                var authorities = Optional.ofNullable(role.getAuthorities())
                    .orElseGet(HashSet::new);

                // 如果角色符合规则，那么添加到权限列表
                if (StringUtils.startsWith(code, SecurityConstant.DEFAULT_ROLE_PREFIX))
                    authorities.add(code);

                return authorities;
            })
            .flatMap(Collection::stream)
            .distinct()
            .toArray(String[]::new);
    }

    /**
     * 根据角色Code查询是否存在
     *
     * @param code 角色Code
     * @return boolean
     */
    public Boolean existByCode(String code) {
        if (StringUtils.isEmpty(code)) return false;

        // 获取角色统计数据
        var count = SqlHelper.retCount(roleMapper.countByCode(code));
        return count > 0;
    }

    /**
     * 根据角色ID更新角色权限
     *
     * @param roleId      角色ID
     * @param authorities 权限集合
     * @return 是否成功
     */
    public Boolean updateAuthoritiesByRoleId(Long roleId, Set<String> authorities) {
        var role = selectById(roleId);
        if (role == null) throw new BizException("该角色不存在");

        // 仅保留数据库菜单所设置的授权码
        if (!CollectionUtils.isEmpty(authorities)) {
            var menuService = menuServiceFactory.getObject();
            var allPermission = menuService.selectAllPermission();
            authorities.retainAll(allPermission);
        }

        // 修改角色权限
        role.setAuthorities(authorities);
        return update(role);
    }

    /**
     * 保存角色数据
     * 包含各项参数校验及数据转换
     *
     * @param role 角色
     * @return 是否成功
     */
    @Transactional
    public Boolean insert(Role role) {
        var name = role.getName();
        var code = role.getCode();

        // 参数校验
        if (StringUtils.isEmpty(name)) throw new BizException(PreconditionFailed, "角色名不能为空");
        if (existByCode(code)) throw new BizException("角色编码已存在");

        // 设置默认值
        role.setId(null);
        if (role.getSystemFlag() == null) role.setSystemFlag(Boolean.FALSE);
        if (role.getAuthorities() == null) role.setAuthorities(Collections.emptySet());

        // 保存角色数据
        if (SqlHelper.retBool(roleMapper.insert(role))) {
            return Boolean.TRUE;
        } else {
            log.info("保存角色数据：{} 失败", role);
            throw new BizException("保存角色失败");
        }
    }

    /**
     * 更新角色数据
     * 包含各项参数校验及数据转换
     *
     * @param role 角色
     * @return 是否成功
     */
    @Transactional
    public Boolean update(Role role) {
        var roleId = role.getId();

        // 检查是否存在
        var old_role = selectById(roleId);
        if (old_role == null) throw new BizException("该角色不存在");

        // 校验角色Code值
        var code = role.getCode();
        var old_code = old_role.getCode();
        if (code != null && !code.equals(old_code)) {
            if (existByCode(code))
                throw new BizException("角色Code已存在");
        }

        // 更新并移除缓存
        if (SqlHelper.retBool(roleMapper.updateById(role))) {
            roleCache.delete(roleId);
            return Boolean.TRUE;
        } else {
            log.warn("修改角色ID：{} 的角色数据失败", roleId);
            throw new BizException("修改失败");
        }
    }

    /**
     * 删除角色
     * 必须与用户进行解绑
     *
     * @param roleId 角色ID
     * @return 是否成功
     */
    @Transactional
    public Boolean delete(Long roleId) {
        var role = selectById(roleId);
        if (role == null) throw new BizException("该角色不存在");

        // 校验角色是否存在绑定
        var userIds = userRoleRelationMapper.selectUserIdByRoleId(roleId);
        if (!CollectionUtils.isEmpty(userIds)) throw new BizException("该角色正在被用户使用");

        // 删除并移除缓存
        if (SqlHelper.retBool(roleMapper.deleteById(role))) {
            roleCache.delete(roleId);
            return Boolean.TRUE;
        } else {
            log.warn("删除角色ID：{} 数据失败", roleId);
            throw new BizException("删除失败");
        }
    }

}
