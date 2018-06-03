package com.genequ.ticketmanagement.web.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.genequ.ticketmanagement.domain.Ticket;
import com.genequ.ticketmanagement.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @RequestMapping("/index")
    public String index(){
        return "ticketSearch";
    }

    @RequestMapping("/search")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public String search(@RequestParam(required = true, defaultValue = "1") Integer page, HttpServletRequest request, Ticket ticket, Model model) {
        String checkin = ticket.getCheckin();
        String checkout = ticket.getCheckout();
        //表单提交自动把date 类型转为String
        Date startTime = ticket.getStartTime();
        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(page, 10);
        List<Ticket> ticketList = ticketService.search(checkin, checkout, startTime, theNextDay(startTime));
        PageInfo<Ticket> pageInfo = new PageInfo<>(ticketList);
        request.setAttribute("ticketList", ticketList);
        request.setAttribute("pageInfo", pageInfo);
        model.addAttribute("list", ticketList);
        model.addAttribute("pageInfo",pageInfo);
        return "ticketList";
    }

    private Date theNextDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
        return c.getTime();
    }

    //关键：将前端input type date类型时间格式化为"yyyy-MM-dd 00:00:00"转给后端Date类型
    //Converter类型转换，它支持从一个Object转为另一个Object。
    @Bean
    public Converter<String, Date> addNewConvert() {//String转Date
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = sdf.parse((String) source);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return date;
            }
        };
    }

    private int getCurrentPage(HttpServletRequest request) {
        String value = request.getParameter("currentPage");
        if (value == null || value.trim().isEmpty()) {
            return 1;
        }
        return Integer.parseInt(value);
    }

}
