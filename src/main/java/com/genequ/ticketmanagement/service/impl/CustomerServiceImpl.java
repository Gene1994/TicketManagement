package com.genequ.ticketmanagement.service.impl;

import com.genequ.ticketmanagement.mapper.CustomerMapper;
import com.genequ.ticketmanagement.service.CustomerService;
import com.genequ.ticketmanagement.web.constant.ConstantKey;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.genequ.ticketmanagement.domain.Customer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.emptyList;


//实现UserDetailsService接口 使用UserDetails验证登录
@Service("Customer")
public class CustomerServiceImpl implements UserDetailsService, CustomerService {
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

    @Override
    public void regist(Customer customer) {
        if (checkUserName(customer.getUserName()) && checkEmailFormat(customer.getEmail()) && checkEmailAddress(customer.getEmail()) && checkMobileNumber(customer.getCellphone())) {
            //验证成功 添加至数据库
            customerMapper.insert(customer.getId(), customer.getUserName(), customer.getPassword(), customer.getEmail(), customer.getCellphone());
        }
    }

    /**
     * 检查数据库中是否存在用户名，存在返回false，不存在返回true。
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerMapper.findByUserName(username);
        if (customer == null){
            throw new UsernameNotFoundException(username);
        }
        //验证，获取用户后应该添加对应的权限列表 而不是emptyList()
        //再根据用户名在数据库中读取用户权限
        return new org.springframework.security.core.userdetails.User(customer.getUserName(), customer.getPassword(), emptyList());
    }

    @Override
    public void uploadAvatar(byte[] file, String filePath, String fileName) throws IOException{
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    /**
     * 根据JWT返回用户
     * @param jwt
     * @return
     */
    public Customer getCustomerByJWT(String jwt){
        if (jwt == null){
            //未登录，请先登录
            return null;
        }
        String userName = Jwts.parser()
                .setSigningKey(ConstantKey.SIGNING_KEY)
                .parseClaimsJws(jwt.replace("Bearer ", ""))
                .getBody()
                .getSubject();
        return getCustomerByUserName(userName);
    }

}
