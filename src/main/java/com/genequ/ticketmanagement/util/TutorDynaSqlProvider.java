package com.genequ.ticketmanagement.util;

import com.genequ.ticketmanagement.pojo.Order;
import com.genequ.ticketmanagement.pojo.Ticket;
import org.apache.ibatis.jdbc.SQL;

public class TutorDynaSqlProvider {

    //TicketMapper
    public String updateByPrimaryKeySelectiveSQL(final Ticket ticket){
        return new SQL(){{
            UPDATE("ticket");
            if (ticket.getId() != null){
                SET("id = #{id}");
            }
            if (ticket.getTrainNumber() != null){
                SET("train_number = #{trainNumber}");
            }
            if (ticket.getCheckIn() != null){
                SET("check_in = #{checkIn}");
            }
            if (ticket.getCheckOut() != null){
                SET("check_out = #{checkOut}");
            }
            if (ticket.getStartTime() != null){
                SET("start_time = #{startTime}");
            }
            if (ticket.getEndTime() != null){
                SET("end_time = #{endTime}");
            }
            if (ticket.getSeatType() != null){
                SET("seat_type = #{seatType}");
            }
            if (ticket.getSeatNumber() != null){
                SET("seat_number = #{seatNumber}");
            }
            if (ticket.getPrice() != null){
                SET("price = #{price}");
            }
            if (ticket.getStock() != null){
                SET("stock = #{stock}");
            }
            if (ticket.getStatus() != null){
                SET("status = #{status}");
            }
            if (ticket.getCreateTime() != null){
                SET("create_time = #{createTime}");
            }
            if (ticket.getStock() != null){
                SET("update_time = now()");
            }
            WHERE("id = #{id,jdbcType=INTEGER}");
        }}.toString();
    }

    //OrderMapper
    public String updateByPrimaryKeySelectiveSQL(final Order order){
        return new SQL(){{
            UPDATE("order");
            if (order.getOrderNo() != null){
                SET("order_no = #{orderNo,jdbcType=BIGINT}");
            }
            if (order.getUserId() != null){
                SET("user_id = #{userId,jdbcType=INTEGER}");
            }
            if (order.getShippingId() != null){
                SET("shipping_id = #{shippingId,jdbcType=INTEGER}");
            }
            if (order.getPayment() != null){
                SET("payment = #{payment,jdbcType=DECIMAL}");
            }
            if (order.getPaymentType() != null){
                SET("payment_type = #{paymentType,jdbcType=INTEGER}");
            }
            if (order.getPostage() != null){
                SET("postage = #{postage,jdbcType=INTEGER}");
            }
            if (order.getStatus() != null){
                SET("status = #{status,jdbcType=INTEGER}");
            }
            if (order.getPaymentTime() != null){
                SET("payment_time = #{paymentTime,jdbcType=TIMESTAMP}");
            }
            if (order.getSendTime() != null){
                SET("send_time = #{sendTime,jdbcType=TIMESTAMP}");
            }
            if (order.getEndTime() != null){
                SET("end_time = #{endTime,jdbcType=TIMESTAMP}");
            }
            if (order.getCloseTime() != null){
                SET("close_time = #{closeTime,jdbcType=TIMESTAMP}");
            }
            if (order.getCreateTime() != null){
                SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
            }
            if (order.getUpdateTime() != null){
                SET("update_time = now(),");
            }
            WHERE("id = #{id,jdbcType=INTEGER}");
        }}.toString();
    }
}
