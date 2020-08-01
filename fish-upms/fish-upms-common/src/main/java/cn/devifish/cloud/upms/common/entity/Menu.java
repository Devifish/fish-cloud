package cn.devifish.cloud.upms.common.entity;

import cn.devifish.cloud.common.core.BaseEntity;
import cn.devifish.cloud.upms.common.enums.MenuType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Menu
 * 菜单
 *
 * @author Devifish
 * @date 2020/7/16 10:35
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Menu extends BaseEntity<Long> {

    /** 菜单名称 **/
    private String name;

    /** 菜单路径 **/
    private String url;

    /** 菜单类型 **/
    private MenuType type;

    /** 授权码 **/
    private String permission;

    /** 父级ID **/
    private Long parentId;

    /** 是否启用 **/
    private Boolean enable;

    /** 排序 **/
    private Integer sort;

}
