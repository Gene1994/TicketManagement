package com.genequ.ticketmanagement.service.impl;

import com.genequ.ticketmanagement.common.Const;
import com.genequ.ticketmanagement.common.ResponseCode;
import com.genequ.ticketmanagement.common.ServerResponse;
import com.genequ.ticketmanagement.mapper.TicketMapper;
import com.genequ.ticketmanagement.pojo.Ticket;
import com.genequ.ticketmanagement.service.ITicketService;
import com.genequ.ticketmanagement.util.DateTimeUtil;
import com.genequ.ticketmanagement.vo.TicketDetailVo;
import com.genequ.ticketmanagement.vo.TicketListVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("iTicketService")
public class TicketServiceImpl implements ITicketService {

    @Autowired
    TicketMapper ticketMapper;


    @Override
    public ServerResponse<TicketDetailVo> getTicketDetailVo(Integer ticketId) {
        if(ticketId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Ticket ticket = ticketMapper.selectByPrimaryKey(ticketId);
        if (ticket == null){
            return ServerResponse.createByErrorMessage("不存在该票");
        }
        TicketDetailVo ticketDetailVo = assembleTicketDetailVo(ticket);
        return ServerResponse.createBySuccess(ticketDetailVo);
    }

    private TicketDetailVo assembleTicketDetailVo(Ticket ticket){
        TicketDetailVo ticketDetailVo = new TicketDetailVo();
        ticketDetailVo.setTrainNumber(ticket.getTrainNumber());
        ticketDetailVo.setCheckIn(ticket.getCheckIn());
        ticketDetailVo.setCheckOut(ticket.getCheckOut());
        ticketDetailVo.setStartTime(DateTimeUtil.dateToStr(ticket.getStartTime()));
        ticketDetailVo.setEndTime(DateTimeUtil.dateToStr(ticket.getEndTime()));
        ticketDetailVo.setSeatType(ticket.getSeatType());
        ticketDetailVo.setSeatNumber(ticket.getSeatNumber());
        ticketDetailVo.setPrice(ticket.getPrice());
        ticketDetailVo.setStock(ticket.getStock());
        ticketDetailVo.setStatus(ticket.getStatus());

        ticketDetailVo.setCreateTime(DateTimeUtil.dateToStr(ticket.getCreateTime()));
        ticketDetailVo.setUpdateTime(DateTimeUtil.dateToStr(ticket.getUpdateTime()));
        return ticketDetailVo;
    }

    @Override
    public ServerResponse<PageInfo> searchTicket(String checkIn, String checkOut, Date date, int pageNum, int pageSize, String orderBy) {
        if (StringUtils.isNotBlank(checkIn) || StringUtils.isBlank(checkOut)|| date == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Date nextDay = theNextDay(date);
        // java.util.Date -> java.time.LocalDate
        LocalDate localDay = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localNextDay = nextDay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // java.time.LocalDate -> java.sql.Date
        date = java.sql.Date.valueOf(localDay);
        nextDay = java.sql.Date.valueOf(localNextDay);

        PageHelper.startPage(pageNum,pageSize);
        //排序处理
        if(StringUtils.isNotBlank(orderBy)){
            if(Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
            }
        }
        List<Ticket> ticketList = ticketMapper.search(checkIn,checkOut,date,nextDay);

        List<TicketListVo> ticketListVoList = Lists.newArrayList();
        for(Ticket ticket : ticketList){
            TicketListVo ticketListVo = assembleTicketListVo(ticket);
            ticketListVoList.add(ticketListVo);
        }

        PageInfo pageInfo = new PageInfo(ticketList);
        pageInfo.setList(ticketListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    private TicketListVo assembleTicketListVo(Ticket ticket){
        TicketListVo ticketListVo = new TicketListVo();
        ticketListVo.setTrainNumber(ticket.getTrainNumber());
        ticketListVo.setCheckIn(ticket.getCheckIn());
        ticketListVo.setCheckOut(ticket.getCheckOut());
        ticketListVo.setStartTime(DateTimeUtil.dateToStr(ticket.getStartTime()));
        ticketListVo.setEndTime(DateTimeUtil.dateToStr(ticket.getEndTime()));
        ticketListVo.setSeatType(ticket.getSeatType());
        ticketListVo.setSeatNumber(ticket.getSeatNumber());
        ticketListVo.setPrice(ticket.getPrice());
        ticketListVo.setStock(ticket.getStock());
        return ticketListVo;
    }
    private Date theNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
//        calendar.clear();//清除缓存
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
        return calendar.getTime();
    }

    @Override
    public ServerResponse saveOrUpdateTicket(Ticket ticket) {
        return null;
    }

    @Override
    public ServerResponse<String> setSaleStatus(Integer ticketId, Integer status) {
        return null;
    }

    @Override
    public ServerResponse<TicketDetailVo> manageTicketDetail(Integer ticketId) {
        return null;
    }

    @Override
    public ServerResponse<PageInfo> getTicketList(int pageNum, int pageSize) {
        return null;
    }



}
