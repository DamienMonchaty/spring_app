package fr.formation.app.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.formation.app.dto.PersonDTO;
import fr.formation.app.models.Car;
import fr.formation.app.services.ICarService;
import fr.formation.app.services.IPersonService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonRestController {

	@Autowired
	private IPersonService personService;
	
	@Autowired
	private ICarService carService;

	// 3 - Ecrire la requete qui retourne une liste de personnes selon un prenom et
	// un nom ->
	// http://localhost:8080/api/v1/persons?firstName=PRENOM1&lastName=NOM1

	// GET http://localhost:8080/api/v1/persons
	// GET http://localhost:8080/api/v1/persons?firstName=PRENOM1&lastName=NOM1
	@Operation(summary = "Get all persons / Get all persons by firstName and lastName")
	@GetMapping
	public ResponseEntity<List<PersonDTO>> getAll(
			@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName) {
		List<PersonDTO> persons;

		if (firstName != null || lastName != null) {
			persons = personService.findByFirstNameAndLastName(firstName, lastName);
		} else {
			persons = personService.findAll();
		}

		return new ResponseEntity<>(persons, HttpStatus.OK);
	}

	// GET http://localhost:8080/api/v1/persons/1
	@Operation(summary = "Get one person by id")
	@GetMapping("/{id}")
	public ResponseEntity<PersonDTO> getOne(@PathVariable("id") int id) {
		PersonDTO pDTO = personService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found with id " + id));
		pDTO.setAge(1500);
		return new ResponseEntity<>(pDTO, HttpStatus.OK);
	}

	// POST http://localhost:8080/api/v1/persons
	@Operation(summary = "Create one person")
	@PostMapping
	public ResponseEntity<PersonDTO> create(@RequestBody @Valid PersonDTO personDTO) {
        PersonDTO savedPerson = personService.saveOrUpdate(personDTO);
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

	// 1 - Ecrire la requete -> PUT http://localhost:8080/api/v1/persons/1
	@Operation(summary = "edit one person by id")
	@PutMapping(value = "/{id}")
	public ResponseEntity<PersonDTO> editPerson(@RequestBody @Valid PersonDTO personDto, @PathVariable("id") int id) {
		PersonDTO pToEdit = personService.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found with id : " + id));
		personDto.setId(pToEdit.getId());
		return new ResponseEntity<>(personService.saveOrUpdate(personDto), HttpStatus.OK);
	}

	// 2 - Ecrire la requete -> DELETE http://localhost:8080/api/v1/persons/1
	@Operation(summary = "Delete one person by id")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deletePerson(@PathVariable("id") int id) {
		PersonDTO pToDelete = personService.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found with id : " + id));
		personService.deleteById(pToDelete.getId());
		return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
	}

	// GET http://localhost:8080/api/v1/persons/1/cars
	@Operation(summary = "Get all cars by person id")
	@GetMapping("/{id}/cars")
	public ResponseEntity<List<Car>> getCarsByPersonId(@PathVariable("id") int id) {
		PersonDTO pDTO = personService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found with id " + id));
		List<Car> cars = carService.getCarsByPersonId(pDTO.getId());
		return new ResponseEntity<>(cars, HttpStatus.OK);
	}
}
