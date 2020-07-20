package cn.devifish.cloud.upms.server.controller;

import cn.devifish.cloud.upms.common.entity.Menu;
import cn.devifish.cloud.upms.common.vo.MenuVo;
import cn.devifish.cloud.upms.server.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * MenuController
 * 菜单接口
 *
 * @author Devifish
 * @date 2020/7/16 10:58
 */
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    /**
     * 根据菜单ID查询菜单数据
     *
     * @param menuId 菜单ID
     * @return 角色
     */
    @GetMapping("/select/id/{menuId}")
    public Menu selectById(@PathVariable Long menuId) {
        return menuService.selectById(menuId);
    }

    /**
     * 查询菜单树
     * 构建树结构数据
     * 方便前端构建菜单
     *
     * @return TreeSet<Menu>
     */
    @GetMapping("/select/menu-tree")
    public Set<MenuVo> selectMenuTree() {
        return menuService.selectMenuTree();
    }

    /**
     * 查询当前用户的菜单树
     * 构建树结构数据
     * 方便前端构建菜单
     *
     * @return TreeSet<Menu>
     */
    @GetMapping("/current/menu-tree")
    public Set<MenuVo> currentMenuTree() {
        return menuService.currentMenuTree();
    }

}
