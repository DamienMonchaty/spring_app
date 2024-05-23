package fr.formation.app.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
// @RequestMapping(value = "/home")
public class HomeController {
	
	// Parametres de requetes
	
	// http://localhost:8080/home
	// http://localhost:8080/home?firstName=Steve
	// http://localhost:8080/home?lastName=Rogers
	// http://localhost:8080/home?firstName=Steve&lastName=Rogers
	@GetMapping("/home")
	public ModelAndView goToHome(
			@RequestParam(value = "lastName", required = false, defaultValue = "DEFAULT") String lastName,
			@RequestParam(value = "firstName", required = false, defaultValue = "DEFAULT") String firstName,
			ModelAndView modelAndView) {	
		modelAndView.addObject("lastName", lastName);
		modelAndView.addObject("firstName", firstName);
		modelAndView.setViewName("jsp/home");
		return modelAndView;
	}
	
	// Variables de chemin
	
	// http://localhost:8080/home2
	// http://localhost:8080/home2/prenom2
	@GetMapping(value = { "/home2/{firstName}", "/home2" })
	public ModelAndView goToHome(
			@PathVariable Optional<String> firstName,
			ModelAndView modelAndView) {	
		if (firstName.isPresent()) {
			modelAndView.addObject("firstName", firstName.get());
		} else {
			modelAndView.addObject("firstName", "Gandalf");
		}
		modelAndView.setViewName("jsp/home");
		return modelAndView;
	}
}
