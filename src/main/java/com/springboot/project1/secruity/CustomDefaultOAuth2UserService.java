package com.springboot.project1.secruity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.springboot.project1.model.User;
import com.springboot.project1.repository.UserRepository;
@Repository
public class CustomDefaultOAuth2UserService extends DefaultOAuth2UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
		
		OAuth2User oAuth2User=super.loadUser(oAuth2UserRequest);
	    String name1 = oAuth2User.getName();
	    String email = oAuth2User.getAttribute("email");
	    String name = oAuth2User.getAttribute("name");
	    String image = oAuth2User.getAttribute("picture");
	    System.out.println(email);
	    System.out.println(name);
	    System.out.println(name1);
		User user = userRepository.findByEmail(email);
		if(user!=null) {
			
			 System.out.println("Save in database");
		}
		else {
			User newUser=new User();
			newUser.setEmail(email);
			newUser.setName(name);
			newUser.setImage(image);
			newUser.setRole("ROLE_USER");
			newUser.setAbout("This is me");
			newUser.setAgree(true);
			newUser.setPassword("kavi");
			newUser.setIs_Enable(true);
			userRepository.save(newUser);
			System.out.println(" SuccessFully save");
		}
	return oAuth2User;	
	}

}
