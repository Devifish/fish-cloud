package cn.devifish.cloud.common.security.util;

import cn.devifish.cloud.common.security.UserGrantedAuthority;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * AuthorityUtils
 * 用户权限工具
 *
 * @author Devifish
 * @date 2020/8/3 10:21
 */
public class AuthorityUtils {

    /**
     * 创建 Authority 集合
     *
     * @param authorities authority
     * @return authorityList
     */
    public static Set<GrantedAuthority> createAuthorityList(String... authorities) {
        if (ArrayUtils.isEmpty(authorities)) return Collections.emptySet();

        // 通过UserGrantedAuthority获取权限对象
        return Arrays.stream(authorities)
            .filter(StringUtils::isNotBlank)
            .map(UserGrantedAuthority::getInstance)
            .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * 转换权限对象为权限信息Set
     *
     * @param userAuthorities 用户权限
     * @return 权限信息集合
     */
    public static Set<String> authorityListToSet(Collection<? extends GrantedAuthority> userAuthorities) {
        if (CollectionUtils.isEmpty(userAuthorities)) return Collections.emptySet();

        // 获取权限信息保存于Set
        return userAuthorities.stream()
            .filter(Objects::nonNull)
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toUnmodifiableSet());
    }

}
