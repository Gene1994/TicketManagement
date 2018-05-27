package pers.gene.ticketmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pers.gene.ticketmanagement.service.CustomAuthenticationProvider;
import pers.gene.ticketmanagement.service.CustomerService;
import pers.gene.ticketmanagement.web.filter.JWTAuthenticationFilter;
import pers.gene.ticketmanagement.web.filter.JWTLoginFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;
    private CustomerService customerService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //HTTP请求安全处理
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()

                .antMatchers("/index","/customer/register").permitAll()

                //以 "/admin/" 开头的URL只能让拥有 "ROLE_ADMIN"角色的用户访问。
                .antMatchers("/admin/**").hasRole("ADMIN")

                //任何以"/db/" 开头的URL需要同时具有 "ROLE_ADMIN" 和 "ROLE_DBA"权限的用户才可以访问。
                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")

                //任何以"/db/" 开头的URL只需要拥有 "ROLE_ADMIN" 和 "ROLE_DBA"其中一个权限的用户才可以访问。
                .antMatchers("/db/**").hasAnyRole("ADMIN", "DBA")

                //尚未匹配的任何URL都要求用户进行身份验证
                .anyRequest().authenticated()

                .and()
                .addFilter(new JWTLoginFilter(authenticationManager()))
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))

                .formLogin()
                .loginPage("/customer/login")
                .loginProcessingUrl("/customer/login")
                .failureUrl("/customer/fail").permitAll()
                .defaultSuccessUrl("/customer/index")

                .and()
                //开启cookie储存用户信息，并设置有效期为14天，指定cookie中的密钥
                .rememberMe().tokenValiditySeconds(1209600).key("mykey");

//                .and()
//                .logout()
                //指定登出的url
//                .logoutUrl("/api/user/logout")
                //指定登出成功之后跳转的url
//                .logoutSuccessUrl("/index")
//                .permitAll();
    }

    //身份验证管理生成器
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //并根据传入的AuthenticationManagerBuilder中的userDetailsService方法来接收我们自定义的认证方法。
        //且该方法必须要实现UserDetailsService这个接口。
        auth.userDetailsService(new CustomerService())
                //密码使用BCryptPasswordEncoder()方法验证，因为这里使用了BCryptPasswordEncoder()方法验证。所以在注册用户的时候在接收前台明文密码之后也需要使用BCryptPasswordEncoder().encode(明文密码)方法加密密码。
                .passwordEncoder(new BCryptPasswordEncoder());;


    }

    //WEB安全
    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/static/css/**", "/static/css/fronts/**", "/static/images/**","/static/js/**");
    }
}