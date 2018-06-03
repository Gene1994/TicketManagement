package com.genequ.ticketmanagement.service;

import com.genequ.ticketmanagement.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;

/**
 * 认证：用户登录后，接下来一段时间不用重复登录
 * 只有在login时候使用到了该类
 * 获取用户名密码和数据库中进行比较 正确，赋予权限。
 */
public class CustomerAuthenticationProvider implements AuthenticationProvider {
//    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private CustomerService customerService;

    public CustomerAuthenticationProvider(CustomerService customerService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.customerService = customerService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * 自定义认证过程
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取认证的用户名 & 密码
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        // 认证逻辑
//        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        Customer customer = customerService.getCustomerByUserName(username);
        if(null != customer){
            String encodePassword = DigestUtils.md5DigestAsHex((password).getBytes());
            if(customer.getPassword().equals(encodePassword)){
                // 这里设置权限和角色
                ArrayList<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add( new GrantedAuthorityImpl("ROLE_ADMIN") );
                authorities.add( new GrantedAuthorityImpl("AUTH_WRITE") );
                // 生成令牌 这里令牌里面存入了:name,password,authorities, 当然你也可以放其他内容
                Authentication auth = new UsernamePasswordAuthenticationToken(username, password, authorities);
                return auth;
            }else {
                throw new BadCredentialsException("密码错误");
            }
        }else {
            throw new UsernameNotFoundException("用户不存在");
        }
    }

    /**
     * 是否可以提供输入类型的认证服务
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
