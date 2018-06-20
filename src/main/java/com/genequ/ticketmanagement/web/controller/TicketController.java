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
import com.genequ.ticketmanagement.service.impl.TicketServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    TicketServiceImpl ticketServiceImpl;

    @RequestMapping("/index")
    public String index(){
        return "ticketSearch";
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @RequestMapping("/search")
    public String search(@RequestParam(required = true, defaultValue = "1") Integer page,HttpServletRequest request, Model model) {
//        String checkin = ticket.getCheckin();
//        String checkout = ticket.getCheckout();
//        startTime = ticket.getStartTime();
        String checkin = request.getParameter("checkin");
        String checkout = request.getParameter("checkout");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startTime = sdf.parse(request.getParameter("startTime"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(page, 10);
        List<Ticket> ticketList = ticketServiceImpl.search(checkin, checkout, startTime, theNextDay(startTime));
        PageInfo<Ticket> pageInfo = new PageInfo<>(ticketList);
        model.addAttribute("list", ticketList);
        model.addAttribute("pageInfo",pageInfo);
        return "ticketList";
    }

    private Date theNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
//        calendar.clear();//清除缓存
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
        return calendar.getTime();
    }

    //使用Converter类型转换，Controller方法的参数中可以直接使用Bean
//    @Bean
//    public Converter<String, Date> addNewConvert() {//String转Date
//        return new Converter<String, Date>() {
//            @Override
//            public Date convert(String source) {
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                Date date = null;
//                try {
//                    date = sdf.parse((String) source);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return date;
//            }
//        };
//    }
}
