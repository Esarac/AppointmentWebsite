package co.edu.icesi.ci.thymeval.controller.implementation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginControllerImpl {

	//Index
	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}
	
	@GetMapping("/access-denied")
	public String accessDenied(Model model) {
		return "access-denied";
	}
	
}
