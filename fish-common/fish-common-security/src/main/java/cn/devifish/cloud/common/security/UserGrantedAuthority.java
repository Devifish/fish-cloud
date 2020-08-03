package cn.devifish.cloud.common.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * UserGrantedAuthority
 * 用户权限
 *
 * @author Devifish
 * @date 2020/8/3 10:15
 * @see org.springframework.security.core.GrantedAuthority
 * @see org.springframework.security.core.authority.SimpleGrantedAuthority
 */
public class UserGrantedAuthority implements GrantedAuthority {

    private static final Map<String, UserGrantedAuthority> grantedAuthorityCacheMap;

    static {
        grantedAuthorityCacheMap = new ConcurrentHashMap<>();
    }

    private final String authority;

    private UserGrantedAuthority(String authority) {
        Assert.hasText(authority, "A granted authority textual representation is required");
        this.authority = authority;
    }

    public static UserGrantedAuthority getInstance(String authority) {
        UserGrantedAuthority userGrantedAuthority = grantedAuthorityCacheMap.get(authority);
        if (userGrantedAuthority == null) {
            userGrantedAuthority = new UserGrantedAuthority(authority);
            grantedAuthorityCacheMap.put(authority, userGrantedAuthority);
        }
        return userGrantedAuthority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
