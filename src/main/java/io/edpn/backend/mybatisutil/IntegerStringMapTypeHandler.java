package io.edpn.backend.mybatisutil;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.HStoreConverter;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@MappedTypes(Map.class)
@MappedJdbcTypes(JdbcType.OTHER)
public class IntegerStringMapTypeHandler extends BaseTypeHandler<Map<Integer, String>> {
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<Integer, String> parameter, JdbcType jdbcType) throws SQLException {
        PGobject pGobject = new PGobject();
        pGobject.setType("hstore");
        pGobject.setValue(HStoreConverter.toString(parameter));
        ps.setObject(i, pGobject);
    }
    
    @Override
    public Map<Integer, String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return readMap(rs.getString(columnName));
    }
    
    @Override
    public Map<Integer, String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return readMap(rs.getString(columnIndex));
    }
    
    @Override
    public Map<Integer, String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return readMap(cs.getString(columnIndex));
    }
    
    private Map<Integer, String> readMap(String hstring) throws SQLException {
        if (hstring != null) {
            Map<Integer, String> map = new LinkedHashMap<>();
            Map<String, String> rawMap = HStoreConverter.fromString(hstring);
            for (Map.Entry<String, String> entry : rawMap.entrySet()) {
                map.put(Integer.parseInt(entry.getKey()), entry.getValue()); // convert from <String, String> to <String,Double>
            }
            
            return map;
        }
        return null;
    }
}

