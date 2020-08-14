package cn.devifish.cloud.common.core.util;

import cn.devifish.cloud.common.core.exception.UtilException;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * DigestUtils
 * 摘要计算工具
 *
 * @author Devifish
 * @date 2020/8/12 17:38
 */
public class DigestUtils {

    private static final int DEFAULT_BUFFER_SIZE = 4096;

    /**
     * 计算文件的md5值
     *
     * @param stream 输入流
     * @return MD5 HashCode
     */
    public static String digest(DigestAlgorithms algorithm, InputStream stream) {
        try {
            var digest = MessageDigest.getInstance(algorithm.getValue());
            var buffer = new byte[DEFAULT_BUFFER_SIZE];

            // 计算文件MD5
            var length = -1;
            while ((length = stream.read(buffer, 0, DEFAULT_BUFFER_SIZE)) != -1) {
                digest.update(buffer, 0, length);
            }

            // 转换为16进制字符串
            var encode = Hex.encode(digest.digest());
            return String.valueOf(encode);
        } catch (Exception e) {
            throw new UtilException(e);
        }
    }

    /**
     * 计算文件的md5值
     *
     * @param file 上传文件流
     * @return MD5 HashCode
     */
    public static String digest(DigestAlgorithms algorithm, MultipartFile file) throws IOException {
        try (var inputStream = file.getInputStream()) {
            return digest(algorithm, inputStream);
        }
    }

    /**
     * 摘要算法
     */
    public enum DigestAlgorithms {

        MD5("MD5"),
        SHA_1("SHA-1");

        private final String value;

        DigestAlgorithms(String algorithm) {
            this.value = algorithm;
        }

        public String getValue() {
            return value;
        }
    }

}
