package cn.devifish.cloud.upms.server.controller;

import cn.devifish.cloud.upms.common.entity.Dept;
import cn.devifish.cloud.upms.server.service.DeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DeptController
 * 部门接口
 *
 * @author Devifish
 * @date 2020/8/1 16:38
 */
@RestController
@RequestMapping("/dept")
@RequiredArgsConstructor
public class DeptController {

    private final DeptService deptService;

    /**
     * 根据部门ID查询部门数据
     *
     * @param deptId 部门ID
     * @return 部门
     */
    @GetMapping("/select/id/{deptId}")
    public Dept selectById(@PathVariable Long deptId) {
        return deptService.selectById(deptId);
    }

}
