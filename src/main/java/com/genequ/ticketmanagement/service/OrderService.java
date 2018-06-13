package com.genequ.ticketmanagement.service;

import com.genequ.ticketmanagement.domain.Order;

public interface OrderService {

    /**
     * 生成订单
     * @param order
     */
    void newOrder(Order order);
}
