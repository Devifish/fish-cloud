package cn.devifish.cloud.common.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MyBatisJsonTypeHandler
 *
 * @author Devifish
 * @date 2020/7/3 18:27
 */
public class MyBatisJsonTypeHandler<T extends Serializable> extends BaseTypeHandler<T> {


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
