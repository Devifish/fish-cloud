package cn.devifish.cloud.file.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Base64FileData
 * Base64文件数据
 *
 * @author Devifish
 * @date 2020/8/3 18:01
 */
@Data
public class Base64FileData implements Serializable {

    /** 文件名 **/
    private String filename;

    /** Base64内容 **/
    private String content;

}
