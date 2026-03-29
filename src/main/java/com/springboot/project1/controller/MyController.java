package com.springboot.project1.controller;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.project1.dao.UserRepository;
//import com.springboot.project1.dao.UserRepository;
import com.springboot.project1.message.PrintingMessage;
import com.springboot.project1.model.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MyController {
	
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("msg", "This is the home page");
        return "Home";
    }
 @GetMapping("/register")
 public String registerPage(Model model, HttpSession session) {

     model.addAttribute("user", new User());

     session.removeAttribute("printingMessage");

     return "Register";
     }
 @Autowired
 private UserRepository userRepository;
@Autowired
 private BCryptPasswordEncoder passwordEncoder;
 @PostMapping("/register_handler")
    public String userRegisterHandler(@Valid @ModelAttribute("user") User user,BindingResult result,
    		@RequestParam(value="agree", defaultValue="false") boolean agree, Model model ,RedirectAttributes redirectAttributes) {
	 
    	 System.out.println(user);
           try {
        	   
        	 if(result.hasErrors()) {
          	   model.addAttribute("user", user);
          	   System.out.println("Error"+result.hasErrors());
          	   return "redirect:/register";
             }
          
        	user.setPassword(passwordEncoder.encode(user.getPassword()));
            model.addAttribute("user", new User());
            user.setRole("ROLE_USER");
            user.setIs_Enable(true);
            user.setImage("add1.png");
		 userRepository.save(user);
             redirectAttributes.addFlashAttribute("printingMessage",new PrintingMessage("Data entered successfully", "success"));
            return "redirect:/register";
            
         } catch(Exception e) {
        	
            e.printStackTrace();

            model.addAttribute("user", user);

            redirectAttributes.addFlashAttribute("printingMessage",new PrintingMessage("Data entry failed:" + e.getMessage(), "warning"));
            return "redirect:/register";
         }
   
 }
}