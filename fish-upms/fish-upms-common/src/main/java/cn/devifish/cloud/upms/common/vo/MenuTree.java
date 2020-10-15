package cn.devifish.cloud.upms.common.vo;

import cn.devifish.cloud.common.core.TreeNode;
import cn.devifish.cloud.upms.common.entity.Menu;
import cn.devifish.cloud.upms.common.enums.MenuType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Comparator;
import java.util.Set;

/**
 * MenuTree
 * 菜单树
 *
 * @author Devifish
 * @date 2020/7/20 15:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends TreeNode<Set<MenuTree>> implements Comparable<MenuTree> {

    /** 菜单名称 **/
    private String name;

    /** 菜单路径 **/
    private String url;

    /** 菜单类型 **/
    private MenuType type;

    /** 授权码 **/
    private String permission;

    /** 图标 **/
    private String icon;

    /** 是否启用 **/
    private Boolean enable;

    /** 排序 **/
    private Integer sort;

    public MenuTree() {
        super();
    }

    public MenuTree(Menu menu) {
        super(menu.getId(), menu.getParentId());

        this.name = menu.getName();
        this.url = menu.getUrl();
        this.type = menu.getType();
        this.permission = menu.getPermission();
        this.icon = menu.getIcon();
        this.enable = menu.getEnable();
        this.sort = menu.getSort();
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public int compareTo(MenuTree menuTree) {
        return Comparator.comparing(MenuTree::getSort,
            Comparator.nullsLast(Integer::compareTo))
            .thenComparingLong(MenuTree::getId)
            .compare(this, menuTree);
    }
}
