package fr.formation.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.formation.app.services.IPersonService;

@Controller
public class ThymeleafController {

	@Autowired
	private IPersonService personService;
	
	@GetMapping("/thymeleaf")
	public String show(Model model) {
		model.addAttribute("message", "salut");
		model.addAttribute("person", personService.findById(1).orElse(null));
		model.addAttribute("persons", personService.findAll());
		return "thymeleaf/index";
	}
	
}
