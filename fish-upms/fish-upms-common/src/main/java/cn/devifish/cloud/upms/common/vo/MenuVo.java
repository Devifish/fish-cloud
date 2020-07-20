package cn.devifish.cloud.upms.common.vo;

import cn.devifish.cloud.upms.common.entity.Menu;
import cn.devifish.cloud.upms.common.enums.MenuType;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * MenuVo
 * 菜单Vo
 *
 * @author Devifish
 * @date 2020/7/20 15:09
 */
@Data
public class MenuVo implements Serializable {

    /** 主键ID **/
    private Long id;

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

    /** 子节点 **/
    private Set<MenuVo> children;

    public MenuVo() {
    }

    public MenuVo(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.url = menu.getUrl();
        this.type = menu.getType();
        this.permission = menu.getPermission();
        this.parentId = menu.getParentId();
        this.enable = menu.getEnable();
        this.sort = menu.getSort();
    }

}
