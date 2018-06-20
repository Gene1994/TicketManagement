package com.genequ.ticketmanagement.service.impl;

import com.genequ.ticketmanagement.domain.Customer;
import com.genequ.ticketmanagement.domain.Order;
import com.genequ.ticketmanagement.domain.Ticket;
import com.genequ.ticketmanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.genequ.ticketmanagement.mapper.OrderMapper;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    TicketServiceImpl ticketService;

    @Override
    public void newOrder(List<Ticket> ticketList, int ticketNumber, Customer customer) {
//选择数量->订票->跳转信息填写页面（评审是否需要）->订票成功 （数据库事务）
        for (int i = 0; i < ticketNumber; i++) {
            Ticket ticket = ticketList.get(i);
            Order order = new Order();
            order.setId(UUID.randomUUID().toString());
            order.setCustomer(customer);
            order.setTicket(ticket);
            ticket.setCustomerId(customer.getId());
            ticket.setOrdered(true);
            orderMapper.insert(order.getId(), order.getCustomer().getId(), order.getCustomer().getUserName(),
                    order.getTicket().getId(), order.getTicket().getTrainNumber(), order.getTicket().getCheckin(),
                    order.getTicket().getCheckout(), order.getTicket().getStartTime(), order.getTicket().getEndTime(),
                    order.getTicket().getSeatType(), order.getTicket().getSeatNumber(), order.getTicket().getPrice());
            ticketService.setIsOrdered(order, "Y");
        }
    }

    public List<Order> findOrderByCustomer(Customer customer) {
        return orderMapper.findOrderByCustomerId(customer.getId());
    }

    public Order findById(String orderId) {
        return orderMapper.findById(orderId);
    }

    public void roll(String orderId) {
        Order order = findById(orderId);
        String ticketId = order.getTicket().getId();
        Ticket ticket = ticketService.findById(ticketId);
        ticket.setOrdered(false);
        ticket.setCustomerId(null);
        orderMapper.deleteById(orderId);
        ticketService.setIsOrdered(order, "N");
    }
}
