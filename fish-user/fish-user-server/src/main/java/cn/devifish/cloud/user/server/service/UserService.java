package cn.devifish.cloud.user.server.service;

import cn.devifish.cloud.common.core.exception.BizException;
import cn.devifish.cloud.common.security.BasicUser;
import cn.devifish.cloud.common.security.util.SecurityUtil;
import cn.devifish.cloud.user.common.entity.User;
import cn.devifish.cloud.user.common.vo.UserVo;
import cn.devifish.cloud.user.server.cache.UserCache;
import cn.devifish.cloud.user.server.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
    private final UserCache userCache;

    /**
     * 根据用户ID查询单个信息
     *
     * @param id 用户ID
     * @return User
     */
    public User selectById(Long id) {
        return userCache.getIfAbsent(id, userMapper::selectById);
    }

    /**
     * 根据用户ID查询单个信息
     *
     * @param id 用户ID
     * @return User
     */
    public UserVo selectVoById(Long id) {
        User user = selectById(id);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

    /**
     * 获取当前用户数据
     *
     * @return UserVo
     */
    public UserVo currentUserVo() {
        BasicUser principal = SecurityUtil.getPrincipal();
        if (principal == null) throw new BizException("获取当前用户失败");

        return selectVoById(principal.getUserId());
    }

    /**
     * 根据用户名称查询用户数据
     *
     * @param username 用户名
     * @return User
     */
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

}
