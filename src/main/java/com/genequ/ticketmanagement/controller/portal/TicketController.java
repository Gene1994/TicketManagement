package com.genequ.ticketmanagement.controller.portal;


import com.genequ.ticketmanagement.common.ServerResponse;
import com.genequ.ticketmanagement.pojo.Ticket;
import com.genequ.ticketmanagement.service.ITicketService;
import com.genequ.ticketmanagement.vo.TicketDetailVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/ticket/")
public class TicketController {

    @Autowired
    ITicketService iTicketService;

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<TicketDetailVo> detail(Integer ticketId){
        return iTicketService.getTicketDetailVo(ticketId);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "checkIn",required = false)String checkIn,
                                         @RequestParam(value = "checkOut",required = false)String checkOut,
                                         @RequestParam(value = "Date",required = false)Date date,
                                         @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy",defaultValue = "") String orderBy){
        return iTicketService.searchTicket(checkIn,checkOut,date,pageNum,pageSize,orderBy);
    }

}
