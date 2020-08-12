package cn.devifish.cloud.file.common.entity;

import cn.devifish.cloud.common.core.util.FilePathUtils;
import lombok.Data;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * UploadResult
 * 上传结果信息
 *
 * @author Devifish
 * @date 2020/8/3 18:04
 */
@Data
public class UploadResult implements Serializable {

    /** 文件名 **/
    private String filename;

    /** 文件路径 **/
    private String path;

    /** 域名 **/
    private String domain;

    /** 文件URL **/
    private String url;

    public UploadResult(String domain, String path) {
        Assert.notNull(domain, "域名不能为空");
        Assert.notNull(path, "路径不能为空");

        var filename = FilePathUtils.getFilename(path);
        var url = FilePathUtils.joinPath(domain, path);

        this.filename = filename;
        this.path = path;
        this.domain = domain;
        this.url = url;
    }

}
