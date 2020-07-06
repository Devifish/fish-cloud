package cn.devifish.cloud.user.server.security;

import cn.devifish.cloud.user.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * OAuth2UserDetailsService
 * OAuth2 用户详情服务实现
 *
 * @see org.springframework.security.core.userdetails.UserDetailsService
 * @author Devifish
 * @date 2020/7/6 11:22
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
        return null;
    }
}
