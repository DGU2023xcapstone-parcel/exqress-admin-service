package com.exqress.adminservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class BasicController {
    @GetMapping("/")
    public String basicScreen(){
        return "basic";
    }
}
