package com.genequ.ticketmanagement.mapper;

import com.genequ.ticketmanagement.pojo.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    List<OrderItem> getByOrderNoUserId(@Param("orderNo")Long orderNo, @Param("userId")Integer userId);

    List<OrderItem> getByOrderNo(@Param("orderNo")Long orderNo);



    @Insert("insert into mmall_order_item (id, order_no, ticket_id, train_number, check_in, check_out, start_time, end_time, seat_type, seat_number, current_unit_price, quantity, total_price, create_time, update_time, user_id)" +
            "values (#{id,jdbcType=INTEGER}, #{orderNo,jdbcType=BIGINT},{ticketId,jdbcType=INTEGER}, #{trainNumber}, #{checkIn}, #{checkOut}, #{startTime}, #{endTime}, #{seatType}, #{seatNumber}, #{currentUnitPrice,jdbcType=DECIMAL}, #{quantity,jdbcType=INTEGER}, #{totalPrice,jdbcType=DECIMAL}, now(), now(), #{userId,jdbcType=INTEGER})")
    void batchInsert(OrderItem orderItem);


}
