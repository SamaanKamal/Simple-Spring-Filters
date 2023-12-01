package com.example.SpringSecurityDemo.SpringSecurityDemo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {
    @GetMapping("/")
    public String home (){
        return "home";
    }
}
