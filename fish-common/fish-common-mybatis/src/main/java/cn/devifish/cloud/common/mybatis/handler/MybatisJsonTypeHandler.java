package cn.devifish.cloud.common.mybatis.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.util.Assert;

/**
 * MybatisJsonTypeHandler
 * Mybatis Json类型数据处理
 * 使用阿里巴巴FastJson实现
 *
 * @author Devifish
 * @date 2020/7/3 18:27
 */
@MappedTypes(Object.class)
@MappedJdbcTypes({JdbcType.VARCHAR, JdbcType.LONGVARCHAR})
public class MybatisJsonTypeHandler extends BaseJsonTypeHandler<Object> {

    private final Class<?> type;

    public MybatisJsonTypeHandler(Class<?> type) {
        Assert.notNull(type, "Type argument cannot be null");
        this.type = type;
    }

    @Override
    protected Object parse(String json) {
        return JSON.parseObject(json, type);
    }

    @Override
    protected String toJson(Object obj) {
        return JSON.toJSONString(obj,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty);
    }
}
