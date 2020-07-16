package cn.devifish.cloud.upms.server.controller;

import cn.devifish.cloud.upms.common.entity.Menu;
import cn.devifish.cloud.upms.server.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * MenuController
 * 菜单接口
 *
 * @author Devifish
 * @date 2020/7/16 10:58
 */
@Controller
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

}
