package cn.devifish.cloud.upms.common.entity;

import cn.devifish.cloud.common.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Dept
 * 部门
 *
 * @author Devifish
 * @date 2020/8/1 14:43
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Dept extends BaseEntity {

    /** 部门名称 **/
    private String name;

    /** 父级ID **/
    private Integer parentId;

    /** 层级结构 **/
    private String level;

    /** 描述 **/
    private String description;

}
