package com.genequ.ticketmanagement.vo;

import lombok.Data;

import java.math.BigDecimal;

public @Data class TicketListVo {
    private String trainNumber;

    private String checkIn;

    private String checkOut;

    private String startTime;

    private String endTime;

    private String seatType;

    private String seatNumber;

    private BigDecimal price;

    private Integer stock;
}
