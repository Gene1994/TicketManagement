package com.genequ.ticketmanagement.vo;

import lombok.Data;

import java.math.BigDecimal;


public @Data class OrderItemVo {

    private Long orderNo;

    private Integer ticketId;

    private String trainNumber;

    private String checkIn;

    private String checkOut;

    private String startTime;

    private  String endTime;

    private   String seatType;

    private  String seatNumber;

    private BigDecimal currentUnitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

    private String createTime;

}
