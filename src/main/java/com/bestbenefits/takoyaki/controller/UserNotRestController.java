package com.bestbenefits.takoyaki.controller;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserNotRestController {
    @GetMapping("/oauth_example")
    void oauth_example(){
    }
    @GetMapping("/oauth")
    void oauth(){
    }
    @GetMapping("/nickname")
    void nickname(){
    }
}
