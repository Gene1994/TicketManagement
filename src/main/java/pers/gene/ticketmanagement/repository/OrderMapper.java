package pers.gene.ticketmanagement.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO orderform (orderid,customerid,username,ticketid,trainnumber,checkin,checkout,starttime,endtime,seattype,seatnumber,price) VALUES(#{orderId}, #{customerId}, #{userName}, #{ticketId}, #{trainNumber}, #{checkin}, #{checkout}, #{startTime}, #{endTime}, #{seatType}, #{seatNumber}, #{price})")
    void insert(@Param("orderId") String orderId, @Param("customerId") String customerId, @Param("userName") String userName, @Param("ticketId") String ticketId, @Param("trainNumber") String trainNumber, @Param("checkin") String checkin, @Param("checkout") String checkout, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("seatType") String seatType, @Param("seatNumber") String seatNumber, @Param("price")double price);
}
