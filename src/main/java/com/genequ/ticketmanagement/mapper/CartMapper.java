package com.genequ.ticketmanagement.mapper;

import com.genequ.ticketmanagement.pojo.Cart;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CartMapper {
    String CART_COLUMN_LIST = " id, user_id, ticket_id, quantity, checked, create_time, update_time ";
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectCartByUserIdProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    List<Cart> selectCartByUserId(Integer userId);

    int selectCartProductCheckedStatusByUserId(Integer userId);

    int deleteByUserIdProductIds(@Param("userId") Integer userId, @Param("productIdList") List<String> productIdList);


    int checkedOrUncheckedProduct(@Param("userId") Integer userId, @Param("productId") Integer productId, @Param("checked") Integer checked);

    int selectCartProductCount(@Param("userId") Integer userId);


    @Select("SELECT" + CART_COLUMN_LIST + "from cart where user_id = #{userId} and checked = 1")
    @Results(id="cartMap", value={
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "ticketId", column = "ticket_id"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "checked", column = "checked"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time")
    })
    List<Cart> selectCheckedCartByUserId(Integer userId);


}