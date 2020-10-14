package cn.devifish.cloud.upms.common.vo;

import cn.devifish.cloud.upms.common.enums.SexEnum;
import lombok.Data;

import java.time.LocalDate;

/**
 * UserVo
 * 用户Vo
 *
 * @author Devifish
 * @date 2020/7/11 15:08
 */
@Data
public class UserVO {

    /** 主键ID **/
    private Long id;

    /** 用户名 **/
    private String username;

    /** 昵称 **/
    private String nickname;

    /** 真实姓名 **/
    private String realname;

    /** 头像 **/
    private String avatar;

    /** 邮箱 **/
    private String email;

    /** 手机号 **/
    private String telephone;

    /** 性别 **/
    private SexEnum sex;

    /** 生日 **/
    private LocalDate birthday;

    /** 是否启用 **/
    private Boolean enabled;

    /** 是否锁定 **/
    private Boolean locked;

}
