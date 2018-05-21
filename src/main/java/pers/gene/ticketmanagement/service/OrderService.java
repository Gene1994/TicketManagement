package pers.gene.ticketmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.gene.ticketmanagement.domain.Customer;
import pers.gene.ticketmanagement.domain.Order;
import pers.gene.ticketmanagement.domain.Ticket;
import pers.gene.ticketmanagement.repository.OrderMapper;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    public void newOrder(Order order){
        orderMapper.insert(order.getId(), order.getCustomer().getId(),order.getCustomer().getUserName(), order.getTicket().getId(), order.getTicket().getTrainNumber(), order.getTicket().getCheckin(), order.getTicket().getCheckout(), order.getTicket().getStartTime(), order.getTicket().getEndTime(), order.getTicket().getSeatType(), order.getTicket().getSeatNumber(), order.getTicket().getPrice());
    }

    public List<Order> findOrderByCustomer(Customer customer){
        return orderMapper.findOrderByCustomerId(customer.getId());
    }
}
