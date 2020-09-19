package cn.devifish.cloud.upms.common.dto;

import cn.devifish.cloud.upms.common.enums.SexEnum;
import lombok.Data;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * UserDTO
 * 用户DTO
 *
 * @author Devifish
 * @date 2020/7/29 15:45
 */
@Data
public class UserDTO implements Serializable {

    /** 密码 **/
    private String password;

    /** 昵称 **/
    private String nickname;

    /** 真实姓名 **/
    private String realname;

    /** 头像 **/
    private String avatar;

    /** 邮箱 **/
    @Email
    private String email;

    /** 手机号 **/
    private String telephone;

    /** 性别 **/
    private SexEnum sex;

    /** 生日 **/
    private LocalDate birthday;

}
