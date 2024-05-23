package fr.formation.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import fr.formation.app.dto.PersonDTO;
import fr.formation.app.services.IPersonService;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("person")
public class FormController {

	@Autowired
	private IPersonService personService;

	@GetMapping("/person")
	public String personForm(Model model) {
		model.addAttribute("person", new PersonDTO());
		return "jsp/personForm";
	}

	@PostMapping("/person")
	public String personSubmit(
			@ModelAttribute("person") @Valid PersonDTO personDTO, 
			BindingResult bindingResult,
			Model model,
			WebRequest request
		) {
		if (bindingResult.hasErrors()) {
			return "jsp/personForm";
		}
		personService.saveOrUpdate(personDTO);
		request.setAttribute("connected", true, WebRequest.SCOPE_SESSION);
		return "redirect:/showAll";
	}
	
	@GetMapping("/clearSession")
	public String clearSession(WebRequest request) {
		request.setAttribute("connected", false, WebRequest.SCOPE_SESSION);
		request.removeAttribute("person", WebRequest.SCOPE_SESSION);
		return "redirect:/person";
	}

}
