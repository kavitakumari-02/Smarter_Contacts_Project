package com.springboot.project1.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;

@Controller
public class GlobalController {
@ModelAttribute
	public void removeMessage(HttpSession session) {
		if(session.getAttribute("printingMsg")!=null) {
			session.removeAttribute("printingMsg");
		}
	}
}
