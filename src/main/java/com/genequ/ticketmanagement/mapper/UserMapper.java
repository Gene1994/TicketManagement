package com.genequ.ticketmanagement.mapper;

import com.genequ.ticketmanagement.pojo.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

public interface UserMapper {
    String USER_COLUMN_LIST = " id, username, password, email, phone, question, answer, role, create_time, update_time ";
    @Select("SELECT" + USER_COLUMN_LIST + "FROM user")
    @Results(id="userMap", value={
            @Result(property = "id", column = "id", jdbcType= JdbcType.INTEGER, javaType = Integer.class),
            @Result(property = "username", column = "username", jdbcType=JdbcType.VARCHAR, javaType = String.class),
            @Result(property = "password", column = "password", jdbcType=JdbcType.VARCHAR, javaType = String.class),
            @Result(property = "email", column = "email", jdbcType=JdbcType.VARCHAR, javaType = String.class),
            @Result(property = "phone", column = "phone", jdbcType=JdbcType.VARCHAR, javaType = String.class),
            @Result(property = "question", column = "question", jdbcType=JdbcType.VARCHAR, javaType = String.class),
            @Result(property = "answer", column = "answer", jdbcType=JdbcType.VARCHAR, javaType = String.class),
            @Result(property = "role", column = "role", jdbcType=JdbcType.INTEGER, javaType = Integer.class),
            @Result(property = "createTime", column = "create_time", jdbcType=JdbcType.TIMESTAMP, javaType = Date.class),
            @Result(property = "updateTime", column = "update_time", jdbcType=JdbcType.TIMESTAMP, javaType = Date.class)
    })
    List<User> findAll();

    @Select("SELECT" + USER_COLUMN_LIST + "FROM user WHERE id = #{id}")
    @ResultMap(value = "userMap")
    User selectByPrimaryKey(@Param("id") Integer id);

    @Select("SELECT" + USER_COLUMN_LIST + "FROM user WHERE email = #{email}")
    @ResultMap(value = "userMap")
    User findByEmail(@Param("email") String email);

    @Select("SELECT" + USER_COLUMN_LIST + "FROM user WHERE username = #{username} and password = #{password}")
    @ResultMap(value = "userMap")
    User selectLogin(@Param("username") String username, @Param("password") String password);

    //@Insert 插入数据库使用，直接传入实体类会自动解析属性到对应的值
    @Insert("insert into user (id, username, password, email, phone, question, answer, role, create_time, update_time)" +
            "    values (#{id,jdbcType=INTEGER}, #{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, " +
            "#{phone,jdbcType=VARCHAR}, #{question,jdbcType=VARCHAR}, #{answer,jdbcType=VARCHAR}, #{role,jdbcType=INTEGER}, now(),now())")
    int insert(User user);

    @Update("UPDATE user SET username=#{userName},password=#{password},email=#{email},cellphone=#{cellphone} WHERE id =#{id}")
    void updateById(@Param("id") String id, @Param("userName") String userName, @Param("password") String password, @Param("email") String email, @Param("cellphone") String cellphone);

    @Delete("DELETE FROM user WHERE id=#{id}")
    void deleteById(@Param("id") String id);

    //根据用户ID插入用户头像URL
    @Update("UPDATE user SET avatar_url = (#{avatarUrl}) WHERE id = #{id}")
    void setAvatarUrl(@Param("id") Integer id, @Param("avatarUrl") String avatarUrl);

    @Select("select count(1) from user where username = #{username}")
    int checkUsername(@Param("username")String username);

    @Select("select count(1) from user where email = #{email}")
    int checkEmail(@Param("username")String email);

    @Select("select count(1) from user where email = #{email} and id != #{userId}")
    int checkEmailByUserId(@Param("email")String email, @Param("userId")int userId);


    @Update("update user set username=#{username},password=#{password},email=#{email},phone=#{phone},question={question},answer=#{answer},role=#{role},create_time=#{createTime},update_time=now()) WHERE id =#{id}")
    int updateByPrimaryKeySelective(User user);

    @Select("select question from user where username = #{username}")
    String selectQuestionByUsername(@Param("username") String username);

    @Select("SELECT count(1) from user where username=#{username} and question = #{question} and answer = #{answer}")
    int checkAnswer(@Param("username")String username, @Param("question")String question, @Param("answer")String answer);

    @Update("update user SET password = #{passwordNew},update_time = now() where username = #{username}")
    int updatePasswordByUsername(@Param("username")String username, @Param("passwordNew")String passwordNew);

    @Select("SELECT count(1) from user where password = #{password} and id = #{userId}")
    int checkPassword(@Param("password")String password, @Param("userId")int userId);
}

