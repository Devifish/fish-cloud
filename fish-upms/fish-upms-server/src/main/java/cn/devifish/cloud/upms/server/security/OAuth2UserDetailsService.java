package cn.devifish.cloud.upms.server.security;

import cn.devifish.cloud.common.security.BasicUser;
import cn.devifish.cloud.upms.common.entity.User;
import cn.devifish.cloud.upms.server.service.RoleService;
import cn.devifish.cloud.upms.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;

/**
 * OAuth2UserDetailsService
 * OAuth2 用户详情服务实现
 *
 * @author Devifish
 * @date 2020/7/6 11:22
 * @see org.springframework.security.core.userdetails.UserDetailsService
 */
@Service
@RequiredArgsConstructor
public class OAuth2UserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final RoleService roleService;

    /**
     * 适配 Spring Cloud OAuth2 接口
     *
     * @param username 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException 用户未找到异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.selectByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("Not found Username: " + username);

        return buildUserDetails(user);
    }

    /**
     * 构建 UserDetails
     *
     * @param user User
     * @return UserDetails
     */
    private UserDetails buildUserDetails(User user) {
        Assert.notNull(user, "user不能为NULL");

        //获取用户权限
        var userId = user.getId();
        var authorities = roleService.selectAuthoritiesByUserId(userId);
        var authorityList = ArrayUtils.isEmpty(authorities)
                ? Collections.<GrantedAuthority>emptyList()
                : AuthorityUtils.createAuthorityList(authorities);

        return new BasicUser(user.getId(), user.getUsername(), user.getPassword(),
                user.getEnabled(), true, true,
                !user.getLocked(), authorityList);
    }
}
