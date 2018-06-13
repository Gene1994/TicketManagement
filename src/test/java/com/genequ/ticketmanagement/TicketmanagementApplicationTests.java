package com.genequ.ticketmanagement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketmanagementApplicationTests {

	@Autowired
	EmailService emailService;

	@Test
	public void main(String[] args){
		emailService.sendSimpleMail("quzhe@hikvision.com","test","simple test");
	}

}
