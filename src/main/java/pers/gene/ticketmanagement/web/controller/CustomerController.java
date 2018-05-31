package pers.gene.ticketmanagement.web.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pers.gene.ticketmanagement.domain.Customer;
import pers.gene.ticketmanagement.repository.CustomerMapper;
import pers.gene.ticketmanagement.service.CustomerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//这个注解导致返回字符串
//@RestController
//@ResponseBody
@Slf4j
@Controller
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);


    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
//        String jwt = request.getHeader("Authorization");
//        response.addHeader("AvatarUrl", customerService.getCustomerByJWT(jwt).getAvatarUrl());
        return "login";
    }

    /**
     * 注册
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String regist(HttpServletRequest request) {
//        CustomerService service = new CustomerService();
        if (request.getParameter("passwordsignup").equals(request.getParameter("passwordsignup_confirm"))) {
            Customer customer = new Customer();
            customer.setId(UUID.randomUUID().toString());
            customer.setUserName(request.getParameter("usernamesignup"));
//            customer.setPassword(request.getParameter("passwordsignup"));
            //使用BCryptPasswordEncoder管理密码
            customer.setPassword(DigestUtils.md5DigestAsHex((request.getParameter("passwordsignup")).getBytes()));
            customer.setEmail(request.getParameter("emailsignup"));
            customer.setCellphone(request.getParameter("cellphonesignup"));
            customerService.regist(customer);
//            confirm("注册成功")；
            return "success";
        } else {
            return "fail";
        }
    }


//    @RequestMapping("/login")
//    public String login(HttpServletRequest request) {
//        String userName = request.getParameter("username");
//        String password = request.getParameter("password");
//        if (checkEmailFormat(userName)) {
//            request.getSession().setAttribute("customer", customerService.getCustomerByEmail(userName));
//        } else {
//            request.getSession().setAttribute("customer", customerService.getCustomerByUserName(userName));
//        }
//        return customerService.login(userName, password);
//    }

    //验证邮箱格式
//    boolean checkEmailFormat(String email) {
//        Pattern emailPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
//        Matcher matcher = emailPattern.matcher(email);
//        if (matcher.find()) {
//            return true;
//        }
//        return false;
//    }

    @RequestMapping("/index")
    public String go() {
        return "index";
    }


    /**
     * 登录成功 将用户头像url传至response header中 返回至index
     * @return
     */
    @RequestMapping("/success")
    public String success(HttpServletRequest request, HttpServletResponse response) {
        return "success";
    }

    @RequestMapping("/fail")
    public String fail() {
        return "fail";
    }

    @RequestMapping("myProfile")
    public String myProfile() {
        return "myProfile";
    }

    /**
     * 上传头像
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("/uploadAvatar")
    public String uploadAvatar(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        Customer customer = customerService.getCustomerByJWT(request.getHeader("Authorization"));
//        String contentType = file.getContentType();
        String fileName = new Date().getTime() + "_" + file.getOriginalFilename();//文件名+上传时的时间戳 避免重名
//        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        String filePath = "D:\\TicketManagement\\customer\\avatar\\";
        try {
            customerService.uploadAvatar(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
        }
        customer.setAvatarUrl(filePath + fileName);
        customerMapper.setAvatarUrl(customer.getId(), filePath + fileName);
        return "success";
    }

    @RequestMapping("/showAvatar")
    public String showAvatar(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String avatarUrl = customerService.getCustomerByJWT(request.getHeader("Authorization")).getAvatarUrl();
//        try {
//            FileInputStream hFile=new FileInputStream(avatarUrl);
//            int i = hFile.available();
//            byte data[]=new byte[i];
//            hFile.read(data);
//            hFile.close();
//            response.setContentType("image/*");
//            OutputStream toClient=response.getOutputStream();
//            toClient.write(data);
//            toClient.close();
//        }catch (IOException e){
//            PrintWriter toClient=response.getWriter();
//            response.setContentType("text/html;charset=gb2312");
//            toClient.write("无法打开图片");
//            toClient.close();
//        }
        model.addAttribute("avatarUrl", avatarUrl);
        return "success";
    }
}
