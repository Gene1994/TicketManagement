package com.genequ.ticketmanagement.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Ticket {

    private Integer id;

    private String trainNumber;

    private String checkIn;

    private String checkOut;

    private  Date startTime;

    private  Date endTime;

    private   String seatType;

    private  String seatNumber;

    private BigDecimal price;

    private Integer stock;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
