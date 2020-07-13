package cn.devifish.cloud.common.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * BasicUser
 * 基础用户数据
 *
 * @author Devifish
 * @date 2020/7/11 17:23
 */
@Getter
public class BasicUser extends User {

    /**
     * 用户ID
     */
    private final Long userId;

    public BasicUser(Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public BasicUser(Long userId, String username, String password,
                     boolean enabled, boolean accountNonExpired,
                     boolean credentialsNonExpired, boolean accountNonLocked,
                     Collection<? extends GrantedAuthority> authorities) {

        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
    }
}
