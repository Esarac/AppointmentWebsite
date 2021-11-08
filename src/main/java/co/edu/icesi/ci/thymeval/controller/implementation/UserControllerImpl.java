package co.edu.icesi.ci.thymeval.controller.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.ci.thymeval.controller.interfaces.UserController;
import co.edu.icesi.ci.thymeval.model.UserApp;
import co.edu.icesi.ci.thymeval.service.UserServiceImpl;
import co.edu.icesi.ci.thymeval.validation.Add1;
import co.edu.icesi.ci.thymeval.validation.Add2;
import co.edu.icesi.ci.thymeval.validation.Edit;

@Controller
@RequestMapping("/users/")
public class UserControllerImpl implements UserController {

	//Attributes
	UserServiceImpl userService;

	//Constructor
	@Autowired
	public UserControllerImpl(UserServiceImpl userService) {
		this.userService = userService;
		;
	}
	
	//~Methods
	public boolean validatePasswordConfirmation(String password, String passwordConfirm) {
		return !passwordConfirm.equals(password);
	}
	
	//Model
	public void generateComboBox(Model model) {
		model.addAttribute("genders", userService.getGenders());
		model.addAttribute("types", userService.getTypes());
	}
	
	//Password
	public void generatePasswordConfirmation(Model model, Boolean error) {
		model.addAttribute("passwordConfirm", "");
		model.addAttribute("passwordConfirmError", error);
	}
	//...
	
	//~Mapping
	//Index
	@GetMapping("/")
	public String indexUser(Model model) {
		model.addAttribute("users", userService.findAll());
		return "users/index";
	}
	
	//Add1
	@GetMapping("/add/")
	public String addUser(Model model) {
		model.addAttribute("userApp", new UserApp());
		return "users/add-user1";
	}
	
	@PostMapping("/add/")
	public String saveUser1(@Validated(Add1.class) @ModelAttribute UserApp userApp, BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {
		String dir = "redirect:/users/";
		
		if (!action.equals("Cancel")) {
			if(!bindingResult.hasErrors()) {
				model.addAttribute("userApp",userApp);
				generateComboBox(model);
				dir = "users/add-user2";
			}
			else {
				generateComboBox(model);
				dir = "users/add-user1";
			}
		}
		
		return dir;
	}
	
	@PostMapping("/add/2")//Cambiar Link
	public String saveUser2(@Validated(Add2.class) @ModelAttribute UserApp userApp, BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {
		String dir = "redirect:/users/";
		
		if (!action.equals("Cancel")) {
			if(!bindingResult.hasErrors()) {
				userService.save(userApp);
			}
			else {
				generateComboBox(model);
				dir = "users/add-user2";
			}
		}
		
		return dir;
	}
	
	//Edit
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Optional<UserApp> userApp = userService.findById(id);
		if (userApp == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);
		model.addAttribute("userApp", userApp.get());
		generateComboBox(model);
		generatePasswordConfirmation(model, false);
		return "users/update-user";
	}

	@PostMapping("/edit/{id}")
	public String updateUser(@Validated(Edit.class) @ModelAttribute UserApp userApp, BindingResult bindingResult, String passwordConfirm, Model model, @PathVariable("id") long id, @RequestParam(value = "action", required = true) String action) {
		String dir = "redirect:/users/";
		
		if (action != null && !action.equals("Cancel")) {
			
			boolean passwordConfirmation = validatePasswordConfirmation(userApp.getPassword(), passwordConfirm);
			if(bindingResult.hasErrors() || passwordConfirmation) {
				model.addAttribute("userApp", userApp);
				generateComboBox(model);
				dir = "users/update-user";
				generatePasswordConfirmation(model, passwordConfirmation);
			}
			else {
				if(userApp.getPassword().isBlank()){
					userApp.setPassword(userService.findById(userApp.getId()).get().getPassword());
				}
				userService.save(userApp);
				model.addAttribute("users", userService.findAll());
			}
			
		}
		
		return dir;
	}
	
	//Delete
	@GetMapping("/del/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		UserApp userApp = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userService.delete(userApp);
		model.addAttribute("users", userService.findAll());
		return "users/index";
	}
}
