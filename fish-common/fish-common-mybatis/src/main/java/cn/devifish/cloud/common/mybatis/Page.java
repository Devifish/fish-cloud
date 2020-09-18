package cn.devifish.cloud.common.mybatis;

import cn.devifish.cloud.common.core.PageParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Page
 *
 * @author Devifish
 * @date 2020/9/18 10:57
 */
public class Page<T> implements IPage<T> {

    private List<T> records = Collections.emptyList();

    private long total = 0;

    private long size = 10;

    private long current = 1;

    public Page() {}

    public Page(List<T> records, long total, long size, long current) {
        this.records = records;
        this.total = total;
        this.size = size;
        this.current = current;
    }

    public static Page<?> of(PageParam param) {
       var page = new Page<>();
       page.setCurrent(param.getCurrent());
       page.setSize(param.getSize());
       return page;
    }

    @SuppressWarnings("unchecked")
    public <E> Page<E> map(Function<T, E> function) {
        if (CollectionUtils.isEmpty(records)) return (Page<E>) this;

        var newRecords = records.stream()
            .map(function)
            .collect(Collectors.toList());

        return new Page<>(newRecords,
            this.total, this.size, this.current);
    }

    @Override
    public List<OrderItem> orders() {
        return Collections.emptyList();
    }

    @Override
    public List<T> getRecords() {
        return records;
    }

    @Override
    public IPage<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public IPage<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public IPage<T> setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return current;
    }

    @Override
    public IPage<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

}
