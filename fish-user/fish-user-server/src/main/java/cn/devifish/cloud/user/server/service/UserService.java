package cn.devifish.cloud.user.server.service;

import cn.devifish.cloud.common.core.exception.BizException;
import cn.devifish.cloud.common.security.BasicUser;
import cn.devifish.cloud.common.security.util.SecurityUtil;
import cn.devifish.cloud.user.common.entity.User;
import cn.devifish.cloud.user.common.vo.UserVo;
import cn.devifish.cloud.user.server.cache.UserCache;
import cn.devifish.cloud.user.server.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
    private final OAuthTokenService oauthTokenService;
    private final PasswordEncoder passwordEncoder;

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

    /**
     * 更具用户ID查询是否存在
     *
     * @param id 用户ID
     * @return boolean
     */
    public boolean existById(Long id) {
        if (id == null) return false;

        //获取用户数据（Cache）
        User user = selectById(id);
        return user != null;
    }

    /**
     * 更新用户信息
     *
     * @param user 用户参数
     * @return 是否成功
     */
    @Transactional
    public Boolean update(User user) {
        if (user == null || user.getId() == null) throw new BizException("用户基础参数不能为空");
        Long userId = user.getId();

        //检查用户是否存在
        User old_user = selectById(userId);
        if (old_user == null) throw new BizException("该用户不存在");

        //校验用户名是否重复
        String username = user.getUsername();
        String old_username = old_user.getUsername();
        if (StringUtils.isNotEmpty(username) && !username.equals(old_username)) {
            User temp = selectByUsername(username);
            if (temp != null) throw new BizException("用户名已存在");
        }

        //是否修改密码 (注销已登陆的所有用户)
        String password = user.getPassword();
        String old_password = old_user.getPassword();
        if (StringUtils.isNotEmpty(password) && !password.equals(old_password)) {
            user.setPassword(passwordEncoder.encode(password));
            Assert.state(oauthTokenService.logoutAllByUsername(username), "用户修改密码注销Token失败");
        }

        //更修并移除缓存
        userCache.delete(userId);
        userMapper.updateById(user);
        return Boolean.TRUE;
    }

}
