package cn.devifish.cloud.common.core.util;

import cn.devifish.cloud.common.core.exception.UtilException;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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

            var buffer = new byte[4096];
            int length = -1;
            while ((length = stream.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }

            var encode = Hex.encode(md.digest());
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
    public static String md5HashCode(MultipartFile file) throws IOException {
        try (var inputStream = file.getInputStream()) {
            return md5HashCode(inputStream);
        }
    }

}
