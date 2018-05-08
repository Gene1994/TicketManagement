package pers.gene.ticketmanagement.repository;

import org.apache.ibatis.annotations.*;
import pers.gene.ticketmanagement.domain.Customer;
import pers.gene.ticketmanagement.domain.Ticket;
import pers.gene.ticketmanagement.service.TicketService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Mapper
public interface TicketMapper {
    @Select("SELECT * FROM TICKET")
    @Results({
            @Result(property = "trainNumber", column = "trainnumber"),
            @Result(property = "checkin", column = "checkin"),
            @Result(property = "checkout", column = "checkout"),
            @Result(property = "startTime", column = "starttime"),
            @Result(property = "endTime", column = "endtime"),
            @Result(property = "seatType", column = "seattype"),
            @Result(property = "seatNumber", column = "seatnumber"),
            @Result(property = "price", column = "price")
    })
    List<Ticket> findAll();

    @Select("SELECT * FROM TICKET WHERE id = #{id}")
    @Results({
            @Result(property = "trainNumber", column = "trainnumber"),
            @Result(property = "checkin", column = "checkin"),
            @Result(property = "checkout", column = "checkout"),
            @Result(property = "startTime", column = "starttime"),
            @Result(property = "endTime", column = "endtime"),
            @Result(property = "seatType", column = "seattype"),
            @Result(property = "seatNumber", column = "seatnumber"),
            @Result(property = "price", column = "price")
    })
    Ticket findById(@Param("id") String id);

//    @Select("SELECT * FROM TICKET  where checkin = #{checkin}  and checkout = #{checkout} and starttime >= #{startTime} and startTime < #{theNextDay}")
//    @Results({
//            @Result(property = "trainNumber", column = "trainnumber"),
//            @Result(property = "checkin", column = "checkin"),
//            @Result(property = "checkout", column = "checkout"),
//            @Result(property = "startTime", column = "starttime"),
//            @Result(property = "endTime", column = "endtime"),
//            @Result(property = "seatType", column = "seattype"),
//            @Result(property = "seatNumber", column = "seatnumber"),
//            @Result(property = "price", column = "price")
//    })
//    List<Ticket> findByCheckinCheckout(@Param("checkin") String checkin, @Param("checkout") String checkout, @Param("startTime") Date startTime, @Param("theNextDay")Date theNextDay);

    @Insert("INSERT INTO ticket (trainnumber,checkin,checkout,starttime,endtime,seattype,seatnumber,price) VALUES(#{trainNumber}, #{checkin}, #{checkout}, #{startTime}, #{endTime}, #{seatType}, #{seatNumber}, #{price})")
    void insert(@Param("trainNumber") String trainNumber, @Param("checkin") String checkin, @Param("checkout") String checkout, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("seatType") String seatType, @Param("seatNumber") String seatNumber, double price);

//    @Update("UPDATE ticket SET trainNumber=#{trainNumber},checkin=#{checkin},checkout=#{checkout},starttime=#{startTime},endtime=#{endTime}, customerid = #{customerId} WHERE id =#{id}")
//    void updateById(@Param("id") String id, @Param("trainNumber") String trainNumber, @Param("checkin") String checkin, @Param("checkout") String checkout, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("customerId") String customerId);

//    @Delete("DELETE FROM ticket WHERE id=#{id}")
//    void delete(@Param("id") String id);

//    @Select("SELECT count(*) FROM TICKET  where checkin = #{checkin}  and checkout = #{checkout} and starttime >= #{startTime} and startTime < #{theNextDay}")
//    Integer countByChecckinCheckout(@Param("checkin") String checkin, @Param("checkout") String checkout, @Param("startTime") Date startTime, @Param("theNextDay")Date theNextDay);

    //分组查询
    @Select("SELECT trainnumber, checkin, checkout, starttime, endtime, seattype, seatnumber, price, count(trainnumber) FROM TICKET group by trainnumber having checkin = #{checkin}  and checkout = #{checkout} and starttime >= #{startTime} and startTime < #{theNextDay}")
    @Results({
            @Result(property = "trainNumber", column = "trainnumber"),
            @Result(property = "checkin", column = "checkin"),
            @Result(property = "checkout", column = "checkout"),
            @Result(property = "startTime", column = "starttime"),
            @Result(property = "endTime", column = "endtime"),
            @Result(property = "seatType", column = "seattype"),
            @Result(property = "seatNumber", column = "seatnumber"),
            @Result(property = "price", column = "price"),
            @Result(property = "amount", column = "count(trainnumber)")
    })
    List<Ticket> findByCheckinCheckout(@Param("checkin") String checkin, @Param("checkout") String checkout, @Param("startTime") Date startTime, @Param("theNextDay")Date theNextDay);

}
