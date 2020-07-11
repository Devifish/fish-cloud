package cn.devifish.cloud.user.common.vo;

import cn.devifish.cloud.user.common.enums.SexEnum;
import lombok.Data;

/**
 * UserVo
 *
 * @author Devifish
 * @date 2020/7/11 15:08
 */
@Data
public class UserVo {

    /** 主键ID **/
    private Long id;

    /** 用户名 **/
    private String username;

    /** 昵称 **/
    private String nickname;

    /** 真实姓名 **/
    private String realname;

    /** 性别 **/
    private SexEnum sex;

}
