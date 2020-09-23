package cn.devifish.cloud.upms.server.service;

import cn.devifish.cloud.common.core.exception.BizException;
import cn.devifish.cloud.common.core.util.BeanUtils;
import cn.devifish.cloud.common.core.util.TreeUtils;
import cn.devifish.cloud.common.security.util.AuthorityUtils;
import cn.devifish.cloud.common.security.util.SecurityUtils;
import cn.devifish.cloud.upms.common.dto.MenuDTO;
import cn.devifish.cloud.upms.common.entity.Menu;
import cn.devifish.cloud.upms.common.enums.MenuType;
import cn.devifish.cloud.upms.common.vo.MenuTree;
import cn.devifish.cloud.upms.server.cache.MenuCache;
import cn.devifish.cloud.upms.server.mapper.MenuMapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static cn.devifish.cloud.common.core.MessageCode.PreconditionFailed;

/**
 * MenuService
 * 菜单服务
 *
 * @author Devifish
 * @date 2020/7/16 10:57
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuMapper menuMapper;
    private final MenuCache menuCache;
    private final RoleService roleService;

    /**
     * 获取全部菜单数据
     *
     * @return List<Menu>
     */
    public List<Menu> selectAll() {
        return menuCache.getAllIfAbsent(menuMapper::selectAll);
    }

    /**
     * 根据菜单ID查询菜单数据
     *
     * @param menuId 菜单ID
     * @return 角色
     */
    public Menu selectById(Long menuId) {
        if (menuId == null)
            throw new BizException(PreconditionFailed, "菜单ID不能为空");

        return menuCache.getIfAbsent(menuId, menuMapper::selectById);
    }

    /**
     * 查询全部菜单Vo数据
     *
     * @return List<MenuVo>
     */
    public List<MenuTree> selectAllVo() {
        var menus = selectAll();
        return menus.stream()
                .map(MenuTree::new)
                .collect(Collectors.toList());
    }

    /**
     * 查询菜单树
     * 构建树结构数据
     * 方便前端构建菜单
     *
     * @return TreeSet<Menu>
     */
    public Set<MenuTree> selectMenuTree() {
        return TreeUtils.toTree(selectAllVo(), null);
    }

    /**
     * 根据用户ID查询菜单树
     * 构建树结构数据
     * 方便前端构建菜单
     *
     * @return TreeSet<Menu>
     */
    public Set<MenuTree> selectMenuTreeByUserId(Long userId) {
        var authorities = Set.of(roleService.selectAuthoritiesByUserId(userId));
        return selectMenuTreeByAuthorities(authorities);
    }

    /**
     * 查询当前用户的菜单树
     * 构建树结构数据
     * 方便前端构建菜单
     *
     * @return TreeSet<Menu>
     */
    public Set<MenuTree> currentMenuTree() {
        var authorities = SecurityUtils.getAuthorities();
        var authoritiesSet = AuthorityUtils.authorityListToSet(authorities);
        return selectMenuTreeByAuthorities(authoritiesSet);
    }

    /**
     * 根据权限构建菜单树
     * 利用TreeSet原生的有序性存储数据
     * 对Menu.getSort进行排序
     *
     * @param authorities 权限
     * @return 菜单树
     */
    private Set<MenuTree> selectMenuTreeByAuthorities(Set<String> authorities) {
        return TreeUtils.toTree(selectAllVo(), null, TreeSet::new, menuTree -> {
            var permission = menuTree.getPermission();
            if (StringUtils.isNotEmpty(permission)) {
                return authorities.contains(permission);
            }
            return true;
        });
    }

    /**
     * 获取全部菜单的权限代码
     * 用于校验角色权限是否正确
     *
     * @return 权限代码集合
     */
    public Set<String> selectAllPermission() {
        var allPermission = menuMapper.selectAllPermission();
        return CollectionUtils.isEmpty(allPermission)
                ? Collections.emptySet()
                : allPermission;
    }

    /**
     * 根据菜单ID查询是否存在
     *
     * @param menuId 菜单ID
     * @return boolean
     */
    public Boolean existById(Long menuId) {
        if (menuId == null) return Boolean.FALSE;
        return selectById(menuId) != null;
    }

    /**
     * 根据菜单授权码查询是否存在
     *
     * @param permission 菜单授权码
     * @return boolean
     */
    public Boolean existByPermission(String permission) {
        if (StringUtils.isEmpty(permission)) return Boolean.FALSE;

        // 获取菜单统计数据
        var count = SqlHelper.retCount(1);
        return count > 0;
    }

    /**
     * 根据菜单父级Id查询是否存在
     *
     * @param parentId 父级Id
     * @return boolean
     */
    public Boolean existByParentId(Long parentId) {
        if (parentId == null) return Boolean.FALSE;

        // 获取菜单统计数据
        int count = SqlHelper.retCount(menuMapper.countByParentId(parentId));
        return count > 0;
    }

    /**
     * 保存菜单数据
     * 包含各项参数校验及数据转换
     *
     * @param menu 菜单
     * @return 是否成功
     */
    @Transactional
    public Boolean insert(Menu menu) {
        var name = menu.getName();
        var permission = menu.getPermission();

        // 参数校验
        if (StringUtils.isEmpty(name)) throw new BizException(PreconditionFailed, "菜单名不能为空");
        if (existByPermission(permission)) throw new BizException("菜单授权码已存在");

        // 校验父级ID是否存在
        var parentId = menu.getParentId();
        if (parentId != null && !existById(parentId)) {
            throw new BizException("父级菜单不存在");
        }

        // 设置默认值
        menu.setId(null);
        if (menu.getEnable() == null) menu.setEnable(Boolean.TRUE);
        if (menu.getSort() == null) menu.setSort(0);
        if (menu.getType() == null) menu.setType(MenuType.Menu);

        // 保存菜单数据
        if (SqlHelper.retBool(menuMapper.insert(menu))) {
            return Boolean.TRUE;
        }else {
            log.info("保存菜单数据：{} 失败", menu);
            throw new BizException("保存菜单失败");
        }
    }

    /**
     * 保存菜单数据
     * 包含各项参数校验及数据转换
     *
     * @param menuDTO 菜单DTO
     * @return 是否成功
     */
    @Transactional
    public Boolean insert(MenuDTO menuDTO) {
        var menu = BeanUtils.copyProperties(menuDTO, Menu::new);
        return insert(menu);
    }

    /**
     * 更新菜单数据
     * 包含各项参数校验及数据转换
     *
     * @param menu 菜单
     * @return 是否成功
     */
    @Transactional
    public Boolean update(Menu menu) {
        var menuId = menu.getId();

        // 检查菜单是否存在
        var old_menu = selectById(menuId);
        if (old_menu == null) throw new BizException("该菜单不存在");

        // 校验授权码是否重复
        var permission = menu.getPermission();
        var old_permission = menu.getPermission();
        if (StringUtils.isNotEmpty(permission) && !permission.equals(old_permission)) {
            if (existByPermission(permission))
                throw new BizException("菜单授权码已存在");
        }

        // 校验父级ID是否存在
        var parentId = menu.getParentId();
        if (parentId != null && !existById(parentId)) {
            throw new BizException("父级菜单不存在");
        }

        // 更新并移除缓存
        if (SqlHelper.retBool(menuMapper.updateById(menu))) {
            menuCache.delete();
            return Boolean.TRUE;
        }else {
            log.warn("修改菜单ID：{} 的数据失败", menuId);
            throw new BizException("修改失败");
        }
    }

    /**
     * 更新菜单数据
     * 包含各项参数校验及数据转换
     *
     * @param menuId 菜单Id
     * @param menuDTO 菜单DTO
     * @return 是否成功
     */
    public Boolean update(Long menuId, MenuDTO menuDTO) {
        var menu = BeanUtils.copyProperties(menuDTO, Menu::new, "id");
        menu.setId(menuId);
        return update(menu);
    }

    /**
     * 删除菜单
     * 必须与子菜单进行解绑
     *
     * @param menuId 菜单ID
     * @return 是否成功
     */
    @Transactional
    public Boolean delete(Long menuId) {
        var menu = selectById(menuId);

        // 校验数据
        if (menu == null) throw new BizException("该菜单不存在");
        if (!existByParentId(menuId)) throw new BizException("存在子菜单, 无法删除");

        // 删除并移除缓存
        if (SqlHelper.retBool(menuMapper.deleteById(menuId))) {
            menuCache.delete();
            return Boolean.TRUE;
        }else {
            log.warn("删除菜单ID：{} 数据失败", menuId);
            throw new BizException("删除失败");
        }
    }

}
