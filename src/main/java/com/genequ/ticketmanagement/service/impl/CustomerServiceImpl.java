package com.genequ.ticketmanagement.service.impl;

import com.genequ.ticketmanagement.domain.Customer;
import com.genequ.ticketmanagement.exception.RegisterException;
import com.genequ.ticketmanagement.mapper.CustomerMapper;
import com.genequ.ticketmanagement.service.CustomerService;
import com.genequ.ticketmanagement.web.constant.ConstantKey;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
    public boolean register(Customer customer) throws RegisterException {
        if (!checkUserName(customer.getUserName())) {
            throw new RegisterException("用户已存在", 1);
        }
        if (!checkEmailAddress(customer.getEmail())) {
            throw new RegisterException("邮箱已存在", 2);
        }
        if (!checkEmailFormat(customer.getEmail())) {
            throw new RegisterException("邮箱格式不正确", 3);
        }
        if (!checkCellphoneNumber(customer.getCellphone())) {
            throw new RegisterException("手机格式不正确", 4);
        }
        customerMapper.insert(customer.getId(), customer.getUserName(), customer.getPassword(), customer.getEmail(), customer.getCellphone());
        return true;
    }

    /**
     * 检查数据库中是否存在用户名
     * @param userNameSignup
     * @return
     */
    boolean checkUserName(String userNameSignup) {
        if (getAllUserName().contains(userNameSignup)) return false;
        return true;
    }

    /**
     * 检查数据库中是否存在重复邮箱名
     * @param email
     * @return
     */
    boolean checkEmailAddress(String email) {
        if (getAllEmailAdress().contains(email)) return false;
        return true;
    }

    /**
     * 验证邮箱格式
     * @param email
     * @return
     */
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
     * 验证手机号码格式
     * @param mobileNumber
     * @return
     */
    boolean checkCellphoneNumber(String mobileNumber) {
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
        if (customer == null) {
            throw new UsernameNotFoundException(username);
        }
        //验证，获取用户后应该添加对应的权限列表 而不是emptyList()
        //再根据用户名在数据库中读取用户权限
        return new org.springframework.security.core.userdetails.User(customer.getUserName(), customer.getPassword(), emptyList());
    }

    @Override
    public boolean uploadAvatar(Customer customer, MultipartFile avatar) throws IOException {
//        String contentType = file.getContentType();
        String fileName = new Date().getTime() + "_" + avatar.getOriginalFilename();//文件名+上传时的时间戳 避免重名
//        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        String filePath = "D:\\TicketManagement\\customer\\avatar\\";//头像保存路径
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(avatar.getBytes());
        out.flush();
        out.close();
        customer.setAvatarUrl(filePath + fileName);
        customerMapper.setAvatarUrl(customer.getId(), filePath + fileName);
        return true;
    }


    /**
     * 根据JWT返回用户
     *
     * @param jwt
     * @return
     */
    public Customer getCustomerByJWT(String jwt) {
        if (jwt == null) {
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
