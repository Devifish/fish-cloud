package cn.devifish.cloud.user.common.entity;

import cn.devifish.cloud.common.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Role
 * 角色
 *
 * @author Devifish
 * @date 2020/7/13 10:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {

    /** 角色名称 **/
    private String name;

    /** 角色编码 **/
    private String code;

    /** 备注 **/
    private String remark;

    /** 权限 **/
    private String authorities;

    /** 系统角色标记 **/
    private Boolean systemFlag;

}
