package pers.gene.ticketmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.gene.ticketmanagement.domain.Ticket;
import pers.gene.ticketmanagement.repository.TicketMapper;

import java.util.Date;
import java.util.List;

@Service
public class TicketService {
//    void add(Ticket ticket);
//    void sell();
//    void roll();
    @Autowired
    TicketMapper ticketMapper;

    public List<Ticket> search(String checkin, String checkout, Date startTime){
        return ticketMapper.findByChecckinCheckout(checkin, checkout, startTime);
    }
}
