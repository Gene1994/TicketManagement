package pers.gene.ticketmanagement.web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pers.gene.ticketmanagement.domain.Customer;
import pers.gene.ticketmanagement.service.CustomerService;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
//这个注解导致返回字符串
//@RestController
//@ResponseBody
@Controller
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);


    @Autowired
    private CustomerService customerService;

    @RequestMapping(value="/index",method= RequestMethod.POST)
    public Customer regist(HttpServletRequest request){
//        CustomerService service = new CustomerService();
//        if (request.getParameter("passwordsignup").equals(request.getParameter("passwordsignup_confirm"))){
            Customer customer = new Customer();
            customer.setId(UUID.randomUUID().toString());
            customer.setUsername(request.getParameter("usernamesignup"));
            customer.setPassword(request.getParameter("passwordsignup"));
            customer.setEmail(request.getParameter("emailsignup"));
            customer.setCellphone("");
            customerService.regist(customer);
            return customer;
//        }

    }

    @RequestMapping("/go")
    public String go(){
        return "index";
    }

//    @RequestMapping("/list")
//    public List<Customer> getCustomer(){
//        LOGGER.info("从数据库读取Custom集合");
//        return customerService.getList();
//    }
}
