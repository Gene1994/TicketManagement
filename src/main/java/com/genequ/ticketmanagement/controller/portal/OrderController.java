package com.genequ.ticketmanagement.controller.portal;

import com.genequ.ticketmanagement.service.impl.OrderServiceImpl;
import com.genequ.ticketmanagement.service.impl.TicketServiceImpl;
import com.genequ.ticketmanagement.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private UserServiceImpl customerServiceImpl;

    @Autowired
    private TicketServiceImpl ticketServiceImpl;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

//    @RequestMapping("/new")
//    @Transactional//数据库事务
////    public synchronized String newOrder(HttpServletRequest request) throws ParseException {
//        User user = customerServiceImpl.getCustomerByJWT(request.getHeader("Authorization "));
//        String trainNumber = request.getParameter("trainNumber").toString();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date startTime = sdf.parse(request.getParameter("startTime").toString());
//        //获得符合条件的票集合
//        List<Ticket> ticketList = ticketServiceImpl.findByTrainNumberStartTime(trainNumber, startTime);
//        int ticketNumber = Integer.parseInt(request.getParameter("ticketNumber").toString());
//
//        if (!orderServiceImpl.newOrder(ticketList, ticketNumber, user)) {
//            return "fail";
//        }
//        return "success";
//    }

    //乐观锁（待实现）
    @RequestMapping("/myOrder")
    public String MyOrder(@RequestParam(required = true, defaultValue = "1") Integer page, HttpServletRequest request, Model model) {
//        String jwt = request.getHeader("Authorization");
//        User user = customerServiceImpl.getCustomerByJWT(jwt);
//        List<Order> orderList = orderServiceImpl.findOrderByCustomer(user);
//        PageHelper.startPage(page, 10);
//        PageInfo<Order> pageInfo = new PageInfo<>(orderList);
//        request.setAttribute("orderList", orderList);
//        request.setAttribute("pageInfo", pageInfo);
//        model.addAttribute("orderList", orderList);
//        model.addAttribute("pageInfo", pageInfo);
        return "myOrder";
    }

    /**
     * 取消订单、退票
     * @param request
     * @return
     */
    @RequestMapping("/roll")
    @Transactional
    public synchronized String roll(HttpServletRequest request) {
        String orderId = request.getAttribute("orderId").toString();
        if (orderServiceImpl.roll(orderId)){
            return "orderList";
        }
        return "fail";
    }
}
