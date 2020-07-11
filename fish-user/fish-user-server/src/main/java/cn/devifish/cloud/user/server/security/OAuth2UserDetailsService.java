package cn.devifish.cloud.user.server.security;

import cn.devifish.cloud.user.common.entity.User;
import cn.devifish.cloud.user.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    /**
     * 适配 Spring Cloud OAuth2 接口
     *
     * @param username 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException 用户未找到异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.selectByUsername(username);
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
        String username = user.getUsername();
        String password = user.getPassword();
        Boolean enabled = user.getEnabled();
        Boolean locked = user.getLocked();
        return new org.springframework.security.core.userdetails.User(
                username, password, enabled,
                true, true,
                !locked, Collections.emptySet());
    }
}
