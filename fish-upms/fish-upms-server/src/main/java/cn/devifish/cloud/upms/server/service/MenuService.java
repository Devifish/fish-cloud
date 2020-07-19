package cn.devifish.cloud.upms.server.service;

import cn.devifish.cloud.upms.common.entity.Menu;
import cn.devifish.cloud.upms.server.cache.MenuCache;
import cn.devifish.cloud.upms.server.mapper.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

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
        return menuMapper.selectById(menuId);
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
