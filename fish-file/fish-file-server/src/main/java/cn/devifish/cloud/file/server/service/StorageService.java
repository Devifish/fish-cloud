package cn.devifish.cloud.file.server.service;

import cn.devifish.cloud.file.common.entity.Base64FileData;
import cn.devifish.cloud.file.common.entity.UploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * StorageService
 * 存储服务
 *
 * @author Devifish
 * @date 2020/8/24 18:08
 */
public interface StorageService {

    /**
     * 文件上传（表单方式）
     *
     * @param file 文件
     * @return 路径
     */
    UploadResult uploadByMultipart(MultipartFile file) throws IOException;

    /**
     * 文件上传（Base64方式）
     *
     * @param data Base64文件数据
     * @return 路径
     */
    UploadResult uploadByBase64(Base64FileData data) throws IOException;

}
