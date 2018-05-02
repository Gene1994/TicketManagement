package pers.gene.ticketmanagement.repository;
import org.apache.ibatis.annotations.*;
import pers.gene.ticketmanagement.domain.Customer;

import java.util.Date;
import java.util.List;


@Mapper
public interface CustomerMapper {

    @Select("SELECT * FROM CUSTOMER")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "cellphone", column = "cellphone"),
            @Result(property = "from", column = "from"),
            @Result(property = "to", column = "to"),
            @Result(property = "startTime", column = "starttime"),
            @Result(property = "endTime", column = "endtime")
    })
    List<Customer> findAll();

    @Select("SELECT * FROM customer WHERE id = #{id}")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "cellphone", column = "cellphone"),
            @Result(property = "from", column = "from"),
            @Result(property = "to", column = "to"),
            @Result(property = "startTime", column = "starttime"),
            @Result(property = "endTime", column = "endtime")
    })
    Customer findById(@Param("id") String id);

    //@Insert 插入数据库使用，直接传入实体类会自动解析属性到对应的值
    @Insert("INSERT INTO customer(id,username,password,email,cellphone,from,to,starttime,endtime) VALUES(#{id}, #{userName}, #{passWord}, #{email}, #{cellphone}, #{from}, #{to}, #{startTime}, #{endTime})")
    void insert(@Param("id") String id, @Param("username") String username, @Param("password") String password, @Param("email") String email, @Param("cellphone") String cellphone, @Param("from") String from, @Param("to") String to, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Update("UPDATE customer SET username=#{username},password=#{password},email=#{email},cellphone=#{cellphone},from=#{from},to=#{to},starttime=#{starttime},endtime=#{endtime} WHERE id =#{id}")
    void update(@Param("id") String id, @Param("username") String username, @Param("password") String password, @Param("email") String email, @Param("cellphone") String cellphone, @Param("from") String from, @Param("to") String to, @Param("startTime") Date startTime, @Param("endTime") Date endTime);;

    @Delete("DELETE FROM customer WHERE id=#{id}")
    void delete(@Param("id") String id);
}

