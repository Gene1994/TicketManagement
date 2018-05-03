package pers.gene.ticketmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.gene.ticketmanagement.repository.CustomerMapper;
import pers.gene.ticketmanagement.domain.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CustomerService {

    //自动绑定mapper
    @Autowired
    private CustomerMapper customerMapper;
//    查询数据
    public List<Customer> getCustomerList()
    {
        return customerMapper.findAll();
    }

    public Customer getCustomerById(String id)
    {
        return customerMapper.findById(id);
    }


    public void regist(Customer customer){
        if (checkUserName(customer.getUserName()) && checkEmail(customer.getEmail()) && checkMobileNumber(customer.getCellphone())){
            //验证成功 添加至数据库
            customerMapper.insert(customer.getId(), customer.getUserName(), customer.getPassword(), customer.getEmail(), customer.getCellphone(), customer.getCheckin(), customer.getCheckout(), customer.getStartTime(), customer.getEndTime());
        }
    }

    /**
     * 检查数据库中是否存在用户名，存在返回false，不存在返回true。
     * @param userNameSignup
     * @return
     */
    boolean checkUserName(String userNameSignup){
        if (getAllUserName().contains(userNameSignup)) return false;
        return true;
    }

    List<String> getAllUserName(){
        List<String> nameList = new ArrayList<>();
        for (Customer customer : customerMapper.findAll()){
            nameList.add(customer.getUserName());
        }
        return nameList;
    }

    //验证邮箱格式
    boolean checkEmail(String email){
        Pattern emailPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher = emailPattern.matcher(email);
        if(matcher.find()){
            return true;
        }
        return false;
    }

    /**
     * 验证手机号码
     * @param mobileNumber
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber){
        boolean flag = false;
        try{
            Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }

    public void login(){

    }
    public void logout(){

    }
}
