package jpagroup.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HelloController {

    @GetMapping("/")
    public String home(){
        log.info("home controller");
        return "home";
    }
}
