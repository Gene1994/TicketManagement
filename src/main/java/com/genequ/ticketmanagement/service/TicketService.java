package com.genequ.ticketmanagement.service;

import com.genequ.ticketmanagement.domain.Ticket;

import java.util.Date;
import java.util.List;

public interface TicketService {

    /**
     * 搜索
     * @param checkin
     * @param checkout
     * @param startTime
     * @param theNextDay
     * @return
     */
    List<Ticket> search(String checkin, String checkout, Date startTime, Date theNextDay);

    String sell();

//    void add(Ticket ticket);

//    void roll();
}
