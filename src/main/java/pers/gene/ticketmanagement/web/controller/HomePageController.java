package pers.gene.ticketmanagement.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
