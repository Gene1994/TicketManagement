package com.genequ.ticketmanagement.service;

import com.genequ.ticketmanagement.common.ServerResponse;
import com.genequ.ticketmanagement.pojo.Shipping;
import com.github.pagehelper.PageInfo;


public interface IShippingService {

    ServerResponse add(Integer userId, Shipping shipping);
    ServerResponse<String> del(Integer userId, Integer shippingId);
    ServerResponse update(Integer userId, Shipping shipping);
    ServerResponse<Shipping> select(Integer userId, Integer shippingId);
    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);

}
