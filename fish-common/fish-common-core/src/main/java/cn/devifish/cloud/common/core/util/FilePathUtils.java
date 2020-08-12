package cn.devifish.cloud.common.core.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.UUID;

/**
 * FilePathUtils
 * 文件路径工具类
 *
 * @author Devifish
 * @date 2020/8/12 17:11
 */
public class FilePathUtils {

    protected static final String PATH_SEPARATOR = "/";
    protected static final String EXTENSION_SEPARATOR = ".";

    /**
     * 拼接文件路径
     * 默认在末尾拼接斜杠
     * 如果前路径存在斜杠则会移除
     *
     * @param path 路径
     * @return 完成拼接路径
     */
    public static String joinPath(String... path) {
        var builder = new StringBuilder();
        for (var i = 0; i < path.length; i++) {
            var temp = path[i];

            if (StringUtils.isEmpty(temp)) continue;
            if (temp.startsWith(PATH_SEPARATOR))
                temp = StringUtils.removeStart(temp, PATH_SEPARATOR);

            // 当存在斜杠或存在文件拓展名时则直接拼接
            var isEnd = i + 1 == path.length;
            if (temp.endsWith(PATH_SEPARATOR) || (isEnd && temp.contains(EXTENSION_SEPARATOR))) {
                builder.append(temp);
                continue;
            }

            builder.append(temp)
                .append(PATH_SEPARATOR);
        }

        return builder.toString();
    }

    /**
     * 随机构建文件名
     *
     * @return 文件名
     */
    public static String generateFilename(String fileExtension) {
        Assert.notNull(fileExtension, "文件拓展名不能为空");
        var uuid = UUID.randomUUID();
        var fileNameBuilder = new StringBuilder(uuid.toString());

        if (StringUtils.startsWith(fileExtension, EXTENSION_SEPARATOR)) {
            fileNameBuilder.append(fileExtension);
        }else {
            fileNameBuilder.append(EXTENSION_SEPARATOR).append(fileExtension);
        }
        return fileNameBuilder.toString();
    }

    public static String getFilename(String filename) {
        if (filename == null) {
            return null;
        }
        int index = indexOfLastSeparator(filename);
        return filename.substring(index + 1);
    }

    public static String getExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int index = indexOfExtension(filename);
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }

    public static int indexOfExtension(String filename) {
        if (filename == null) {
            return -1;
        }
        int extensionPos = filename.lastIndexOf(EXTENSION_SEPARATOR);
        int lastSeparator = indexOfLastSeparator(filename);
        return lastSeparator > extensionPos ? -1 : extensionPos;
    }

    public static int indexOfLastSeparator(String filename) {
        if (filename == null) {
            return -1;
        }

        return filename.lastIndexOf(PATH_SEPARATOR);
    }
}
