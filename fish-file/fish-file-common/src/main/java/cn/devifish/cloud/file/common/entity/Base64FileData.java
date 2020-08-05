package cn.devifish.cloud.file.common.entity;

import cn.devifish.cloud.common.core.util.RegexpUtils;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
    @NotEmpty
    private String filename;

    /** Base64内容 **/
    @Pattern(regexp = RegexpUtils.BASE64_FILE)
    private String content;

}
