package pers.gene.ticketmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;

//@MapperScan("pers.gene.ticketmanagement")
//@ComponentScan(basePackages = {"pers.gene.ticketmanagement"})



@SpringBootApplication
//使用redis实现session共享
//@EnableRedisHttpSession
public class TicketmanagementApplication {
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@RequestMapping("/index")
	public String index(){
		return "index";
	}

	public static void main(String[] args) {
		SpringApplication.run(TicketmanagementApplication.class, args);
	}
}
