package cn.devifish.cloud.upms.common.entity;

import cn.devifish.cloud.common.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Dictionary
 * 字典
 *
 * @author Devifish
 * @date 2020/8/1 14:35
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Dictionary extends BaseEntity {

    /** 字典名称 **/
    private String name;

    /** 字典值 **/
    private String value;

    /** 描述 **/
    private String remark;

    /** 父级ID **/
    private Long parentId;

}
