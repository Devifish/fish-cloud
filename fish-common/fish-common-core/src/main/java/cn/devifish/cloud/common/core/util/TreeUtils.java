package cn.devifish.cloud.common.core.util;

import cn.devifish.cloud.common.core.TreeNode;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * TreeUtils
 * 树状数据工具类
 * 方便对树状数据进行处理
 *
 * @author Devifish
 * @date 2020/9/23 17:17
 */
public class TreeUtils {

    /**
     * 列表数据转换树形数据
     * 实体数据需要继承TreeNode
     *
     * @param list 列表数据
     * @param rootId 根节点ID
     * @param createSupplier 构建器
     * @return 树形数据集合
     */
    public static <T extends TreeNode<E>, E extends Collection<T>> E toTree(Collection<T> list, Long rootId, Supplier<? extends E> createSupplier) {
        return toTree(list, rootId, createSupplier, null);
    }

    /**
     * 列表数据转换树形数据
     * 实体数据需要继承TreeNode
     *
     * @param list 列表数据
     * @param rootId 根节点ID
     * @param createSupplier 构建器
     * @param filterFunction 过滤器
     * @return 树形数据集合
     */
    public static <T extends TreeNode<E>, E extends Collection<T>> E toTree(Collection<T> list, Long rootId, Supplier<? extends E> createSupplier, Function<T, Boolean> filterFunction) {
        var iterator = list.iterator();

        E tree = null;
        while (iterator.hasNext()) {
            var item = iterator.next();
            if (!Objects.equals(item.getParentId(), rootId)) continue;
            if (filterFunction != null && Objects.equals(Boolean.FALSE, filterFunction.apply(item))) continue;
            if (tree == null) tree = createSupplier.get();

            // 搜索是否存在子节点
            iterator.remove();
            var childrenMenu = toTree(list, item.getId(), createSupplier, filterFunction);
            if (!CollectionUtils.isEmpty(childrenMenu)) {
                item.setChildren(childrenMenu);

                // 当存在子节点时则发生变化，需重置迭代器
                iterator = list.iterator();
            }
            tree.add(item);
        }
        return tree;
    }

}
