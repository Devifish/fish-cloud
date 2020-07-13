package cn.devifish.cloud.user.server.controller;

import cn.devifish.cloud.user.common.entity.Role;
import cn.devifish.cloud.user.server.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * RoleController
 *
 *
 * @author Devifish
 * @date 2020/7/13 10:45
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /**
     * 根据角色ID查询角色数据
     *
     * @param roleId 角色ID
     * @return 角色
     */
    @GetMapping("/select/id/{roleId}")
    public Role selectById(@PathVariable Long roleId) {
        return roleService.selectById(roleId);
    }

    /**
     * 根据用户ID查询角色数据
     *
     * @param userId 用户ID
     * @return List<Role>
     */
    @GetMapping("/select/userId/{userId}")
    public List<Role> selectByUserId(@PathVariable Long userId) {
        return Collections.emptyList();
    }

}
