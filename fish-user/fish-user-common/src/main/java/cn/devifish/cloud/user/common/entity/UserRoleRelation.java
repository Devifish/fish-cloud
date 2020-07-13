package cn.devifish.cloud.user.common.entity;

import lombok.Data;

/**
 * UserRoleRelation
 * 用户角色关系
 *
 * @author Devifish
 * @date 2020/7/13 17:18
 */
@Data
public class UserRoleRelation {

    /** 用户Id **/
    private Long userId;

    /** 角色ID **/
    private Long roleId;

}
