package pers.gene.ticketmanagement.repository;
import lombok.extern.slf4j.Slf4j;
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
    })
    List<Customer> findAll();

    @Select("SELECT * FROM customer WHERE id = #{id}")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "userName", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "cellphone", column = "cellphone"),
    })
    Customer findById(@Param("id") String id);

    @Select("SELECT * FROM customer WHERE email = #{email}")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "userName", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "cellphone", column = "cellphone"),
    })
    Customer findByEmail(@Param("email") String email);

    @Select("SELECT * FROM customer WHERE username = #{userName}")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "userName", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "cellphone", column = "cellphone"),
    })
    Customer findByUserName(@Param("userName") String userName);

    //@Insert 插入数据库使用，直接传入实体类会自动解析属性到对应的值
    @Insert("INSERT INTO customer(id,username,password,email,cellphone) VALUES(#{id}, #{userName}, #{password}, #{email}, #{cellphone})")
    void insert(@Param("id") String id, @Param("userName") String userName, @Param("password") String password, @Param("email") String email, @Param("cellphone") String cellphone);

    @Update("UPDATE customer SET username=#{userName},password=#{password},email=#{email},cellphone=#{cellphone} WHERE id =#{id}")
    void updateById(@Param("id") String id, @Param("userName") String userName, @Param("password") String password, @Param("email") String email, @Param("cellphone") String cellphone);;

    @Delete("DELETE FROM customer WHERE id=#{id}")
    void deleteById(@Param("id") String id);

    //根据用户ID插入用户头像URL
    @Update("UPDATE customer SET avatar_url = (#{avatarUrl}) WHERE id = #{id}")
    void setAvatarUrl(@Param("id") String id, @Param("avatarUrl") String avatarUrl);
}

