package com.genequ.ticketmanagement.mapper;

import com.genequ.ticketmanagement.domain.Ticket;
import com.genequ.ticketmanagement.util.myBooleanTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
public interface TicketMapper {
    @Select("SELECT * FROM TICKET")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "trainNumber", column = "trainnumber"),
            @Result(property = "checkin", column = "checkin"),
            @Result(property = "checkout", column = "checkout"),
            @Result(property = "startTime", column = "starttime"),
            @Result(property = "endTime", column = "endtime"),
            @Result(property = "seatType", column = "seattype"),
            @Result(property = "seatNumber", column = "seatnumber"),
            @Result(property = "price", column = "price"),
            @Result(property = "isOrdered", column = "isordered", typeHandler= myBooleanTypeHandler.class)
    })
    List<Ticket> findAll();

    @Select("SELECT * FROM TICKET WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "trainNumber", column = "trainnumber"),
            @Result(property = "checkin", column = "checkin"),
            @Result(property = "checkout", column = "checkout"),
            @Result(property = "startTime", column = "starttime"),
            @Result(property = "endTime", column = "endtime"),
            @Result(property = "seatType", column = "seattype"),
            @Result(property = "seatNumber", column = "seatnumber"),
            @Result(property = "price", column = "price"),
            @Result(property = "isOrdered", column = "isordered", typeHandler= myBooleanTypeHandler.class)
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
    void insert(@Param("trainNumber") String trainNumber, @Param("checkin") String checkin, @Param("checkout") String checkout, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("seatType") String seatType, @Param("seatNumber") String seatNumber, @Param("price")double price);

    @Update({"UPDATE ticket SET isordered = #{orderInfo} WHERE id =#{id}"})
    void setIsOrdered(@Param("id") String id, @Param("orderInfo") String orderInfo);

//    @Delete("DELETE FROM ticket WHERE id=#{id}")
//    void delete(@Param("id") String id);

//    @Select("SELECT count(*) FROM TICKET  where checkin = #{checkin}  and checkout = #{checkout} and starttime >= #{startTime} and startTime < #{theNextDay}")
//    Integer countByChecckinCheckout(@Param("checkin") String checkin, @Param("checkout") String checkout, @Param("startTime") Date startTime, @Param("theNextDay")Date theNextDay);


    /**
     * 根据出发地、目的地和出发时间查询可以预定的票并分组
     * @param checkin
     * @param checkout
     * @param startTime
     * @param theNextDay
     * @return
     */
    @Select("SELECT trainnumber, checkin, checkout, starttime, endtime, seattype, seatnumber, price, count(trainnumber) FROM TICKET where isordered = 'N' group by trainnumber having checkin = #{checkin} and checkout = #{checkout} and starttime >= #{startTime} and starttime < #{theNextDay}")
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


    /**
     * 根据列车号和出发时间查询，生成订单时用
     * @param trainNumber
     * @param startTime
     * @return
     */
    @Select("SELECT * FROM TICKET WHERE trainnumber = #{trainNumber} and starttime = #{startTime} and isordered = 'N'")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "trainNumber", column = "trainnumber"),
            @Result(property = "checkin", column = "checkin"),
            @Result(property = "checkout", column = "checkout"),
            @Result(property = "startTime", column = "starttime"),
            @Result(property = "endTime", column = "endtime"),
            @Result(property = "seatType", column = "seattype"),
            @Result(property = "seatNumber", column = "seatnumber"),
            @Result(property = "price", column = "price"),
            @Result(property = "isOrdered", column = "isordered", typeHandler= myBooleanTypeHandler.class)
    })
    List<Ticket> findByTrainNumberStartTime(@Param("trainNumber") String trainNumber, @Param("startTime") Date startTime);

}
