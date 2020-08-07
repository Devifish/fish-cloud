package cn.devifish.cloud.common.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.function.Supplier;

/**
 * ProxyUtils
 * 代理工具类
 *
 * @author Devifish
 * @date 2020/8/7 18:12
 */
public class ProxyUtils {

    /**
     * 通过代理实现懒加载对象
     * 仅在调用方法时加载
     *
     * @param type     类型
     * @param supplier 构造器
     * @param <T>      类型泛型
     * @return 懒加载对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T lazy(Class<T> type, Supplier<T> supplier) {
        return (T) Proxy.newProxyInstance(
            ProxyUtils.class.getClassLoader(),
            new Class<?>[]{type},
            (proxy, method, args) -> {
                var methodName = method.getName();

                // 防止equals及hashCode异常
                if (methodName.equals("equals")) return proxy == args[0];
                if (methodName.equals("hashCode")) return System.identityHashCode(proxy);

                try {
                    var typeObj = supplier.get();
                    return method.invoke(typeObj, args);
                } catch (InvocationTargetException ex) {
                    throw ex.getTargetException();
                }
            });
    }

}
