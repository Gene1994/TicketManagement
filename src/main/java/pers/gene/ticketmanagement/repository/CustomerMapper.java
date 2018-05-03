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
            @Result(property = "userName", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "cellphone", column = "cellphone"),
            @Result(property = "checkin", column = "checkin"),
            @Result(property = "checkout", column = "checkout"),
            @Result(property = "startTime", column = "starttime"),
            @Result(property = "endTime", column = "endtime")
    })
    List<Customer> findAll();

    @Select("SELECT * FROM customer WHERE id = #{id}")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "userName", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "cellphone", column = "cellphone"),
            @Result(property = "checkin", column = "checkin"),
            @Result(property = "checkout", column = "checkout"),
            @Result(property = "startTime", column = "starttime"),
            @Result(property = "endTime", column = "endtime")
    })
    Customer findById(@Param("id") String id);

    @Select("SELECT * FROM customer WHERE email = #{email}")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "userName", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "cellphone", column = "cellphone"),
            @Result(property = "checkin", column = "checkin"),
            @Result(property = "checkout", column = "checkout"),
            @Result(property = "startTime", column = "starttime"),
            @Result(property = "endTime", column = "endtime")
    })
    Customer findByEmail(@Param("email") String email);

    @Select("SELECT * FROM customer WHERE username = #{userName}")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "userName", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "cellphone", column = "cellphone"),
            @Result(property = "checkin", column = "checkin"),
            @Result(property = "checkout", column = "checkout"),
            @Result(property = "startTime", column = "starttime"),
            @Result(property = "endTime", column = "endtime")
    })
    Customer findByUserName(@Param("userName") String userName);

    //@Insert 插入数据库使用，直接传入实体类会自动解析属性到对应的值
    @Insert("INSERT INTO customer(id,username,password,email,cellphone,checkin,checkout,starttime,endtime) VALUES(#{id}, #{userName}, #{password}, #{email}, #{cellphone}, #{checkin}, #{checkout}, #{startTime}, #{endTime})")
    void insert(@Param("id") String id, @Param("userName") String userName, @Param("password") String password, @Param("email") String email, @Param("cellphone") String cellphone, @Param("checkin") String checkin, @Param("checkout") String checkout, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Update("UPDATE customer SET username=#{userName},password=#{password},email=#{email},cellphone=#{cellphone},checkin=#{checkin},checkout=#{checkout},starttime=#{startTime},endtime=#{endTime} WHERE id =#{id}")
    void update(@Param("id") String id, @Param("userName") String userName, @Param("password") String password, @Param("email") String email, @Param("cellphone") String cellphone, @Param("checkin") String checkin, @Param("checkout") String checkout, @Param("startTime") Date startTime, @Param("endTime") Date endTime);;

    @Delete("DELETE FROM customer WHERE id=#{id}")
    void delete(@Param("id") String id);
}

