package cn.devifish.cloud.upms.server.service;

import cn.devifish.cloud.common.core.exception.BizException;
import cn.devifish.cloud.common.security.util.SecurityUtil;
import cn.devifish.cloud.upms.common.entity.User;
import cn.devifish.cloud.upms.common.enums.SexEnum;
import cn.devifish.cloud.upms.server.cache.UserCache;
import cn.devifish.cloud.upms.server.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserCache userCache;
    private final OAuthService oauthService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 根据用户ID查询单个信息
     *
     * @param userId 用户ID
     * @return User
     */
    public User selectById(Long userId) {
        if (userId == null)
            throw new BizException("用户ID不能为空");

        return userCache.getIfAbsent(userId, userMapper::selectById);
    }

    /**
     * 获取当前用户数据
     *
     * @return User
     */
    public User currentUser() {
        var principal = SecurityUtil.getPrincipal();
        if (principal == null) throw new BizException("获取当前用户失败");

        return selectById(principal.getUserId());
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
     * 根据用户ID查询是否存在
     *
     * @param id 用户ID
     * @return boolean
     */
    public Boolean existById(Long id) {
        if (id == null) return false;

        // 获取用户数据（Cache）
        var user = selectById(id);
        return user != null;
    }

    /**
     * 根据用户名查询是否存在
     *
     * @param username 用户名
     * @return boolean
     */
    public Boolean existByUsername(String username) {
        if (StringUtils.isEmpty(username)) return false;

        // 获取用户统计数据
        var count = SqlHelper.retCount(userMapper.countByUsername(username));
        return count > 0;
    }

    /**
     * 保存用户信息
     * 包含各项参数校验及数据转换
     *
     * @param user 用户参数
     * @return 是否成功
     */
    @Transactional
    public Boolean insert(User user) {
        var username = user.getUsername();
        var password = user.getPassword();

        // 参数校验
        if (StringUtils.isEmpty(username)) throw new BizException("用户名不能为空");
        if (StringUtils.isEmpty(password)) throw new BizException("密码不能为空");
        if (existByUsername(username)) throw new BizException("用户名已存在");

        // 加密密码
        var encode_password = passwordEncoder.encode(password);
        user.setPassword(encode_password);

        // 设置默认值
        user.setId(null);
        if (user.getSex() == null) user.setSex(SexEnum.Unset);
        if (user.getLocked() == null) user.setLocked(Boolean.FALSE);
        if (user.getEnabled() == null) user.setEnabled(Boolean.TRUE);

        // 保存用户数据
        if (SqlHelper.retBool(userMapper.insert(user))) {
            log.info("注册用户：{} 成功", username);
            return Boolean.TRUE;
        }else {
            log.info("注册用户：{} 失败", username);
            throw new BizException("注册用户失败");
        }
    }

    /**
     * 更新用户信息
     * 包含各项参数校验及数据转换
     *
     * @param user 用户参数
     * @return 是否成功
     */
    @Transactional
    public Boolean update(User user) {
        var userId = user.getId();

        // 检查用户是否存在
        var old_user = selectById(userId);
        if (old_user == null) throw new BizException("该用户不存在");

        // 校验用户名是否重复
        var username = user.getUsername();
        var old_username = old_user.getUsername();
        if (StringUtils.isNotEmpty(username) && !username.equals(old_username)) {
            if (existByUsername(username))
                throw new BizException("用户名已存在");
        }

        // 是否修改密码 (注销已登陆的所有用户)
        var password = user.getPassword();
        var old_password = old_user.getPassword();
        if (StringUtils.isNotEmpty(password) && !password.equals(old_password)) {
            user.setPassword(passwordEncoder.encode(password));
            Assert.state(oauthService.logoutAllByUsername(username), "用户修改密码注销Token失败");
        }

        // 更新并移除缓存
        if (SqlHelper.retBool(userMapper.updateById(user))) {
            userCache.delete(userId);
            return Boolean.TRUE;
        }else {
            log.warn("修改用户：{} 的用户信息失败", username);
            throw new BizException("修改失败");
        }
    }



}
