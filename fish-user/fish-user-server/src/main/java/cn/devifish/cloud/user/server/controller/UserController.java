package cn.devifish.cloud.user.server.controller;

import cn.devifish.cloud.user.common.vo.UserVo;
import cn.devifish.cloud.user.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 * 用户接口
 *
 * @author Devifish
 * @date 2020/7/1 11:27
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 根据用户ID查询单个信息
     *
     * @param userId 用户ID
     * @return User
     */
    @GetMapping("/select/{userId}")
    public UserVo selectById(@PathVariable Long userId) {
        return userService.selectVoById(userId);
    }
}
