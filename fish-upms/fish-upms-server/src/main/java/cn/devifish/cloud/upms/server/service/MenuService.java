package cn.devifish.cloud.upms.server.service;

import cn.devifish.cloud.common.core.exception.BizException;
import cn.devifish.cloud.common.security.util.SecurityUtil;
import cn.devifish.cloud.upms.common.entity.Menu;
import cn.devifish.cloud.upms.common.vo.MenuVo;
import cn.devifish.cloud.upms.server.cache.MenuCache;
import cn.devifish.cloud.upms.server.mapper.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * MenuService
 * 菜单服务
 *
 * @author Devifish
 * @date 2020/7/16 10:57
 */
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuMapper menuMapper;
    private final MenuCache menuCache;

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
            throw new BizException("菜单ID不能为空");

        return menuCache.getIfAbsent(menuId, menuMapper::selectById);
    }

    /**
     * 查询全部菜单Vo数据
     *
     * @return List<MenuVo>
     */
    public List<MenuVo> selectAllVo() {
        var menus = selectAll();
        return menus.stream()
                .map(MenuVo::new)
                .collect(Collectors.toList());
    }

    /**
     * 查询菜单树
     * 构建树结构数据
     * 方便前端构建菜单
     *
     * @return TreeSet<Menu>
     */
    public Set<MenuVo> selectMenuTree() {
        return buildMenuVoTree(selectAllVo(), null, null, false);
    }

    /**
     * 查询当前用户的菜单树
     * 构建树结构数据
     * 方便前端构建菜单
     *
     * @return TreeSet<Menu>
     */
    public Set<MenuVo> currentMenuTree() {
        var authorities = SecurityUtil.getAuthorities();
        var authoritiesSet = AuthorityUtils.authorityListToSet(authorities);
        return buildMenuVoTree(selectAllVo(), null, authoritiesSet, true);
    }

    /**
     * 构建菜单树
     *
     * @param collection 所有菜单
     * @param parentId 父级ID
     * @param authorities 权限
     * @param valid 是否校验权限
     * @return 菜单树
     */
    private Set<MenuVo> buildMenuVoTree(Collection<MenuVo> collection, Long parentId, Set<String> authorities, boolean valid) {
        var iterator = collection.iterator();

        Set<MenuVo> temp = null;
        while (iterator.hasNext()) {
            var menuVo = iterator.next();
            if (!Objects.equals(menuVo.getParentId(), parentId)) continue;
            if (valid && StringUtils.isNotEmpty(menuVo.getPermission()) && !authorities.contains(menuVo.getPermission())) continue;
            if (temp == null) temp = new TreeSet<>(Comparator.comparing(MenuVo::getSort,
                    Comparator.nullsLast(Integer::compareTo)).thenComparingLong(MenuVo::getId));

            iterator.remove();
            var childrenMenu = buildMenuVoTree(collection, menuVo.getId(), authorities, valid);
            if (CollectionUtils.isEmpty(childrenMenu)) {
                menuVo.setChildren(Collections.emptySet());
            }else {
                menuVo.setChildren(childrenMenu);

                //当存在子节点时则表明集合发生变化，需重置迭代器
                iterator = collection.iterator();
            }
            temp.add(menuVo);
        }
        return temp;
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

}
