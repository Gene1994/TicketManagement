package com.genequ.ticketmanagement.service;

import com.genequ.ticketmanagement.common.ServerResponse;
import com.genequ.ticketmanagement.pojo.Ticket;
import com.genequ.ticketmanagement.vo.TicketDetailVo;
import com.github.pagehelper.PageInfo;

import java.util.Date;

public interface ITicketService {

    ServerResponse saveOrUpdateTicket(Ticket ticket);

    ServerResponse<String> setSaleStatus(Integer ticketId,Integer status);

    ServerResponse<TicketDetailVo> manageTicketDetail(Integer ticketId);

    ServerResponse<PageInfo> getTicketList(int pageNum, int pageSize);

    ServerResponse<TicketDetailVo> getTicketDetailVo(Integer ticketId);

    ServerResponse<PageInfo> searchTicket(String checkIn, String checkOut,Date date,int pageNum,int pageSize,String orderBy);

}
