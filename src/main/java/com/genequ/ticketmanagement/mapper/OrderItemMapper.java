package com.genequ.ticketmanagement.mapper;

import com.genequ.ticketmanagement.pojo.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderItemMapper {

    String ORDERITEM_COLUMN_LIST = " id, order_no, ticket_id, train_number, check_in, check_out, start_time, end_time, seat_type, seat_number, current_unit_price, quantity, total_price, create_time, update_time, user_id ";

    int deleteByPrimaryKey(Integer id);

    @Insert("insert into order_item (" + ORDERITEM_COLUMN_LIST + ") values (#{id,jdbcType=INTEGER}, #{orderNo,jdbcType=BIGINT},{ticketId,jdbcType=INTEGER}, #{trainNumber}, #{checkIn}, #{checkOut}, #{startTime}, #{endTime}, #{seatType}, #{seatNumber}, #{currentUnitPrice,jdbcType=DECIMAL}, #{quantity,jdbcType=INTEGER}, #{totalPrice,jdbcType=DECIMAL}, now(), now(), #{userId,jdbcType=INTEGER})")
    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    @Select("SELECT" + ORDERITEM_COLUMN_LIST + "from order_item where order_no = #{orderNo} and user_id = #{userId}")
    List<OrderItem> getByOrderNoUserId(@Param("orderNo")Long orderNo, @Param("userId")Integer userId);

    @Select("SELECT" + ORDERITEM_COLUMN_LIST + " from order_item where order_no = #{orderNo}")
    List<OrderItem> getByOrderNo(@Param("orderNo")Long orderNo);



    void batchInsert(List<OrderItem> orderItemList);


}
