package co.edu.icesi.ci.thymeval.controller.implementation;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.ci.thymeval.controller.interfaces.AppointmentController;
import co.edu.icesi.ci.thymeval.model.Appointment;
import co.edu.icesi.ci.thymeval.service.interfaces.AppointmentService;
import co.edu.icesi.ci.thymeval.service.interfaces.UserService;

@Controller
@RequestMapping("/apps/")
public class AppointmentControllerImpl implements AppointmentController {

	//Attributes
	AppointmentService appointmentService;
	UserService userService;

	//Constructor
	@Autowired
	public AppointmentControllerImpl(AppointmentService appointmentService, UserService userService) {
		this.userService = userService;
		this.appointmentService = appointmentService;
	}

	//Method
	public void generateComboBox(Model model) {
		model.addAttribute("doctors", userService.findAllDoctors());
		model.addAttribute("patients", userService.findAllPatients());
	}
	
	//Index
	@GetMapping("/")
	public String indexApp(Model model) {
		model.addAttribute("appointments", appointmentService.findAll());
		return "apps/index";
	}
	
	//Add
	@GetMapping("/add")
	public String addApp(Model model) {
		model.addAttribute("appointment", new Appointment());
		generateComboBox(model);
		return "apps/add-app";
	}
	
	@PostMapping("/add")
	public String saveApp(@Valid @ModelAttribute Appointment app, BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {
		String dir = "redirect:/apps/";
		
		if (!action.equals("Cancel")) {
			if(!bindingResult.hasErrors()) {
				appointmentService.save(app);
			}
			else {
				generateComboBox(model);
				dir = "apps/add-app";
			}
		}
			
		return dir;
	}

	//Id
	@GetMapping("/edit/{id}")
	public String showUpdateApp(@PathVariable("id") long id, Model model) {
		Optional<Appointment> app = appointmentService.findById(id);
		if (app == null)
			throw new IllegalArgumentException("Invalid appointment Id:" + id);
		model.addAttribute("appointment", app.get());
		generateComboBox(model);
		return "apps/update-app";
	}

	@PostMapping("/edit/{id}")
	public String updateApp(@Valid @ModelAttribute Appointment app, BindingResult bindingResult, Model model, @PathVariable("id") long id, @RequestParam(value = "action", required = true) String action) {
		String dir = "redirect:/apps/";
		
		if (!action.equals("Cancel")) {
			if(!bindingResult.hasErrors()) {
				appointmentService.save(app);
				model.addAttribute("apps", appointmentService.findAll());
			}
			else {
				model.addAttribute("appointment", app);
				generateComboBox(model);
				dir = "apps/update-app";
			}
		}
		return dir;
	}
	
	//Delete
	@GetMapping("/del/{id}")
	public String deleteApp(@PathVariable("id") long id, Model model) {
		Appointment app = appointmentService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		appointmentService.delete(app);
		model.addAttribute("users", appointmentService.findAll());
		return "apps/index";
	}
}
