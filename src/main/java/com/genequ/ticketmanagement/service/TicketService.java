package com.genequ.ticketmanagement.service;

import com.genequ.ticketmanagement.domain.Order;
import com.genequ.ticketmanagement.mapper.TicketMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.genequ.ticketmanagement.domain.Ticket;

import java.util.Date;
import java.util.List;
@Slf4j
@Service
public class TicketService {
//    void add(Ticket ticket);
//    void sell();
//    void roll();
    @Autowired
TicketMapper ticketMapper;

    public List<Ticket> search(String checkin, String checkout, Date startTime, Date theNextDay){
        return ticketMapper.findByCheckinCheckout(checkin, checkout, startTime, theNextDay);
    }

//    public int countTicket(String checkin, String checkout, Date startTime, Date theNextDay){
//        return ticketMapper.countByChecckinCheckout(checkin, checkout, startTime, theNextDay);
//    }

    public List<Ticket> findByTrainNumberStartTime(String ticketNumber, Date startTime){
        return ticketMapper.findByTrainNumberStartTime(ticketNumber, startTime);
    }

    public void setIsOrdered(Order order, String orderInfo){
        ticketMapper.setIsOrdered(order.getTicket().getId(), orderInfo);
    }

    public Ticket findById(String ticketId){
        return ticketMapper.findById(ticketId);
    }
}
