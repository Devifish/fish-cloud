package cn.devifish.cloud.upms.common.entity;

import cn.devifish.cloud.common.core.LogicDeleteEntity;
import cn.devifish.cloud.upms.common.enums.SexEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * User
 * 用户实体类
 *
 * @author Devifish
 * @date 2020/7/7 17:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends LogicDeleteEntity<Long> {

    /** 用户名 **/
    private String username;

    /** 密码 **/
    private String password;

    /** 昵称 **/
    private String nickname;

    /** 真实姓名 **/
    private String realname;

    /** 头像 **/
    private String avatar;

    /** 邮箱 **/
    private String email;

    /** 手机号 **/
    private String mobile;

    /** 性别 **/
    private SexEnum sex;

    /** 生日 **/
    private LocalDate birthday;

    /** 是否启用 **/
    private Boolean enabled;

    /** 是否锁定 **/
    private Boolean locked;

}
