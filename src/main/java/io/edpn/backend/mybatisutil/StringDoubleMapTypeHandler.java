package io.edpn.backend.mybatisutil;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.HStoreConverter;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@MappedTypes(Map.class)
public class StringDoubleMapTypeHandler extends BaseTypeHandler<Map<String, Double>> {
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<String, Double> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, HStoreConverter.toString(parameter));
    }
    
    @Override
    public Map<String, Double> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return readMap(rs.getString(columnName));
    }
    
    @Override
    public Map<String, Double> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return readMap(rs.getString(columnIndex));
    }
    
    @Override
    public Map<String, Double> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return readMap(cs.getString(columnIndex));
    }
    
    private Map<String, Double> readMap(String hstring) throws SQLException {
        if (hstring != null) {
            Map<String, Double> map = new LinkedHashMap<>();
            Map<String, String> rawMap = HStoreConverter.fromString(hstring);
            for (Map.Entry<String, String> entry : rawMap.entrySet()) {
                map.put(entry.getKey(), Double.parseDouble(entry.getValue())); // convert from <String, String> to <String,Double>
            }
            
            return map;
        }
        return null;
    }
}

