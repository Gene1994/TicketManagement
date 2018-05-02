package pers.gene.ticketmanagement.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  //注意这里必须为Controller
public class TempController {

    /**
     * 本地访问内容地址 ：http://localhost:8080/lmycc/hello
     *
     * @param map
     * @return
     */
    @RequestMapping("/hello")
    public String testF2F() {
        return "index";

    }
}