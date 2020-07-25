package cn.devifish.cloud.common.security.config;

import cn.devifish.cloud.common.security.annotation.OpenApi;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RegExUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * OpenApiUrlProperties
 *
 * @author Devifish
 * @date 2020/7/16 16:07
 */
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "security.oauth2.open-api")
public class OpenApiUrlProperties implements InitializingBean {

    private static final Pattern PATH_VARIABLE_PATTERN = Pattern.compile("\\{(.*?)}");

    private final RequestMappingHandlerMapping handlerMapping;

    @Getter
    @Setter
    private List<String> urls = new ArrayList<>();

    @Override
    public void afterPropertiesSet() {
        var openApiUrlPatterns = scannerOpenApiRequestMappingUrlPatterns();

        //添加注解获取到的URL
        urls.addAll(openApiUrlPatterns);
    }

    /**
     * 扫描添加 {@link OpenApi}注解 RequestMapping接口
     * 获取接口URL加入请求白名单
     *
     * @return OpenAPI白名单
     */
    public Set<String> scannerOpenApiRequestMappingUrlPatterns() {
        var handlerMethods = handlerMapping.getHandlerMethods();
        var handlerMethodEntry = handlerMethods.entrySet();

        //扫描Spring MVC HandlerMapping添加OpenApi注解接口并转换成UrlPattern
        return handlerMethodEntry.stream()
                .filter(entry -> {
                    var handlerMethod = entry.getValue();
                    return AnnotationUtils.findAnnotation(handlerMethod.getMethod(), OpenApi.class) != null
                            || AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), OpenApi.class) != null;
                })
                .map(entry -> {
                    var requestMappingInfo = entry.getKey();
                    return requestMappingInfo
                            .getPatternsCondition()
                            .getPatterns();
                })
                .flatMap(Collection::stream)
                .map(pattern -> RegExUtils.replaceAll(pattern, PATH_VARIABLE_PATTERN, "*"))
                .collect(Collectors.toSet());
    }

}
