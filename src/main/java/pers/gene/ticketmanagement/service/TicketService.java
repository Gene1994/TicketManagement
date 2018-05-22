package pers.gene.ticketmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.gene.ticketmanagement.domain.Order;
import pers.gene.ticketmanagement.domain.Ticket;
import pers.gene.ticketmanagement.repository.TicketMapper;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    public void setIsOrdered(Order ordered, String orderInfo){
        ticketMapper.setIsOrdered(ordered.getTicket().getId(), orderInfo);
    }

    public Ticket findById(String ticketId){
        return ticketMapper.findById(ticketId);
    }
}
