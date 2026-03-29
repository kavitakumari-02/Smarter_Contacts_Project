package com.springboot.project1.user;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.springboot.project1.SmartcontactApplication;
import com.springboot.project1.dao.ContactRepository;
import com.springboot.project1.dao.UserRepository;
import com.springboot.project1.message.PrintingMessage;
import com.springboot.project1.model.Contact;
import com.springboot.project1.model.User;
import com.springboot.project1.secruity.UserDetailsServiceImp;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserDetailsServiceImp userDetailsServiceImp;

    private final SmartcontactApplication smartcontactApplication;

    @Autowired
    private UserRepository userRepository;

    UserController(SmartcontactApplication smartcontactApplication, UserDetailsServiceImp userDetailsServiceImp) {
        this.smartcontactApplication = smartcontactApplication;
        this.userDetailsServiceImp = userDetailsServiceImp;
    }
    @ModelAttribute
	 public void setUser(Principal principal,Model model) {
		String username = principal.getName();
		User user = userRepository.findByEmail(username);
		model.addAttribute("user", user);
	 }

   @Autowired
 private ContactRepository contactRepository;


    // Dashboard
    @GetMapping("/dashboardPage")
    public String hello(Model model, Principal principal) {

        String userName = principal.getName();

        User user = userRepository.findByEmail(userName);

        model.addAttribute("name", user);

        return "Dashboard/index";
    }


    // Open Add Contact Page
    @GetMapping("/add_contact")
    public String addContacts(Model model) {

        model.addAttribute("contact", new Contact());

        return "Dashboard/Add_Contact";
    }

    @PostMapping("/process-contact")
    public String process_contact(
    		@Valid
            @ModelAttribute Contact contact,BindingResult result,
            @RequestParam("profileImage")MultipartFile file,
            Principal principal,HttpSession session,Model model) {

    	if(result.hasErrors()) {
    		model.addAttribute("contact", contact);
    		return "Dashboard/Add_Contact";
    	}
        try {
        	 String name = principal.getName();

             User user = userRepository.findByEmail(name);

        	if(file.isEmpty()) {
         System.out.println("File is empty " );
         contact.setImage("default.png");
        	}
        	else {
        		
				String originalFilename = file.getOriginalFilename();
				contact.setImage(originalFilename);
				 File savedFile = new ClassPathResource("/static/images").getFile();
				
				Path path= Paths.get(savedFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
      Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        	}
        	
            contact.setUser(user);

            user.getContacts().add(contact);

            userRepository.save(user);
        
           session.setAttribute("printingMsg", new PrintingMessage("Data Enter SuccessFully", "success"));
           model.addAttribute("contact", new Contact());
        	
            System.out.println("Contact Saved: " + contact);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("printingMsg", new PrintingMessage("Data Entery Failed", "danger"));
        }

        return "Dashboard/Add_Contact";
}
    @GetMapping("/show_contact/{page}")
public String show_contact(@PathVariable("page")int page, Model model,Principal principal) {
	model.addAttribute("title", "Smart Contact");
	String name = principal.getName();
	
	User user = userRepository.findByEmail(name);
	Pageable pageable = PageRequest.of(page, 4);
	Page<Contact> contactsAll = contactRepository.findByUserId(user.getId(),pageable);
	System.out.println("all contact data"+contactsAll);
	
	model.addAttribute("contact", contactsAll);
	model.addAttribute("currentPage", page);
	model.addAttribute("totalPage", contactsAll.getTotalPages());
	
	return "Dashboard/Show_Contact";
}
    @RequestMapping("/contact/{id}")
 public String showUserDetail(@PathVariable("id")int id,Model model,Principal principal) {
	// System.out.println("CID= "+id);
	  Contact contact = contactRepository.findById(id).get();
	 //System.out.println("Single Detail "+contact);
	
	 String name = principal.getName();
	User user = userRepository.findByEmail(name);
	 if(user==contact.getUser()) {
		 model.addAttribute("contact", contact);	 
	 }
	  return "Dashboard/Show_User_Detail";
 }

   @GetMapping("/delete-contact/{id}") 
  public String deleteContact(@PathVariable("id") int id,Principal principal) {
	   Contact contact = contactRepository.findById(id).get();

	    

	        String name = principal.getName();
	        User user = userRepository.findByEmail(name);

	       
	     if (user!= null && contact.getUser()!=null &&
	      contact.getUser().getId()==user.getId()) {
	    	
	            contactRepository.deleteByUserContact(id, user.getId());
	        }

	    return "redirect:/user/show_contact/0";
  }
    
   
   @PostMapping("/update-contact/{id}") 
   public String updateContact(@PathVariable("id") int id,Model model) {
 	   Contact contact = contactRepository.findById(id).get();
 	 model.addAttribute("contact", contact);
     
    
    return "Dashboard/Update-Contact";
    }
   @SuppressWarnings("deprecation")
@PostMapping("/update-contactdata") 
   public String updateContactData(@ModelAttribute("contact") Contact contact,Principal principal,@RequestParam("profileImage")MultipartFile file,HttpSession session) {
	try {
		 Contact contactOldId = contactRepository.findById(contact.getId()).get();
	   if(!file.isEmpty()) { 
		   String originalFilename = file.getOriginalFilename();
			contact.setImage(originalFilename);
			 File savedFile = new ClassPathResource("/static/images").getFile();
			
			Path path= Paths.get(savedFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
 Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
 contact.setImage(file.getOriginalFilename());
	   }
	   else {
		   contact.setImage(contactOldId.getImage());
		   
	   }
	   String name = principal.getName(); 
		 User user = userRepository.findByEmail(name);
		 contact.setUser(user);
		contactRepository.save(contact);
		session.setAttribute("message",new PrintingMessage("Data Update successfully.....", "success"));
	 }catch(Exception e) {
		 session.setAttribute("message",new PrintingMessage("Data Update Failed.......", "danger"));	 
	e.printStackTrace();	 
	 }
	   return "redirect:/user/contact/"+contact.getId();
   }
   @GetMapping("/profile")
   public String profile(Principal principal,Model model) {
	   model.addAttribute("title", "User Profile");
	   String name = principal.getName();
	   User user = userRepository.findByEmail(name);
	   model.addAttribute("user", user);
	   return"Dashboard/UserProfile";
   }
   @GetMapping("/setting")
   public String settings() {
   
   return "Dashboard/Settings";
   }
   @PostMapping("/update-profile")
   public String updateSetting(Principal principal,Model model,
		   @RequestParam("name")String name,
		   @RequestParam("email")String email,
		   @RequestParam("about")String about) {
	   String username = principal.getName();
	   User user = userRepository.findByEmail(username);
	   model.addAttribute("user", user);
	   user.setName(name);
	   user.setEmail(email);
	   user.setAbout(about);
	   userRepository.save(user);
   return "Dashboard/Settings";
   } 
   @Autowired
   private BCryptPasswordEncoder passwordEncoder;
   @PostMapping("/change-password")
   public String changePassword(Principal principal,Model model,
		   @RequestParam("newPassword")String newPassword,
		   @RequestParam("oldPassword")String oldPassword) {
	   String username = principal.getName();
	   User user = userRepository.findByEmail(username);
	  if(user.getPassword().equals(oldPassword)) {
		model.addAttribute("msg", "Old password incorrect....");
		return "Dashboard/Settings";
	  }
	  user.setPassword(passwordEncoder.encode(newPassword));
	   userRepository.save(user);
	   return "Dashboard/Settings";  
   }
   @PostMapping("/delete-account")
   public String deleteAccount(Principal principal) {

       User user = userRepository.findByEmail(principal.getName());
       userRepository.delete(user);

       return "redirect:/login?logout";
   }

   
   
   
   
   
}