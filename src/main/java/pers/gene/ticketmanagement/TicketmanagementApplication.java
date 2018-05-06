package pers.gene.ticketmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
//import org.springframework.web.bind.annotation.RequestMapping;


//@MapperScan("pers.gene.ticketmanagement")
//@ComponentScan(basePackages = {"pers.gene.ticketmanagement"})


@SpringBootApplication
//使用redis实现session共享
//@EnableRedisHttpSession
public class TicketmanagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(TicketmanagementApplication.class, args);
	}
}
