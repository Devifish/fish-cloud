package cn.devifish.cloud.common.core.generator;

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

}
