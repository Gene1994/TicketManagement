package pers.gene.ticketmanagement.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.gene.ticketmanagement.domain.Customer;
import pers.gene.ticketmanagement.domain.Ticket;
import pers.gene.ticketmanagement.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @RequestMapping("/search")
    public List<Ticket> search(HttpServletRequest request){
        String checkin = request.getAttribute("checkin").toString();
        String checkout = request.getAttribute("checkout").toString();

//<input type="date" id="myDate" value="2014-06-01">
//<p>点击按钮来获得 date 字段的日期。</p>
//<button onclick="myFunction()">试一下</button>
//<p id="demo"></p>
//
//<script>
//                function myFunction() {
//            var x = document.getElementById("myDate").value;
//            document.getElementById("demo").innerHTML = x;
//        }
//</script>

        Date startTime = (Date)request.getAttribute("startTime");
//        Customer customer = (Customer) request.getSession().getAttribute("customer");
        return  ticketService.search(checkin, checkout, startTime);

    }
}
