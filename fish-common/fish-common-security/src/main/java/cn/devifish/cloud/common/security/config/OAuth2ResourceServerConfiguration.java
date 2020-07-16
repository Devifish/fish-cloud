package cn.devifish.cloud.common.security.config;

import cn.devifish.cloud.common.security.annotation.OpenApi;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RegExUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Collection;
import java.util.regex.Pattern;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

/**
 * OAuth2ResourceServerConfiguration
 * OAuth2资源服务公共配置
 * 当前环境存在 {@link AuthorizationServerConfigurer} 授权服务配置时
 * 停止加载该OAuth2资源服务配置 Bean
 *
 * @author Devifish
 * @date 2020/7/9 17:25
 */
@EnableResourceServer
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class OAuth2ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final Pattern PATH_VARIABLE_PATTERN = Pattern.compile("\\{(.*?)}");

    private final TokenStore tokenStore;
    private final RequestMappingHandlerMapping handlerMapping;

    /**
     * 加载放行路径
     * 不被安全框架拦截
     *
     * @param http HttpSecurity
     * @throws Exception 异常
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] ignore = {};
        String[] openApiUrlPatterns = scannerOpenApiRequestMappingUrlPatterns();

        //白名单路径放行, 其他需要鉴权
        http.authorizeRequests()
                .antMatchers(ignore).permitAll()
                .antMatchers(openApiUrlPatterns).permitAll()
                .anyRequest().authenticated();
    }

    /**
     * 配置授权、令牌的访问服务
     * 用于资源服务器获取授权信息
     *
     * @param resources spring security
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

    /**
     * 扫描添加 {@link OpenApi}注解 RequestMapping接口
     * 获取接口URL加入请求白名单
     */
    public String[] scannerOpenApiRequestMappingUrlPatterns() {
        var handlerMethods = handlerMapping.getHandlerMethods();
        var handlerMethodEntry = handlerMethods.entrySet();

        //扫描Spring MVC HandlerMapping添加OpenApi注解接口并转换成UrlPattern
        return handlerMethodEntry.stream()
                .filter(entry -> {
                    var handlerMethod = entry.getValue();
                    return findAnnotation(handlerMethod.getMethod(), OpenApi.class) != null
                            || findAnnotation(handlerMethod.getBeanType(), OpenApi.class) != null;
                })
                .map(entry -> {
                    var requestMappingInfo = entry.getKey();
                    return requestMappingInfo
                            .getPatternsCondition()
                            .getPatterns();
                })
                .flatMap(Collection::stream)
                .map(pattern -> RegExUtils.replaceAll(pattern, PATH_VARIABLE_PATTERN, "*"))
                .distinct()
                .toArray(String[]::new);
    }

}
