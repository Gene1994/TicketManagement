package com.genequ.ticketmanagement.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

public @Data class OrderItem {
    private Integer id;

    private Long orderNo;

    private Integer ticketId;

    private String trainNumber;

    private String checkIn;

    private String checkOut;

    private  Date startTime;

    private  Date endTime;

    private   String seatType;

    private  String seatNumber;

    private BigDecimal currentUnitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

    private Date createTime;

    private Date updateTime;

    private Integer userId;

    public OrderItem(Integer id, Long orderNo, Integer ticketId, String trainNumber, String checkIn, String checkOut, Date startTime, Date endTime, String seatType, String seatNumber, BigDecimal currentUnitPrice, Integer quantity, BigDecimal totalPrice, Date createTime, Date updateTime, Integer userId) {
        this.id = id;
        this.orderNo = orderNo;
        this.ticketId = ticketId;
        this.trainNumber = trainNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.startTime = startTime;
        this.endTime = endTime;
        this.seatType = seatType;
        this.seatNumber = seatNumber;
        this.currentUnitPrice = currentUnitPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.userId = userId;
    }


    public OrderItem() {
        super();
    }

}