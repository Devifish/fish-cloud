package cn.devifish.cloud.upms.common.vo;

import cn.devifish.cloud.upms.common.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * UserDetailVO
 * 用户详情VO
 *
 * @author Devifish
 * @date 2020/10/13 18:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDetailVO extends UserVO {

    /** 角色 **/
    private List<Role> roles;

}
