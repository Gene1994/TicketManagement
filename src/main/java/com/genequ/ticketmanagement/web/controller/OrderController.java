package com.genequ.ticketmanagement.web.controller;

import com.genequ.ticketmanagement.domain.Customer;
import com.genequ.ticketmanagement.domain.Order;
import com.genequ.ticketmanagement.domain.Ticket;
import com.genequ.ticketmanagement.service.impl.CustomerServiceImpl;
import com.genequ.ticketmanagement.service.impl.TicketServiceImpl;
import com.genequ.ticketmanagement.web.constant.ConstantKey;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.genequ.ticketmanagement.service.impl.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @Autowired
    private TicketServiceImpl ticketServiceImpl;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @RequestMapping("/new")
    @Transactional//数据库事务
    public synchronized String newOrder(HttpServletRequest request) throws ParseException {
        Customer customer = customerServiceImpl.getCustomerByJWT(request.getHeader("Authorization "));
        String trainNumber = request.getParameter("trainNumber").toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = sdf.parse(request.getParameter("startTime").toString());
        //获得符合条件的票集合
        List<Ticket> ticketList = ticketServiceImpl.findByTrainNumberStartTime(trainNumber, startTime);
        int ticketNumber = Integer.parseInt(request.getParameter("ticketNumber").toString());
        if (ticketNumber > ticketList.size()) {
            //没有这么多票
            return "fail";
        }
        orderServiceImpl.newOrder(ticketList, ticketNumber, customer);
        return "success";
    }

    //乐观锁（待实现）
    @RequestMapping("/myOrder")
    public String MyOrder(@RequestParam(required = true, defaultValue = "1") Integer page, HttpServletRequest request, Model model) {
        String jwt = request.getHeader("Authorization");
        Customer customer = customerServiceImpl.getCustomerByJWT(jwt);
        List<Order> orderList = orderServiceImpl.findOrderByCustomer(customer);
        PageHelper.startPage(page, 10);
        PageInfo<Order> pageInfo = new PageInfo<>(orderList);
        request.setAttribute("orderList", orderList);
        request.setAttribute("pageInfo", pageInfo);
        model.addAttribute("orderList", orderList);
        model.addAttribute("pageInfo", pageInfo);
        return "myOrder";
    }

    /**
     * 取消订单、退票
     *
     * @param request
     * @return
     */
    @RequestMapping("/roll")
    @Transactional
    public synchronized String roll(HttpServletRequest request) {
        String orderId = request.getAttribute("orderId").toString();
        Order order = orderServiceImpl.findById(orderId);
        String ticketId = order.getTicket().getId();
        Ticket ticket = ticketServiceImpl.findById(ticketId);
        ticket.setOrdered(false);
        ticket.setCustomerId(null);
        ticketServiceImpl.setIsOrdered(order, "N");
        orderServiceImpl.deleteById(orderId);
        return "orderList";
    }
}
