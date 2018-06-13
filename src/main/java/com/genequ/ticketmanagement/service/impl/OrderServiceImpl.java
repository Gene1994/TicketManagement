package com.genequ.ticketmanagement.service.impl;

import com.genequ.ticketmanagement.domain.Customer;
import com.genequ.ticketmanagement.domain.Order;
import com.genequ.ticketmanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.genequ.ticketmanagement.mapper.OrderMapper;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void newOrder(Order order){
        orderMapper.insert(order.getId(), order.getCustomer().getId(),order.getCustomer().getUserName(), order.getTicket().getId(), order.getTicket().getTrainNumber(), order.getTicket().getCheckin(), order.getTicket().getCheckout(), order.getTicket().getStartTime(), order.getTicket().getEndTime(), order.getTicket().getSeatType(), order.getTicket().getSeatNumber(), order.getTicket().getPrice());
    }

    public List<Order> findOrderByCustomer(Customer customer){
        return orderMapper.findOrderByCustomerId(customer.getId());
    }

    public Order findById(String orderId){
        return orderMapper.findById(orderId);
    }

    public void deleteById(String orderId){
        orderMapper.deleteById(orderId);
    }
}
