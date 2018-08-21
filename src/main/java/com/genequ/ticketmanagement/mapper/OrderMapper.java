package com.genequ.ticketmanagement.mapper;

import com.genequ.ticketmanagement.pojo.Order;
import com.genequ.ticketmanagement.util.TutorDynaSqlProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

public interface OrderMapper {
    String ORDER_COLUMN_LIST = " id, order_no, user_id, shipping_id, payment, payment_type, postage, status, payment_time, send_time, end_time, close_time, create_time, update_time ";

    int deleteByPrimaryKey(Integer id);

    @Insert("insert into order (id, order_no, user_id, shipping_id, payment, payment_type, postage, status, payment_time, send_time, end_time, close_time, create_time, update_time) " +
            "values (#{id,jdbcType=INTEGER}, #{orderNo,jdbcType=BIGINT}, #{userId,jdbcType=INTEGER}, #{shippingId,jdbcType=INTEGER}, #{payment,jdbcType=DECIMAL}, #{paymentType,jdbcType=INTEGER}, " +
            " #{postage,jdbcType=INTEGER}, #{status,jdbcType=INTEGER}, #{paymentTime,jdbcType=TIMESTAMP},  #{sendTime,jdbcType=TIMESTAMP}, #{endTime,jdbcType=TIMESTAMP}, #{closeTime,jdbcType=TIMESTAMP}, now(), now())")
    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    @UpdateProvider(type = TutorDynaSqlProvider.class, method = "updateByPrimaryKeySelectiveSQL")
    int updateByPrimaryKeySelective(Order order);

    int updateByPrimaryKey(Order record);


    @Select("SELECT" + ORDER_COLUMN_LIST + "from mmall_order where order_no = #{orderNo} and user_id = #{userId}")
    Order selectByUserIdAndOrderNo(@Param("userId")Integer userId,@Param("orderNo")Long orderNo);


    Order selectByOrderNo(Long orderNo);



    @Select("SELECT" + ORDER_COLUMN_LIST + "from order where user_id = #{userId} order by create_time desc")
    List<Order> selectByUserId(Integer userId);


    List<Order> selectAllOrder();
}
