package fr.formation.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import fr.formation.app.dao.PersonRepository;
import fr.formation.app.dto.PersonDTO;
import fr.formation.app.models.Person;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

	@Mock
	private PersonRepository personRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@InjectMocks
	private PersonService personService;
	
	private Person person1;
	private Person person2;
	
    private PersonDTO personDTO1;
    private PersonDTO personDTO2;
	
	@BeforeEach
	void setup() {
		person1 = new Person("PRENOM1", "NOM1", 40);
		person2 = new Person("PRENOM2", "NOM2", 40);
		
		personDTO1 = new PersonDTO("PRENOM1", "NOM1", 40);
		personDTO2 = new PersonDTO("PRENOM2", "NOM2", 40);
	}
	
	@Test
	void TestFindAll() {
		when(personRepository.findAll()).thenReturn(List.of(person1, person2));
		
        when(modelMapper.map(person1, PersonDTO.class)).thenReturn(personDTO1);
        when(modelMapper.map(person2, PersonDTO.class)).thenReturn(personDTO2);

		List<PersonDTO> personDTOS = personService.findAll();
		
		assertEquals(2, personDTOS.size());
		assertEquals(person1.getFirstName(), personDTOS.get(0).getFirstName());
	}
	
	@Test
	void TestSaveOrUpdate() {		
        when(modelMapper.map(personDTO1, Person.class)).thenReturn(person1);
        when(personRepository.save(person1)).thenReturn(person1);
        when(modelMapper.map(person1, PersonDTO.class)).thenReturn(personDTO1);

		PersonDTO pDTOSaved = personService.saveOrUpdate(personDTO1);
		
		assertEquals(personDTO1, pDTOSaved);
	}
	
	@Test
	void TestFindById() {		
        when(personRepository.findById(1)).thenReturn(Optional.of(person1));
        when(modelMapper.map(person1, PersonDTO.class)).thenReturn(personDTO1);

		Optional<PersonDTO> pDTOSaved = personService.findById(1);
		
		assertTrue(pDTOSaved.isPresent());
		assertEquals(personDTO1, pDTOSaved.get());
	}
	
	
	@Test
	void TestDeleteById() {		
        doNothing().when(personRepository).deleteById(1);

		boolean isDeleted = personService.deleteById(1);
		
		assertEquals(true, isDeleted);
	}	
	
}
