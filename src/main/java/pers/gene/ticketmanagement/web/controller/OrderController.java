package pers.gene.ticketmanagement.web.controller;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.gene.ticketmanagement.domain.Customer;
import pers.gene.ticketmanagement.domain.Order;
import pers.gene.ticketmanagement.domain.Ticket;
import pers.gene.ticketmanagement.service.CustomerService;
import pers.gene.ticketmanagement.service.OrderService;
import pers.gene.ticketmanagement.service.TicketService;
import pers.gene.ticketmanagement.web.constant.ConstantKey;

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
    private CustomerService customerService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/new")
    @Transactional//数据库事务
    public String newOrder(HttpServletRequest request) throws ParseException {
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
         Customer customer = customerService.getCustomerByUserName(userName);

         String trainNumber =  request.getParameter("trainNumber").toString();
         SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         String dstr = request.getParameter("startTime").toString();
         Date startTime = sdf.parse(dstr);
         List<Ticket> ticketList = ticketService.findByTrainNumberStartTime(trainNumber, startTime);

         int ticketNumber = Integer.parseInt(request.getParameter("ticketNumber").toString());
         //选择数量->订票->跳转信息填写页面（评审是否需要）->订票成功 （数据库事务）
         //new Order 票isOrdered 票customerId
         if (ticketNumber > ticketList.size()){
             //没有这么多票
             return "fail";
         }
         for (int i = 0; i < ticketNumber; i++){
             Ticket ticket = ticketList.get(i);
             Order order = new Order();
             order.setId(UUID.randomUUID().toString());
             order.setCustomer(customer);
             order.setTicket(ticket);
             orderService.newOrder(order);
             ticketService.setIsOrdered(order);
         }
        return "success";
    }
}
