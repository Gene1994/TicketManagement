package pers.gene.ticketmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pers.gene.ticketmanagement.repository.CustomerMapper;
import pers.gene.ticketmanagement.domain.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.emptyList;

//实现UserDetailsService接口 使用UserDetails验证登录
@Service
public class CustomerService implements UserDetailsService {

    //自动绑定mapper
    @Autowired
    private CustomerMapper customerMapper;

    //    查询数据
    public List<Customer> getCustomerList() {
        return customerMapper.findAll();
    }

    public Customer getCustomerById(String id) {
        return customerMapper.findById(id);
    }

    public Customer getCustomerByUserName(String userName) {
        return customerMapper.findByUserName(userName);
    }

    public Customer getCustomerByEmail(String email) {
        return customerMapper.findByEmail(email);
    }


    public void regist(Customer customer) {
        if (checkUserName(customer.getUserName()) && checkEmailFormat(customer.getEmail()) && checkEmailAddress(customer.getEmail()) && checkMobileNumber(customer.getCellphone())) {
            //验证成功 添加至数据库
            customerMapper.insert(customer.getId(), customer.getUserName(), customer.getPassword(), customer.getEmail(), customer.getCellphone());
        }
    }

    /**
     * 检查数据库中是否存在用户名，存在返回false，不存在返回true。
     *
     * @param userNameSignup
     * @return
     */
    boolean checkUserName(String userNameSignup) {
        if (getAllUserName().contains(userNameSignup)) return false;
        return true;
    }

    boolean checkEmailAddress(String email) {
        if (getAllEmailAdress().contains(email)) return false;
        return true;
    }

    //验证邮箱格式
    boolean checkEmailFormat(String email) {
        Pattern emailPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher = emailPattern.matcher(email);

        if (matcher.find()) {
            return true;
        }
        return false;
    }

    List<String> getAllUserName() {
        List<String> nameList = new ArrayList<>();
        for (Customer customer : customerMapper.findAll()) {
            nameList.add(customer.getUserName());
        }
        return nameList;
    }

    List<String> getAllEmailAdress() {
        List<String> emailList = new ArrayList<>();
        for (Customer customer : customerMapper.findAll()) {
            emailList.add(customer.getEmail());
        }
        return emailList;
    }

    /**
     * 验证手机号码
     *
     * @param mobileNumber
     * @return
     */
    boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */

    public String login(String userName, String password) {
        if (checkEmailFormat(userName)) {
            //邮箱登录
            if (getAllEmailAdress().contains(userName)) {
                Customer customer = getCustomerByEmail(userName);
                if (customer != null) {
                    if (password.equals(customer.getPassword())) {
                        //登录成功
                        return "success";
                    } else {
                        //密码错误
                        return "index";
                    }
                } else {
                    //该邮箱不存在
                    return "index";
                }
            }
        } else {
            //用户名登录
            if (getAllUserName().contains(userName)) {
                Customer customer = getCustomerByUserName(userName);
                if (customer != null) {
                    if (password.equals(customer.getPassword())) {
                        //登录成功
                        return "index";
                    } else {
                        //密码错误
                        return "login";
                    }
                } else {
                    //用户名不存在
                    return "login";
                }
            }
        }
        return null;
    }

    public void logout(){

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerMapper.findByUserName(username);
        if (customer == null){
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(customer.getUserName(), customer.getPassword(), emptyList());
    }
}
