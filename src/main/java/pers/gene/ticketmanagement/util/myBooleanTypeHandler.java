package pers.gene.ticketmanagement.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class myBooleanTypeHandler implements TypeHandler<Boolean> {

    /**
     * 用于定义在Mybatis设置参数时该如何把Java类型的参数转换为对应的数据库类型
     * @param preparedStatement 当前的PreparedStatement对象
     * @param i 当前参数的位置
     * @param b 当前参数的Java对象
     * @param jdbcType 当前参数的数据库类型
     * @throws SQLException
     */
    public void setParameter(PreparedStatement preparedStatement, int i, Boolean b, JdbcType jdbcType) throws SQLException {
        if(b.equals(Boolean.TRUE)){
            preparedStatement.setString(i,"Y");
        }else{
            preparedStatement.setString(i,"N");
        }
    }

    /**
     * 用于在Mybatis获取数据结果集时如何把数据库类型转换为对应的Java类型
     * @param resultSet 当前的结果集
     * @param columnName 当前的字段名称
     * @return 转换后的Java对象
     * @throws SQLException
     */
    public Boolean getResult(ResultSet resultSet, String columnName) throws SQLException {
        return tranferType(resultSet.getString(columnName)) ;
    }

    /**
     * 用于在Mybatis通过字段位置获取字段数据时把数据库类型转换为对应的Java类型
     * @param resultSet 当前的结果集
     * @param i 当前字段的位置
     * @return 转换后的Java对象
     * @throws SQLException
     */
    public Boolean getResult(ResultSet resultSet, int i) throws SQLException {
        return tranferType(  resultSet.getString(i) );
    }

    /**
     * 用于Mybatis在调用存储过程后把数据库类型的数据转换为对应的Java类型
     * @param callableStatement 当前的CallableStatement执行后的CallableStatement
     * @param columnIndex 当前输出参数的位置
     * @return
     * @throws SQLException
     */
    public Boolean getResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return tranferType(callableStatement.getString(columnIndex));
    }

    private Boolean tranferType(String s){
        if("Y".equalsIgnoreCase(s))
        {
            return Boolean.TRUE ;
        }else{
            return Boolean.FALSE;
        }
    }
}