package cn.devifish.cloud.file.server.service;

import cn.devifish.cloud.common.core.exception.BizException;
import cn.devifish.cloud.file.common.entity.Base64FileData;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * StorageService
 * 存储服务
 *
 * @author Devifish
 * @date 2020/8/3 16:37
 */
public abstract class AbstractStorageService {

    private static final String PATH_SEPARATOR = "/";
    private static final String DOT = ".";

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
    protected abstract String upload(String path, InputStream inputStream) throws IOException;

    /**
     * 文件上传
     *
     * @param path 上传路径
     * @param content 文件数据
     * @return 服务端路径
     */
    public String upload(String path, byte[] content) throws IOException {
        if (ArrayUtils.isEmpty(content))
            throw new BizException("内容不能为NULL");

        try (InputStream inputStream = new ByteArrayInputStream(content)) {
            var fullPath = joinPath(pathPrefix(), path);
            return upload(fullPath, inputStream);
        }
    }

    /**
     * 文件上传（表单方式）
     *
     * @param file 文件
     * @return 路径
     */
    public String uploadByMultipart(MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new BizException("上传文件不能为空");

        try (InputStream inputStream = file.getInputStream()) {
            var originalFilename = file.getOriginalFilename();
            var filename = generateFilename(FilenameUtils.getExtension(originalFilename));
            var fullPath = joinPath(pathPrefix(), filename);
            return upload(fullPath, inputStream);
        }
    }

    /**
     * 文件上传（Base64方式）
     *
     * @param data Base64文件数据
     * @return 路径
     */
    public String uploadByBase64(Base64FileData data) throws IOException {
        var content = data.getContent();
        var bytes = Base64.decodeBase64(content);

        return upload(data.getFilename(), bytes);
    }

    /**
     * 拼接文件路径
     *
     * @param path 路径
     * @return 完成拼接路径
     */
    protected String joinPath(String... path) {
        var builder = new StringBuilder();
        for (String temp : path) {
            if (StringUtils.isEmpty(temp)) continue;
            if (temp.endsWith(PATH_SEPARATOR))
                temp = StringUtils.removeEnd(temp, PATH_SEPARATOR);

            if (temp.startsWith(PATH_SEPARATOR)) {
                builder.append(temp);
                continue;
            }

            builder.append(PATH_SEPARATOR)
                .append(temp);
        }

        return builder.toString();
    }

    /**
     * 随机构建文件名
     *
     * @return 文件名
     */
    protected String generateFilename(String fileExtension) {
        Assert.notNull(fileExtension, "文件拓展名不能为空");
        var uuid = UUID.randomUUID();
        var fileNameBuilder = new StringBuilder(uuid.toString());

        if (StringUtils.startsWith(fileExtension, DOT)) {
            fileNameBuilder.append(fileExtension);
        }else {
            fileNameBuilder.append(DOT).append(fileExtension);
        }
        return fileNameBuilder.toString();
    }

}
