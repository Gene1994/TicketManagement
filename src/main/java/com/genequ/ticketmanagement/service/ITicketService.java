package com.genequ.ticketmanagement.service;

import com.genequ.ticketmanagement.common.ServerResponse;
import com.genequ.ticketmanagement.pojo.Ticket;
import com.genequ.ticketmanagement.vo.TicketDetailVo;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.List;

public interface ITicketService {

    /**
     * 根据始发站、到达站和出发日期查询可以预定的票
     * @param checkin
     * @param checkout
     * @param startTime
     * @param theNextDay
     * @return
     */
    List<Ticket> search(String checkin, String checkout, Date startTime, Date theNextDay);

    ServerResponse saveOrUpdateTicket(Ticket ticket);

    ServerResponse<String> setSaleStatus(Integer ticketId,Integer status);

    ServerResponse<TicketDetailVo> manageTicketDetail(Integer ticketId);

    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize);

    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword,Integer categoryId,int pageNum,int pageSize,String orderBy);


}
