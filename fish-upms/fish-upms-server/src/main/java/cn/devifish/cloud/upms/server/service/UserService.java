package cn.devifish.cloud.upms.server.service;

import cn.devifish.cloud.common.core.constant.RegexpConstant;
import cn.devifish.cloud.common.core.exception.BizException;
import cn.devifish.cloud.common.core.util.BeanUtils;
import cn.devifish.cloud.common.mybatis.Page;
import cn.devifish.cloud.common.security.BasicUser;
import cn.devifish.cloud.common.security.util.AuthorityUtils;
import cn.devifish.cloud.common.security.util.SecurityUtils;
import cn.devifish.cloud.upms.common.dto.UserDTO;
import cn.devifish.cloud.upms.common.dto.UserPageDTO;
import cn.devifish.cloud.upms.common.entity.User;
import cn.devifish.cloud.upms.common.enums.SexEnum;
import cn.devifish.cloud.upms.common.enums.SmsCodeType;
import cn.devifish.cloud.upms.common.vo.UserVO;
import cn.devifish.cloud.upms.server.cache.UserCache;
import cn.devifish.cloud.upms.server.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Objects;

import static cn.devifish.cloud.common.core.MessageCode.PreconditionFailed;

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
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;
    private final UserCache userCache;
    private final RoleService roleService;
    private final OAuthService oauthService;
    private final PasswordEncoder passwordEncoder;
    private final SmsCodeService smsCodeService;

    /**
     * 根据用户ID查询单个信息
     *
     * @param userId 用户ID
     * @return User
     */
    public User selectById(Long userId) {
        if (userId == null)
            throw new BizException(PreconditionFailed, "用户ID不能为空");

        return userCache.getIfAbsent(userId, userMapper::selectById);
    }

    /**
     * 获取当前用户数据
     *
     * @return User
     */
    public User currentUser() {
        var principalOpt = SecurityUtils.getPrincipalOpt();
        if (principalOpt.isEmpty()) throw new BizException("获取当前用户失败");

        var basicUser = principalOpt.get();
        return selectById(basicUser.getUserId());
    }

    /**
     * 分页查询用户数据
     *
     * @param param 查询参数
     * @return Page<UserVO>
     */
    public Page<UserVO> selectPage(UserPageDTO param) {
        return userMapper.selectPage(Page.of(param), param)
            .map(item -> BeanUtils.copyProperties(item, UserVO::new));
    }

    /**
     * 根据用户名称查询用户数据
     *
     * @param username 用户名
     * @return User
     */
    public User selectByUsername(String username) {
        if (StringUtils.isEmpty(username))
            throw new BizException(PreconditionFailed, "用户名不能为空");

        return userMapper.selectByUsername(username);
    }

    /**
     * 根据手机号查询用户数据
     *
     * @param telephone 手机号
     * @return User
     */
    public User selectByTelephone(String telephone) {
        if (StringUtils.isEmpty(telephone))
            throw new BizException(PreconditionFailed, "手机号不能为空");

        return userMapper.selectByTelephone(telephone);
    }

    /**
     * 适配 Spring Cloud OAuth2 接口
     *
     * @param username 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException 用户未找到异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = selectByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("Not found Username: " + username);

        //获取用户权限
        var userId = user.getId();
        var authorities = roleService.selectAuthoritiesByUserId(userId);
        var authorityList = ArrayUtils.isEmpty(authorities)
            ? Collections.<GrantedAuthority>emptyList()
            : AuthorityUtils.createAuthorityList(authorities);

        return new BasicUser(user.getId(), user.getUsername(), user.getPassword(),
            user.getEnabled(), true, true,
            true, authorityList);
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
     * 根据手机号查询是否存在
     *
     * @param telephone 手机号查
     * @return boolean
     */
    public Boolean existByTelephone(String telephone) {
        if (StringUtils.isEmpty(telephone)) return false;

        // 获取用户统计数据
        var count = SqlHelper.retCount(userMapper.countByTelephone(telephone));
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
        if (StringUtils.isEmpty(username)) throw new BizException(PreconditionFailed, "用户名不能为空");
        if (StringUtils.isEmpty(password)) throw new BizException(PreconditionFailed, "密码不能为空");
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
        } else {
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
        } else {
            log.warn("修改用户：{} 的用户信息失败", username);
            throw new BizException("修改失败");
        }
    }

    /**
     * 更新用户信息
     * 包含各项参数校验及数据转换
     *
     * @param username 用户名
     * @param userDTO 用户参数
     * @return 是否成功
     */
    @Transactional
    public Boolean update(String username, UserDTO userDTO) {
        var user = selectByUsername(username);
        if (user == null) throw new BizException("该用户不存在");
        if (user.getLocked()) throw new BizException("无法修改已锁定用户");

        BeanUtils.copyProperties(userDTO, user);
        return update(user);
    }

    /**
     * 根据用户ID删除用户
     *
     * @param userId 用户ID
     * @return 是否成功
     */
    @Transactional
    public Boolean delete(Long userId) {
        var user = selectById(userId);
        if (user == null) throw new BizException("该用户不存在");

        // 校验参数
        var principal = SecurityUtils.getPrincipal();
        if (user.getLocked()) throw new BizException("无法删除已锁定用户");
        if (Objects.equals(user.getId(), principal.getUserId()))
            throw new BizException("无法删除本用户");

        // 更新并移除缓存
        if (SqlHelper.retBool(userMapper.deleteById(userId))) {
            userCache.delete(userId);
            return Boolean.TRUE;
        } else {
            log.warn("删除用户ID：{} 失败", userId);
            throw new BizException("删除失败");
        }
    }

    /**
     * 发送用户登录验证码
     *
     * @param telephone 电话号码
     * @return 是否成功
     */
    public Boolean sendSmsCode(String telephone, SmsCodeType type) {
        if (type == null) throw new BizException(PreconditionFailed, "请输入正确的短信验证码类型");
        if (StringUtils.isNotEmpty(telephone) && !telephone.matches(RegexpConstant.PHONE_NUM))
            throw new BizException(PreconditionFailed, "请输入正确的手机号");

        // 校验用户是否存在
        var exist = existByTelephone(telephone);
        switch (type) {
            case UserLogin:
            case ResetPassword:
                if (!exist) throw new BizException("手机号不存在, 请注册或绑定后尝试");
                break;
            case UserRegister:
                if (exist) throw new BizException("该手机号已注册");
                break;
            default:
                throw new BizException("未知短信验证码类型");
        }

        // 发送验证码
        var code = smsCodeService.generate(telephone, SmsCodeType.UserLogin);
        if (StringUtils.isEmpty(code)) throw new BizException("请稍后尝试发送验证码");
        // TODO 请求发送验证码
        return Boolean.TRUE;
    }

}
