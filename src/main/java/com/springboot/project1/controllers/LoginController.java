package com.springboot.project1.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.springboot.project1.message.PrintingMessage;

import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
   
}