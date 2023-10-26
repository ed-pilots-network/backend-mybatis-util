module edpn.backend.mybatisutil {
    requires java.sql;
    requires org.mybatis.spring;
    requires org.mybatis;
    requires org.postgresql.jdbc;
    
    exports io.edpn.backend.mybatisutil;
}
