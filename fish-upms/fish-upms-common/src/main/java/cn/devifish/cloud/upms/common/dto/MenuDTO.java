package cn.devifish.cloud.upms.common.dto;

import cn.devifish.cloud.upms.common.enums.MenuType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * MenuDTO
 * 菜单DTO
 *
 * @author Devifish
 * @date 2020/7/29 14:18
 */
@Data
public class MenuDTO implements Serializable {

    /** 菜单名称 **/
    @NotEmpty
    private String name;

    /** 菜单路径 **/
    private String url;

    /** 菜单类型 **/
    private MenuType type;

    /** 授权码 **/
    private String permission;

    /** 图标 **/
    private String icon;

    /** 父级ID **/
    private Long parentId;

    /** 是否启用 **/
    private Boolean enable;

    /** 排序 **/
    private Integer sort;
}
