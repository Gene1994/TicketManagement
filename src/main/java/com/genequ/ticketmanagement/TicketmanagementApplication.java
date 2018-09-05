package com.genequ.ticketmanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
//import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableCaching
@MapperScan(value = "com.genequ.ticketmanagement.mapper")
public class TicketmanagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(TicketmanagementApplication.class, args);
	}
}
