package cn.devifish.cloud.common.core;

import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import sun.misc.Unsafe;

/**
 * BaseApplication
 *
 * @author Devifish
 * @date 2020/7/2 11:18
 */
public abstract class BaseApplication {

    private static final Banner DEFAULT_BANNER = new FishCloudBanner();

    //默认执行禁用JDK非法反射警告
    static { disableAccessWarnings(); }

    protected static void run(Class<?> primarySource, String... args) {
        var application = new SpringApplication(primarySource);
        application.setBanner(DEFAULT_BANNER);
        application.setBannerMode(Banner.Mode.CONSOLE);
        application.run(args);
    }

    /**
     * 禁用JDK非法反射警告
     * (适用于 JDK11及以上版本)
     */
    @SneakyThrows
    public static void disableAccessWarnings() {
        var unsafe = (Unsafe) FieldUtils.readStaticField(
                Unsafe.class, "theUnsafe", true);

        //使用Unsafe修改IllegalAccessLogger为NULL
        var illegalAccessLoggerClass = Class.forName("jdk.internal.module.IllegalAccessLogger");
        var logger = illegalAccessLoggerClass.getDeclaredField("logger");
        var offset = unsafe.staticFieldOffset(logger);
        unsafe.putObjectVolatile(illegalAccessLoggerClass, offset, null);
    }

}
