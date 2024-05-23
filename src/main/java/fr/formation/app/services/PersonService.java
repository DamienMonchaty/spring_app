package fr.formation.app.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.formation.app.dao.PersonRepository;
import fr.formation.app.dto.PersonDTO;
import fr.formation.app.models.Person;

@Service
public class PersonService implements IPersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	@Qualifier("person")
	private Person person;
	
	@Override
	public void getPersonFirstName() {
		System.out.println(person.getFirstName());
	};
	
	@Override
	public void getName(String name) {
		System.out.println(name);
	};
	
	@Override
	public String getPersonLastName() {
        throw new RuntimeException("Something went wrong!");
    }
	
	@Override
    public String performAction(String arg) {
        return "Action performed with argument: " + arg;
    }

	@Override
	public List<PersonDTO> findAll() {	
		return personRepository.findAll()
				.stream()
				.map(p -> modelMapper.map(p, PersonDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public PersonDTO saveOrUpdate(PersonDTO o) {
		Person pToSave = modelMapper.map(o, Person.class);
		Person pSaved = personRepository.save(pToSave);
		return modelMapper.map(pSaved, PersonDTO.class);
	}

	@Override
	public Optional<PersonDTO> findById(int id) {
		Optional<Person> pOptional = personRepository.findById(id);
		return pOptional.map(p -> modelMapper.map(p, PersonDTO.class));
	}

	@Override
	public boolean deleteById(int id) {
		personRepository.deleteById(id);
		return true;
	}

	@Override
	public List<PersonDTO> findByFirstNameAndLastName(String firstName, String lastName) {
		return personRepository.findByFirstNameAndLastName(firstName, lastName)
				.stream()
				.map(p -> modelMapper.map(p, PersonDTO.class))
				.collect(Collectors.toList());
	}
	
}
