package fr.formation.app.services;

import java.util.List;

import fr.formation.app.dto.PersonDTO;

public interface IPersonService extends IService<PersonDTO> {

	void getPersonFirstName();
	void getName(String name);
	String getPersonLastName();
	String performAction(String arg);
	List<PersonDTO> findByFirstNameAndLastName(String firstName, String lastName);

}
