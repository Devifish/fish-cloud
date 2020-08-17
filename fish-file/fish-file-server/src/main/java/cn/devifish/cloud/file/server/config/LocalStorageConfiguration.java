package cn.devifish.cloud.file.server.config;

import cn.devifish.cloud.common.core.util.FilePathUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * LocalStorageConfiguration
 * 本地存储配置
 *
 * @author Devifish
 * @date 2020/8/17 23:36
 */
@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "storage.local.path")
public class LocalStorageConfiguration implements WebMvcConfigurer {

    private final StorageProperties storageProperties;

    /**
     * 注册本地存储目录
     *
     * @param registry ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        var config = storageProperties.getLocal();
        var path = config.getPath();
        var prefix = config.getPrefix();

        registry.addResourceHandler(String.format("/%s/**", prefix))
            .addResourceLocations(FilePathUtils.joinPath(path, prefix))
            .setCacheControl(CacheControl.noCache());
    }
}
