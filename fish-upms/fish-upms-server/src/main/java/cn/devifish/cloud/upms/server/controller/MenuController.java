package cn.devifish.cloud.upms.server.controller;

import cn.devifish.cloud.upms.common.dto.MenuDTO;
import cn.devifish.cloud.upms.common.entity.Menu;
import cn.devifish.cloud.upms.common.vo.MenuTree;
import cn.devifish.cloud.upms.server.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public Set<MenuTree> selectMenuTree() {
        return menuService.selectMenuTree();
    }

    /**
     * 根据用户ID查询菜单树
     * 构建树结构数据
     * 方便前端构建菜单
     *
     * @return TreeSet<Menu>
     */
    @GetMapping("/select/menu-tree/user-id/{userId}")
    public Set<MenuTree> selectMenuTreeByUserId(@PathVariable Long userId) {
        return menuService.selectMenuTreeByUserId(userId);
    }

    /**
     * 查询当前用户的菜单树
     * 构建树结构数据
     * 方便前端构建菜单
     *
     * @return TreeSet<Menu>
     */
    @GetMapping("/current/menu-tree")
    public Set<MenuTree> currentMenuTree() {
        return menuService.currentMenuTree();
    }

    /**
     * 保存菜单数据
     * 包含各项参数校验及数据转换
     *
     * @param menuDTO 菜单DTO
     * @return 是否成功
     */
    @PostMapping("/insert")
    public Boolean insert(@Valid @RequestBody MenuDTO menuDTO) {
        return menuService.insert(menuDTO);
    }

    /**
     * 更新菜单数据
     * 包含各项参数校验及数据转换
     *
     * @param menuId 菜单Id
     * @param menuDTO 菜单DTO
     * @return 是否成功
     */
    @PutMapping("/update/{menuId}")
    public Boolean update(@PathVariable Long menuId, @Valid @RequestBody MenuDTO menuDTO) {
        return menuService.update(menuId, menuDTO);
    }

    /**
     * 删除菜单
     * 必须与子菜单进行解绑
     *
     * @param menuId 菜单ID
     * @return 是否成功
     */
    @DeleteMapping("/delete/{menuId}")
    public Boolean delete(@PathVariable Long menuId) {
        return menuService.delete(menuId);
    }

}
