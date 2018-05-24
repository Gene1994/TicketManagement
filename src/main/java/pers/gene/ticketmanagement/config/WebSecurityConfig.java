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
import pers.gene.ticketmanagement.web.filter.JWTAuthenticationFilter;
import pers.gene.ticketmanagement.web.filter.JWTLoginFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .anyRequest().authenticated()
                .antMatchers("/index","/customer/register").permitAll()
                .and()
                .addFilter(new JWTLoginFilter(authenticationManager()))
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .formLogin()
                .loginPage("/customer/login").permitAll()
                .loginProcessingUrl("/customer/login")
                .failureUrl("/customer/fail")
                .defaultSuccessUrl("/customer/index");
//                .and()
//                .rememberMe().tokenValiditySeconds(1209600).key("mykey")
//                .and()
//                .logout()
                //指定登出的url
//                .logoutUrl("/api/user/logout")
                //指定登出成功之后跳转的url
//                .logoutSuccessUrl("/index")
//                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //并根据传入的AuthenticationManagerBuilder中的userDetailsService方法来接收我们自定义的认证方法。
        //且该方法必须要实现UserDetailsService这个接口。
        auth.authenticationProvider(new CustomAuthenticationProvider(userDetailsService,bCryptPasswordEncoder));

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/static/css/**", "/static/css/fronts/**", "/static/images/**","/static/js/**");
    }
}