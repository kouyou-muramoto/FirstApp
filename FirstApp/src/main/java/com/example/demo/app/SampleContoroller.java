package com.example.demo.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SampleContoroller {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRegistrationService userRegistrationService; 
	
	@Autowired
	private UserFindAllService userFindAllService;
	
	@GetMapping("/List")
	public String list(Model model) {
		
		List<AuthenticatedUser> allUser = userFindAllService.userFindAll(); 
		
		for (int i = 0; i <allUser.size(); i++){
			System.out.println(allUser.get(i).getId() + allUser.get(i).getUsername());
		}
		
		model.addAttribute("users",allUser);
		
		return "html/list";
	}
	
	
	@GetMapping("/login")
	public String login() {

		return "html/login";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("userRegistrationForm", new UserRegistrationForm());
		return "html/signup";
	}
	@PostMapping("/registration")
	public String resistration(@Validated @ModelAttribute UserRegistrationForm userRegisetraionForm,
								BindingResult result,
								Model model
								) {
		if(result.hasErrors()) {
			return "html/signup";
		}
		
		AuthenticatedUser user =new AuthenticatedUser();
		user.setEmail(userRegisetraionForm.getEmail());
		user.setUsername(userRegisetraionForm.getName());
		user.setPassword(passwordEncoder.encode(userRegisetraionForm.getPassword()));
		
		userRegistrationService.registerUser(user);
		
		return "html/login";
	}
	
}

