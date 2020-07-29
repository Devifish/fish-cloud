package cn.devifish.cloud.common.core.util;

import cn.devifish.cloud.common.core.exception.UtilException;
import org.springframework.util.CollectionUtils;

import java.net.*;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.function.Function;

/**
 * NetworkUtil
 * 网络工具类
 *
 * @author Devifish
 * @date 2020/7/3 10:42
 */
public class NetworkUtils {

    /**
     * 获取本机网卡IP地址，规则如下：
     *
     * <pre>
     * 1. 查找所有网卡地址，必须非回路（loopback）地址、非局域网地址（siteLocal）、IPv4地址
     * 2. 如果无满足要求的地址，调用 {@link InetAddress#getLocalHost()} 获取地址
     * </pre>
     *
     * 此方法不会抛出异常，获取失败将返回<code>null</code><br>
     *
     * 见：https://github.com/looly/hutool/issues/428
     *
     * @return 本机网卡IP地址，获取失败返回<code>null</code>
     * @since 3.0.1
     */
    public static InetAddress getLocalhost() {
        final LinkedHashSet<InetAddress> localAddressList = getLocalAddressList(address -> {
            // 非loopback地址，指127.*.*.*的地址
            return !address.isLoopbackAddress()
                    // 非地区本地地址，指10.0.0.0 ~ 10.255.255.255、172.16.0.0 ~ 172.31.255.255、192.168.0.0 ~ 192.168.255.255
                    && !address.isSiteLocalAddress()
                    // 需为IPV4地址
                    && address instanceof Inet4Address;
        });

        if (!CollectionUtils.isEmpty(localAddressList)) {
            return localAddressList.iterator().next();
        }

        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // ignore
        }

        return null;
    }



    /**
     * 获取所有满足过滤条件的本地IP地址对象
     *
     * @param addressFilter 过滤器，null表示不过滤，获取所有地址
     * @return 过滤后的地址对象列表
     * @since 4.5.17
     */
    public static LinkedHashSet<InetAddress> getLocalAddressList(Function<InetAddress, Boolean> addressFilter) {
        Enumeration<NetworkInterface> networkInterfaces;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new UtilException(e);
        }

        if (networkInterfaces == null) {
            throw new UtilException("Get network interface error!");
        }

        final LinkedHashSet<InetAddress> ipSet = new LinkedHashSet<>();

        while (networkInterfaces.hasMoreElements()) {
            final NetworkInterface networkInterface = networkInterfaces.nextElement();
            final Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                final InetAddress inetAddress = inetAddresses.nextElement();
                if (inetAddress != null && (null == addressFilter || addressFilter.apply(inetAddress))) {
                    ipSet.add(inetAddress);
                }
            }
        }

        return ipSet;
    }

    /**
     * 获得本机MAC地址
     *
     * @return 本机MAC地址
     */
    public static String getLocalMacAddress() {
        return getMacAddress(getLocalhost());
    }

    /**
     * 获得指定地址信息中的MAC地址，使用分隔符“-”
     *
     * @param inetAddress {@link InetAddress}
     * @return MAC地址，用-分隔
     */
    public static String getMacAddress(InetAddress inetAddress) {
        return getMacAddress(inetAddress, "-");
    }

    /**
     * 获得指定地址信息中的MAC地址
     *
     * @param inetAddress {@link InetAddress}
     * @param separator 分隔符，推荐使用“-”或者“:”
     * @return MAC地址，用-分隔
     */
    public static String getMacAddress(InetAddress inetAddress, String separator) {
        if (null == inetAddress) {
            return null;
        }

        byte[] mac;
        try {
            mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
        } catch (SocketException e) {
            throw new UtilException(e);
        }
        if (null != mac) {
            final StringBuilder sb = new StringBuilder();
            String s;
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append(separator);
                }
                // 字节转换为整数
                s = Integer.toHexString(mac[i] & 0xFF);
                sb.append(s.length() == 1 ? 0 + s : s);
            }
            return sb.toString();
        }
        return null;
    }

}
