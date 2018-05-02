package pers.gene.ticketmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;


//@MapperScan("pers.gene.ticketmanagement")
//@ComponentScan(basePackages = {"pers.gene.ticketmanagement"})


@SpringBootApplication
public class TicketmanagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(TicketmanagementApplication.class, args);
	}
}
