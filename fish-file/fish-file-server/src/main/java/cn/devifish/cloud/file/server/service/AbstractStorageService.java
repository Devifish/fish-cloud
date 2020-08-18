package cn.devifish.cloud.file.server.service;

import cn.devifish.cloud.common.core.exception.BizException;
import cn.devifish.cloud.common.core.util.DigestUtils;
import cn.devifish.cloud.common.core.util.DigestUtils.DigestAlgorithms;
import cn.devifish.cloud.file.common.entity.Base64FileData;
import cn.devifish.cloud.file.common.entity.UploadResult;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import static cn.devifish.cloud.common.core.util.FilePathUtils.*;

/**
 * StorageService
 * 存储服务
 *
 * @author Devifish
 * @date 2020/8/3 16:37
 */
public abstract class AbstractStorageService {

    private static final String MONTH_DIR_PATTERN = "yyyy/MM";
    private static final String BASE64_FILE_PATTERN = "^data:(?<type>[\\w/]+);base64,(?<data>[\\w+/=]+)$";

    /**
     * 获取路径前缀
     * 用户自动拼接完整路径使用
     *
     * @return 路径前缀
     */
    protected abstract String pathPrefix();

    /**
     * 文件上传
     *
     * @param path 上传路径
     * @param inputStream 文件流
     * @return 服务端路径
     */
    protected abstract UploadResult upload(String path, InputStream inputStream) throws IOException;

    /**
     * 文件上传
     *
     * @param filename 文件名
     * @param content 文件数据
     * @return 服务端路径
     */
    public UploadResult upload(String filename, byte[] content) throws IOException {
        if (ArrayUtils.isEmpty(content))
            throw new BizException("内容不能为NULL");

        try (InputStream inputStream = new ByteArrayInputStream(content)) {
            var hashCode = DigestUtils.digest(DigestAlgorithms.MD5, inputStream);
            var extension = getExtension(filename);

            // 使用md5校验值代替文件名
            filename = hashCode + EXTENSION_SEPARATOR + extension;
            var fullPath = joinPath(pathPrefix(), currentMonth(), filename);

            // 重置流并上传
            inputStream.reset();
            return upload(fullPath, inputStream);
        }
    }

    /**
     * 文件上传（表单方式）
     *
     * @param file 文件
     * @return 路径
     */
    public UploadResult uploadByMultipart(MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new BizException("上传文件不能为空");

        try (InputStream inputStream = file.getInputStream()) {
            var hashCode = DigestUtils.digest(DigestAlgorithms.MD5, file);
            var extension = getExtension(file.getOriginalFilename());

            // 使用md5校验值代替文件名
            var filename = hashCode + EXTENSION_SEPARATOR + extension;
            var fullPath = joinPath(pathPrefix(), currentMonth(), filename);
            return upload(fullPath, inputStream);
        }
    }

    /**
     * 文件上传（Base64方式）
     *
     * @param data Base64文件数据
     * @return 路径
     */
    public UploadResult uploadByBase64(Base64FileData data) throws IOException {
        var content = data.getContent();
        var matcher = Pattern.compile(BASE64_FILE_PATTERN).matcher(content);
        if (!matcher.find()) throw new BizException("Base64数据格式不匹配");

        // 获取文件名
        var filename = data.getFilename();
        if (StringUtils.isEmpty(filename) || StringUtils.isEmpty(getExtension(filename))) {
            var type = matcher.group("type");
            var extension = RegExUtils.removeAll(type, ".+/");
            filename = generateFilename(extension);
        }

        // 获取文件数据
        var base64Data = matcher.group("data");
        var bytes = Base64.decodeBase64(base64Data);
        return upload(filename, bytes);
    }

    /**
     * 获取当前月份
     *
     * @return 当前月份
     */
    public String currentMonth() {
        var now = LocalDate.now();
        return now.toString(MONTH_DIR_PATTERN);
    }
}
