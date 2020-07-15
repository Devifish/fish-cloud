package cn.devifish.cloud.common.core;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * BaseApplication
 *
 * @author Devifish
 * @date 2020/7/2 11:18
 */
public abstract class BaseApplication {

    private static final Banner DEFAULT_BANNER = new FishCloudBanner();

    protected static void run(Class<?> primarySource, String... args) {
        var application = new SpringApplication(primarySource);
        application.setBanner(DEFAULT_BANNER);
        application.setBannerMode(Banner.Mode.CONSOLE);

        //在启动SpringBoot前禁用JDK非法反射警告
        disableAccessWarnings();
        application.run(args);
    }

    /**
     * 忽略非法反射警告  适用于JDK11
     */
    public static void disableAccessWarnings() {
        try {
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            Field theUnsafe = unsafeClass.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe unsafe = (Unsafe) theUnsafe.get(null);

            Class<?> illegalAccessLoggerClass = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field logger = illegalAccessLoggerClass.getDeclaredField("logger");
            unsafe.putObjectVolatile(illegalAccessLoggerClass, unsafe.staticFieldOffset(logger), null);
        } catch (Exception ignored) { }
    }

}
