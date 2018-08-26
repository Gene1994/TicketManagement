package com.genequ.ticketmanagement.mapper;

import com.genequ.ticketmanagement.pojo.Product;
import com.genequ.ticketmanagement.pojo.Ticket;
import com.genequ.ticketmanagement.util.TutorDynaSqlProvider;
import com.genequ.ticketmanagement.util.myBooleanTypeHandler;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
public interface TicketMapper {

    String TICKET_COLUMN_LIST = " id, train_number, check_in, check_out, start_time, end_time, seat_type, seat_number, price, stock, status, create_time, update_time ";

    @Select("SELECT" + TICKET_COLUMN_LIST + "FROM TICKET WHERE id = #{id}")
    @Results(id = "ticketMap",value ={
            @Result(property = "id", column = "id", jdbcType = JdbcType.INTEGER, javaType = Integer.class),
            @Result(property = "trainNumber", column = "train_number", jdbcType = JdbcType.VARCHAR, javaType = String.class),
            @Result(property = "checkIn", column = "check_in", jdbcType = JdbcType.VARCHAR, javaType = String.class),
            @Result(property = "checkOut", column = "check_out", jdbcType = JdbcType.VARCHAR, javaType = String.class),
            @Result(property = "startTime", column = "start_time", jdbcType = JdbcType.TIMESTAMP, javaType=Date.class),
            @Result(property = "endTime", column = "end_time", jdbcType = JdbcType.TIMESTAMP, javaType=Date.class),
            @Result(property = "seatType", column = "seat_type", jdbcType = JdbcType.VARCHAR, javaType = String.class),
            @Result(property = "seatNumber", column = "seat_number", jdbcType = JdbcType.VARCHAR, javaType = String.class),
            @Result(property = "price", column = "price", jdbcType = JdbcType.DECIMAL, javaType = BigDecimal.class),
            @Result(property = "stock", column = "stock", jdbcType = JdbcType.INTEGER, javaType = Integer.class),
            @Result(property = "status", column = "status", jdbcType = JdbcType.INTEGER, javaType = Integer.class),
            @Result(property = "createTime", column = "create_time", jdbcType = JdbcType.TIMESTAMP, javaType=Date.class),
            @Result(property = "updateTime", column = "update_time", jdbcType = JdbcType.TIMESTAMP, javaType=Date.class)
    })
    Ticket selectByPrimaryKey(@Param("id")Integer id);

    @Select("SELECT" + TICKET_COLUMN_LIST + "FROM TICKET WHERE id = #{id}")
    @ResultMap(value = "ticketMap")
    Ticket findById(@Param("id") String id);

    @Select("SELECT" + TICKET_COLUMN_LIST + "FROM TICKET where status = 1 and check_in = #{checkIn} and check_out = #{checkOut} and start_time >= #{startTime} and start_time < #{theNextDay}")
    @ResultMap(value = "ticketMap")
    List<Ticket> search(@Param("checkIn") String checkIn, @Param("checkOut") String checkOut, @Param("startTime") Date startTime, @Param("theNextDay")Date theNextDay);

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

    @Insert("INSERT INTO ticket (trainnumber,checkin,checkout,starttime,endtime,seattype,seatnumber,price) VALUES(#{trainNumber}, #{checkIn}, #{checkOut}, #{startTime}, #{endTime}, #{seatType}, #{seatNumber}, #{price})")
    void insert(@Param("trainNumber") String trainNumber, @Param("checkin") String checkin, @Param("checkout") String checkout, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("seatType") String seatType, @Param("seatNumber") String seatNumber, @Param("price")double price);

    @Update({"UPDATE ticket SET isordered = #{orderInfo} WHERE id =#{id}"})
    void setIsOrdered(@Param("id") String id, @Param("orderInfo") String orderInfo);

//    @Delete("DELETE FROM ticket WHERE id=#{id}")
//    void delete(@Param("id") String id);

//    @Select("SELECT count(*) FROM TICKET  where checkin = #{checkin}  and checkout = #{checkout} and starttime >= #{startTime} and startTime < #{theNextDay}")
//    Integer countByChecckinCheckout(@Param("checkin") String checkin, @Param("checkout") String checkout, @Param("startTime") Date startTime, @Param("theNextDay")Date theNextDay);



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

    //////////////////////////////////////////////////////////////////

    int deleteByPrimaryKey(Integer id);

    int insert(Ticket record);

    int insertSelective(Ticket record);

    @UpdateProvider(type = TutorDynaSqlProvider.class, method = "updateTicketByPrimaryKeySelectiveSQL")
    int updateByPrimaryKeySelective(Ticket ticket);

    int updateByPrimaryKey(Ticket record);

    List<Ticket> selectList();

    List<Ticket> selectByNameAndTicketId(@Param("ticketName") String ticketName, @Param("ticketId") Integer ticketId);

    List<Product> selectByNameAndCategoryIds(@Param("ticketName") String ticketName, @Param("categoryIdList") List<Integer> categoryIdList);

}
