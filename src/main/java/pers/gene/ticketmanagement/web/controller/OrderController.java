package pers.gene.ticketmanagement.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
             ticket.setCustomerId(customer.getId());
             ticket.setOrdered(true);
             orderService.newOrder(order);
             ticketService.setIsOrdered(order, "Y");
         }
        return "success";
    }

    @RequestMapping("/myOrder")
    public String MyOrder(@RequestParam(required = true, defaultValue = "1") Integer page, HttpServletRequest request, Model model){
        String jwt = request.getHeader("Authorization");
        Customer customer = customerService.getCustomerByJWT(jwt);
        List<Order> orderList = orderService.findOrderByCustomer(customer);
        PageHelper.startPage(page, 10);
        PageInfo<Order> pageInfo = new PageInfo<>(orderList);
        request.setAttribute("orderList", orderList);
        request.setAttribute("pageInfo", pageInfo);
        model.addAttribute("orderList", orderList);
        model.addAttribute("pageInfo",pageInfo);
        return "myOrder";
//        for (Order order : orderList){
//            model.addAttribute("orderId", order.getId());
//            model.addAttribute("trainNumber", order.getTicket().getTrainNumber());
//            model.addAttribute("checkin", order.getTicket().getCheckin());
//            model.addAttribute("checkout", order.getTicket().getCheckout());
//            model.addAttribute("startTime", order.getTicket().getStartTime());
//            model.addAttribute("endTime", order.getTicket().getEndTime());
//            model.addAttribute("seatType", order.getTicket().getSeatType());
//            model.addAttribute("seatNumber", order.getTicket().getSeatNumber());
//            model.addAttribute("price", order.getTicket().getPrice());
//        }
    }

    /**
     * 取消订单、退票
     * @param request
     * @return
     */
    @RequestMapping("/roll")
    @Transactional
    public String roll(HttpServletRequest request){
        String orderId = request.getAttribute("orderId").toString();
        Order order = orderService.findById(orderId);
        String ticketId = order.getTicket().getId();
        Ticket ticket = ticketService.findById(ticketId);
        ticket.setOrdered(false);
        ticket.setCustomerId(null);
        ticketService.setIsOrdered(order, "N");
        orderService.deleteById(orderId);
        return "orderList";
    }
}
