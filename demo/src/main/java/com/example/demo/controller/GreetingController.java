// src/main/java/com/example/demo/controller/MyController.java

package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingController {

    @GetMapping("/gretting")
    public String hello() {
        return "greeting"; // Thymeleaf template name (e.g., hello.html)
    }
}