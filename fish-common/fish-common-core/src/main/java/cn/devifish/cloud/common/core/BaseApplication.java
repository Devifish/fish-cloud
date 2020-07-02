package cn.devifish.cloud.common.core;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;

/**
 * BaseApplication
 *
 * @author Devifish
 * @date 2020/7/2 11:18
 */
public abstract class BaseApplication {

    private static final Banner DEFAULT_BANNER = new FishCloudBanner();

    protected static void run(Class<?> primarySource, String... args) {
        SpringApplication application = new SpringApplication(primarySource);
        application.setBanner(DEFAULT_BANNER);
        application.setBannerMode(Banner.Mode.CONSOLE);
        application.run(args);
    }
}
