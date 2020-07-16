package cn.devifish.cloud.common.core.generator;

import java.util.Arrays;

/**
 * IdGenerator
 *
 * @author Devifish
 * @date 2020/7/1 18:02
 */
public interface IdGenerator {

    /**
     * 批量获取下一组ID
     *
     * @param count 批量条数
     * @return long[]
     */
    long[] nextIds(int count);

    /**
     * 获得下一个ID
     *
     * @return long
     */
    long nextId();

    /**
     * 批量获取下一组String类型ID
     *
     * @param count 批量条数
     * @return String[]
     */
    default String[] nextStringIds(int count) {
        return Arrays.stream(nextIds(count))
                .mapToObj(String::valueOf)
                .toArray(String[]::new);
    }

    /**
     * 获得下一个String类型ID
     *
     * @return String
     */
    default String nextStringId() {
        return String.valueOf(nextId());
    }

}
