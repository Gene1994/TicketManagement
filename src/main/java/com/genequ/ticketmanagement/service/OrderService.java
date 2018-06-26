package com.genequ.ticketmanagement.service;

import com.genequ.ticketmanagement.domain.Customer;
import com.genequ.ticketmanagement.domain.Ticket;

import java.util.List;

public interface OrderService {

    boolean newOrder(List<Ticket> ticketList, int ticketNumber, Customer customer);

    boolean roll(String orderId);
}
