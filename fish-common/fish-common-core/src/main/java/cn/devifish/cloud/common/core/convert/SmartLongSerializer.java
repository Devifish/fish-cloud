package cn.devifish.cloud.common.core.convert;

/**
 * SmartLongSerializer
 *
 * @author Devifish
 * @date 2020/7/24 14:45
 */

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * SmartLongSerializer
 * 智能序列化Long类型数据
 * 当数据超过Javascript Number类型最大值时转换为String
 *
 * @author Devifish
 * @date 2019/8/9 15:35
 */
public class SmartLongSerializer extends StdSerializer<Long> {

    private final static long JAVASCRIPT_NUMBER_BITS = 53L;
    private final static long JAVASCRIPT_NUMBER_MAX_SIZE = ~(-1L << JAVASCRIPT_NUMBER_BITS);

    public SmartLongSerializer() {
        super(Long.class);
    }

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }

        // 当数据超过Javascript Number类型最大值时转换为String
        var temp = (long) value;
        if (temp >= JAVASCRIPT_NUMBER_MAX_SIZE) {
            gen.writeString(value.toString());
        } else {
            gen.writeNumber(temp);
        }
    }
}
