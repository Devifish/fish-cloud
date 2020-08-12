package cn.devifish.cloud.common.core.util;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * FileUtils
 * 文件工具类
 *
 * @author Devifish
 * @date 2020/8/12 17:38
 */
public class FileUtils {

    /**
     * 计算文件的md5值
     *
     * @param stream 输入流
     * @return MD5 HashCode
     */
    public static String md5HashCode(InputStream stream) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            var buffer = new byte[1024];
            int length = -1;
            while ((length = stream.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }

            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            BigInteger value = new BigInteger(1, md.digest());
            return value.toString(16);
        } catch (Exception e) {
            return null;
        }
    }

}
