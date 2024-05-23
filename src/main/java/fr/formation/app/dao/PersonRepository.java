package fr.formation.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.formation.app.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

	List<Person> findByFirstNameAndLastName(String firstName, String lastName);
	
	@Query("SELECT p FROM Person p WHERE p.firstName = ?1 AND p.lastName = ?2")
	List<Person> findByPrenomAndNom(String firstName, String lastName);
}
