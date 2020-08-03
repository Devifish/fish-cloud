package cn.devifish.cloud.file.common.entity;

import lombok.Data;

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

    /** 文件全路径 **/
    private String fullPath;

}
