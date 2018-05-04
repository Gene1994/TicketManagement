package pers.gene.ticketmanagement.repository;

import org.apache.ibatis.annotations.*;
import pers.gene.ticketmanagement.domain.Customer;
import pers.gene.ticketmanagement.domain.Ticket;

import java.util.Date;
import java.util.List;
@Mapper
public interface TicketMapper {
    @Select("SELECT * FROM TICKET")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "trainNumber", column = "trainnumber"),
            @Result(property = "checkin", column = "checkin"),
            @Result(property = "checkout", column = "checkout"),
            @Result(property = "startTime", column = "starttime"),
            @Result(property = "endTime", column = "endtime"),
            @Result(property = "customerId", column = "customerid")
    })
    List<Ticket> findAll();

    @Select("SELECT * FROM TICKET WHERE id = #{id}")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "trainNumber", column = "trainnumber"),
            @Result(property = "checkin", column = "checkin"),
            @Result(property = "checkout", column = "checkout"),
            @Result(property = "startTime", column = "starttime"),
            @Result(property = "endTime", column = "endtime"),
            @Result(property = "customerId", column = "customerid")
    })
    Ticket findById(@Param("id") String id);

    @Select("SELECT * FROM TICKET  where checkin = #{checkin}  and checkout = #{checkout} and (starttime = #{startTime} or #{startTime} is null)")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "trainNumber", column = "trainnumber"),
            @Result(property = "checkin", column = "checkin"),
            @Result(property = "checkout", column = "checkout"),
            @Result(property = "startTime", column = "starttime"),
            @Result(property = "endTime", column = "endtime"),
            @Result(property = "customerId", column = "customerid")
    })
    List<Ticket> findByChecckinCheckout(@Param("checkin") String checkin, @Param("checkout") String checkout, @Param("startTime") Date startTime);

    @Insert("INSERT INTO ticket (id,trainnumber,checkin,checkout,starttime,endtime,customerid) VALUES(#{id}, #{trainNumber}, #{checkin}, #{checkout}, #{startTime}, #{endTime}, #{customerId})")
    void insert(@Param("id") String id, @Param("trainNumber") String trainNumber, @Param("checkin") String checkin, @Param("checkout") String checkout, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("customerId") String customerId);

    @Update("UPDATE ticket SET trainNumber=#{trainNumber},checkin=#{checkin},checkout=#{checkout},starttime=#{startTime},endtime=#{endTime}, customerid = #{customerId} WHERE id =#{id}")
    void updateById(@Param("id") String id, @Param("trainNumber") String trainNumber, @Param("checkin") String checkin, @Param("checkout") String checkout, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("customerId") String customerId);

    @Delete("DELETE FROM ticket WHERE id=#{id}")
    void delete(@Param("id") String id);
}
