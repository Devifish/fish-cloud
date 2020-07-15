package cn.devifish.cloud.upms.common.entity;

import cn.devifish.cloud.common.core.LogicDeleteEntity;
import cn.devifish.cloud.upms.common.enums.SexEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User
 * 用户实体类
 *
 * @author Devifish
 * @date 2020/7/7 17:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends LogicDeleteEntity {

    /** 用户名 **/
    private String username;

    /** 密码 **/
    private String password;

    /** 昵称 **/
    private String nickname;

    /** 真实姓名 **/
    private String realname;

    /** 性别 **/
    private SexEnum sex;

    /** 是否启用 **/
    private Boolean enabled;

    /** 是否锁定 **/
    private Boolean locked;

}
