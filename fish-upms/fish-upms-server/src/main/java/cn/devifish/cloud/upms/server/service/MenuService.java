package cn.devifish.cloud.upms.server.service;

import cn.devifish.cloud.upms.common.entity.Menu;
import cn.devifish.cloud.upms.server.mapper.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    /**
     * 根据菜单ID查询菜单数据
     *
     * @param menuId 菜单ID
     * @return 角色
     */
    public Menu selectById(Long menuId) {
        return menuMapper.selectById(menuId);
    }

}
