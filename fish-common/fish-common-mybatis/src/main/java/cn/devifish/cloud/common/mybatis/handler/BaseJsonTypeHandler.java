package cn.devifish.cloud.common.mybatis.handler;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;

/**
 * BaseJsonTypeHandler
 * JSON转换处理器基础实现
 *
 * @author Devifish
 * @date 2020/7/27 10:51
 */
public abstract class BaseJsonTypeHandler<T> extends BaseTypeHandler<T> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            ps.setNull(i, Types.VARCHAR);
        } else {
            var json = toJson(parameter);
            ps.setString(i, json);
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        var json = rs.getString(columnName);
        return StringUtils.isBlank(json)
                ? null
                : parse(json);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        var json = rs.getString(columnIndex);
        return StringUtils.isBlank(json)
                ? null
                : parse(json);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        var json = cs.getString(columnIndex);
        return StringUtils.isBlank(json)
                ? null
                : parse(json);
    }

    protected abstract T parse(String json);

    protected abstract String toJson(T obj);

}
