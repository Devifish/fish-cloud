package cn.devifish.cloud.upms.common.vo;

import cn.devifish.cloud.common.core.TreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * DeptTree
 * 部门树
 *
 * @author Devifish
 * @date 2020/8/1 15:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptTree extends TreeNode<List<DeptTree>> {

    /** 部门名称 **/
    private String name;

    /** 层级结构 **/
    private String level;

    /** 描述 **/
    private String description;

}
