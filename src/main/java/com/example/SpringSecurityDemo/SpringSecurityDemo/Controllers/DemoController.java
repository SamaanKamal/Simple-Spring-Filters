package com.example.SpringSecurityDemo.SpringSecurityDemo.Controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {
    @GetMapping("/")
    public String home (Authentication authentication){
        return "home";
    }
    @GetMapping("/leaders")
    public String showLeaders (){
        return "leaders";
    }
    @GetMapping("/systems")
    public String showSystems (){
        return "systems";
    }
}
