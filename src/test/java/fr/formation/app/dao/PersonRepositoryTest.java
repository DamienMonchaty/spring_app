package fr.formation.app.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import fr.formation.app.models.Person;

@DataJpaTest
public class PersonRepositoryTest {
	
	@Autowired
	private PersonRepository personRepository;
	
	private Person person1;
	private Person person2;
	
	@BeforeEach
	void setup() {
		person1 = new Person("PRENOM1", "NOM1", 40);
		person2 = new Person("PRENOM2", "NOM2", 40);
	}
	
	@Test
	void testFindAll() {
		personRepository.saveAll(Arrays.asList(person1, person2));	
		assertThat(personRepository.findAll()).contains(person1, person2);
	}
	
	@Test
	void testFindByFirstNameAndLastName() {
		personRepository.save(person1);	
		
		List<Person> persons = personRepository.findByFirstNameAndLastName("PRENOM1", "NOM1");
		
		assertThat(persons).isNotEmpty();
		assertThat(persons).hasSize(1);
		assertThat(persons.get(0).getFirstName()).isEqualTo("PRENOM1");
		assertThat(persons.get(0).getLastName()).isEqualTo("NOM1");
		assertThat(persons).contains(person1);
	}
	
	@AfterEach
	void leave() {
		person1 = null;
		person2 = null;
	}
}
