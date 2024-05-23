package fr.formation.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.formation.app.services.IPersonService;
import fr.formation.app.utils.Log;

@Controller
public class PersonController {
			
	@Autowired
	private IPersonService personService;
	
	// localhost:8080/person
	@Log
	@GetMapping("/ioc")
	public String getPerson() {
		personService.getPersonFirstName();
		personService.getName("NOM1");
		String result = personService.performAction("Hello AOP");
        System.out.println(result);
        // personService.getPersonLastName();
		return "jsp/success";
	}
	
	// localhost:8080/showAll
	@GetMapping("/showAll")
	public ModelAndView showAll(ModelAndView mv) {	
		mv.addObject("persons", personService.findAll());
		mv.setViewName("jsp/persons");
		return mv;
	}
	
}
