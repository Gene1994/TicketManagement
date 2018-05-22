package pers.gene.ticketmanagement.repository;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;
import pers.gene.ticketmanagement.domain.Order;
import pers.gene.ticketmanagement.util.myBooleanTypeHandler;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO orderform (orderid,customerid,username,ticketid,trainnumber,checkin,checkout,starttime,endtime,seattype,seatnumber,price) VALUES(#{orderId}, #{customerId}, #{userName}, #{ticketId}, #{trainNumber}, #{checkin}, #{checkout}, #{startTime}, #{endTime}, #{seatType}, #{seatNumber}, #{price})")
    void insert(@Param("orderId") String orderId, @Param("customerId") String customerId, @Param("userName") String userName, @Param("ticketId") String ticketId, @Param("trainNumber") String trainNumber, @Param("checkin") String checkin, @Param("checkout") String checkout, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("seatType") String seatType, @Param("seatNumber") String seatNumber, @Param("price")double price);

    @Select("SELECT * FROM orderform WHERE customerid = #{customerId}")
    @Results({
            //设置属性的属性 Mybatis
            @Result(property = "orderId", column = "orderid"),
            @Result(property = "customer.id", column = "customerid"),
            @Result(property = "customer.userName", column = "username"),
            @Result(property = "ticket.id", column = "ticketid"),
            @Result(property = "ticket.trainNumber", column = "trainnumber"),
            @Result(property = "ticket.checkin", column = "checkin"),
            @Result(property = "ticket.checkout", column = "checkout"),
            @Result(property = "ticket.startTime", column = "starttime"),
            @Result(property = "ticket.endTime", column = "endtime"),
            @Result(property = "ticket.seatType", column = "seattype"),
            @Result(property = "ticket.seatNumber", column = "seatnumber"),
            @Result(property = "ticket.price", column = "price"),
    })
    List<Order> findOrderByCustomerId(@Param("customerId") String customerId);

    @Select("SELECT * FROM orderform WHERE orderid = #{orderId}")
    @Results({
            @Result(property = "orderId", column = "orderid"),
            @Result(property = "customer.id", column = "customerid"),
            @Result(property = "customer.userName", column = "username"),
            @Result(property = "ticket.id", column = "ticketid"),
            @Result(property = "ticket.trainNumber", column = "trainnumber"),
            @Result(property = "ticket.checkin", column = "checkin"),
            @Result(property = "ticket.checkout", column = "checkout"),
            @Result(property = "ticket.startTime", column = "starttime"),
            @Result(property = "ticket.endTime", column = "endtime"),
            @Result(property = "ticket.seatType", column = "seattype"),
            @Result(property = "ticket.seatNumber", column = "seatnumber"),
            @Result(property = "ticket.price", column = "price"),
    })
    Order findById(@Param("orderId") String orderId);

    @Delete("DELETE FROM orderform WHERE orderid=#{orderId}")
    void deleteById(@Param("orderId") String orderId);
}
