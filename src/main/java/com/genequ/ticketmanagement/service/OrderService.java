package com.genequ.ticketmanagement.service;

import com.genequ.ticketmanagement.domain.Customer;
import com.genequ.ticketmanagement.domain.Order;
import com.genequ.ticketmanagement.domain.Ticket;

import java.util.List;

public interface OrderService {


    void newOrder(List<Ticket> ticketList, int ticketNumber, Customer customer);
}
