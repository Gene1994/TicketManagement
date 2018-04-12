package pers.gene.ticketmanagement.dao;
import pers.gene.ticketmanagement.domain.Ticket;

public interface TicketDao {
    void add(Ticket ticket);
    void sell();
    void roll();
}
