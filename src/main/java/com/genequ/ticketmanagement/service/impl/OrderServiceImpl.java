package com.genequ.ticketmanagement.service.impl;

import com.genequ.ticketmanagement.pojo.User;
import com.genequ.ticketmanagement.pojo.Order;
import com.genequ.ticketmanagement.pojo.Ticket;
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
    public boolean newOrder(List<Ticket> ticketList, int ticketNumber, User user) {
        if (ticketList != null || ticketList.size() < 1 || ticketNumber < 1 || ticketNumber > ticketList.size() || user != null){
            return false;
        }
//选择数量->订票->跳转信息填写页面（评审是否需要）->订票成功 （数据库事务）
        for (int i = 0; i < ticketNumber; i++) {
            Ticket ticket = ticketList.get(i);
            Order order = new Order();
            order.setId(UUID.randomUUID().toString());
            order.setUser(user);
            order.setTicket(ticket);
            ticket.setCustomerId(user.getId());
            ticket.setOrdered(true);
            orderMapper.insert(order.getId(), user.getId(), user.getUserName(), ticket.getId(), ticket.getTrainNumber(), ticket.getCheckin(),
                    ticket.getCheckout(), ticket.getStartTime(), ticket.getEndTime(), ticket.getSeatType(), ticket.getSeatNumber(), ticket.getPrice());
            ticketService.setIsOrdered(order, "Y");
        }
        return true;
    }

    public List<Order> findOrderByCustomer(User user) {
        return orderMapper.findOrderByCustomerId(user.getId());
    }

    public Order findById(String orderId) {
        return orderMapper.findById(orderId);
    }

    public boolean roll(String orderId) {
        Order order = findById(orderId);
        if (order == null){
            return false;
        }
        String ticketId = order.getTicket().getId();
        Ticket ticket = ticketService.findById(ticketId);
        ticket.setOrdered(false);
        ticket.setCustomerId(null);
        orderMapper.deleteById(orderId);
        ticketService.setIsOrdered(order, "N");
        return true;
    }
}
