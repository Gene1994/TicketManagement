package pers.gene.ticketmanagement.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.gene.ticketmanagement.domain.Customer;
import pers.gene.ticketmanagement.domain.Ticket;
import pers.gene.ticketmanagement.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @RequestMapping("/search")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public String search(HttpServletRequest request, Ticket ticket) {
        String checkin = ticket.getCheckin();
        String checkout = ticket.getCheckout();
        //表单提交自动把date 类型转为String 
        String startTime = ticket.getStartTime();
//        convert(startTime);
//        Customer customer = (Customer) request.getSession().getAttribute("customer");
        ticketService.search(checkin, checkout, startTime, getCurrentPage(request), 10);
//        request.setAttribute("items", items);
        request.setAttribute("pageBean", ticketService.pageBean);
        //for(Ticket ticket : items)
        return "ticketList";
    }

    @Bean
    public Converter<String, Date> addNewConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
