package com.lcl.dataxexample.util;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName: DbUtil
 * @Description: 数据库工具类
 * @Author: Chunliang.Li
 * @Date: 2020/7/2 15:34
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
public class DbUtil {
    /**
     * db
     */
    public enum DbType{

        MYSQL(1,"MYSQL"),
        ORACLE(2,"ORACLE");

        private int code;
        private String desc;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        DbType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
    /**
     * 检验查询sql
     * @param sql
     * @param dbTypeCode
     * @return
     */
    public static boolean validSql(String sql, int dbTypeCode) {
        try {
            String dbName = getDbNameByDbType(dbTypeCode);
            switch (dbName) {
                case JdbcConstants.MYSQL:
                    new MySqlStatementParser(sql).parseStatementList();
                    break;
                case JdbcConstants.ORACLE:
                    new OracleStatementParser(sql).parseStatementList();
                    break;
                default:
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取查询sql的查询字段
     *
     * @param sql
     * @param dbTypeCode
     * @return
     */
    public static List<String> getColumns(String sql, int dbTypeCode) {
        String dbName = getDbNameByDbType(dbTypeCode);
        SQLStatementParser sqlStatementParser = SQLParserUtils.createSQLStatementParser(sql, dbName);
        SQLStatement sqlStatement = sqlStatementParser.parseStatement();
        switch (dbName) {
            case JdbcConstants.MYSQL:
                return getColumns(sqlStatement, new MySqlSchemaStatVisitor());
            case JdbcConstants.ORACLE:
                return getColumns(sqlStatement, new OracleSchemaStatVisitor());
            default:
                return null;
        }
    }

    private static List<String> getColumns(SQLStatement sqlStatement, SchemaStatVisitor visitor) {
        List<String> list = new ArrayList<>();
        sqlStatement.accept(visitor);
        Collection<TableStat.Column> columns = visitor.getColumns();
        columns.forEach(col -> list.add(col.getName()));
        return list;
    }

    private static String getDbNameByDbType(int dbTypeCode) {
        switch (dbTypeCode) {
            case 1:
                return JdbcConstants.MYSQL;
            case 2:
                return JdbcConstants.ORACLE;
            default:
                return null;
        }
    }

}
