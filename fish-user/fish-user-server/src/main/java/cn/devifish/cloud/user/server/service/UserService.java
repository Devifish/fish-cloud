package cn.devifish.cloud.user.server.service;

import cn.devifish.cloud.user.server.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * UserService
 * 用户服务
 *
 * @author Devifish
 * @date 2020/7/6 11:19
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

}
