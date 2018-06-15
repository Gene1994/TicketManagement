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

//    @Autowired
//    Order order;

    @RequestMapping("/new")
    @Transactional//数据库事务
    public synchronized String newOrder(HttpServletRequest request) throws ParseException {
        //解析用户
         String header = request.getHeader("Authorization");
         if (header == null){
             //未登录，请先登录
             return "login";
         }
         String userName = Jwts.parser()
                 .setSigningKey(ConstantKey.SIGNING_KEY)
                 .parseClaimsJws(header.replace("Bearer ", ""))
                 .getBody()
                 .getSubject();
         Customer customer = customerServiceImpl.getCustomerByUserName(userName);

         String trainNumber =  request.getParameter("trainNumber").toString();
         SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         String dstr = request.getParameter("startTime").toString();
         Date startTime = sdf.parse(dstr);
         //获得符合条件的票集合
         List<Ticket> ticketList = ticketServiceImpl.findByTrainNumberStartTime(trainNumber, startTime);

         int ticketNumber = Integer.parseInt(request.getParameter("ticketNumber").toString());
         //选择数量->订票->跳转信息填写页面（评审是否需要）->订票成功 （数据库事务）
         //new Order 票isOrdered 票customerId
         if (ticketNumber > ticketList.size()){
             //没有这么多票
             return "fail";
         }
         for (int i = 0; i < ticketNumber; i++){
             Ticket ticket = ticketList.get(i);
             //这里可以使用@Autowired吗？
             Order order = new Order();
             order.setId(UUID.randomUUID().toString());
             order.setCustomer(customer);
             order.setTicket(ticket);
             ticket.setCustomerId(customer.getId());
             ticket.setOrdered(true);
             orderServiceImpl.newOrder(order);
             ticketServiceImpl.setIsOrdered(order, "Y");
         }
        return "success";
    }

    //乐观锁（待实现）
    @RequestMapping("/myOrder")
    public String MyOrder(@RequestParam(required = true, defaultValue = "1") Integer page, HttpServletRequest request, Model model){
        String jwt = request.getHeader("Authorization");
        Customer customer = customerServiceImpl.getCustomerByJWT(jwt);
        List<Order> orderList = orderServiceImpl.findOrderByCustomer(customer);
        PageHelper.startPage(page, 10);
        PageInfo<Order> pageInfo = new PageInfo<>(orderList);
        request.setAttribute("orderList", orderList);
        request.setAttribute("pageInfo", pageInfo);
        model.addAttribute("orderList", orderList);
        model.addAttribute("pageInfo",pageInfo);
        return "myOrder";
    }

    /**
     * 取消订单、退票
     * @param request
     * @return
     */
    @RequestMapping("/roll")
    @Transactional
    public synchronized String roll(HttpServletRequest request){
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
