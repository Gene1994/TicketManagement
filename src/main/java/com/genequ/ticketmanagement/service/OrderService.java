package com.genequ.ticketmanagement.service;

import com.genequ.ticketmanagement.pojo.User;
import com.genequ.ticketmanagement.pojo.Ticket;

import java.util.List;

public interface OrderService {

    boolean newOrder(List<Ticket> ticketList, int ticketNumber, User user);

    boolean roll(String orderId);
}
