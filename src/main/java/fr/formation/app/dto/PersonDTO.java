package fr.formation.app.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

// Person -> responsable de valider les proprietes de l'object Person (AJOUT)
public class PersonDTO {

	private Integer id;
	@NotEmpty()
	@Size(min = 2, message = "FirstName doit contenir minimum 2 caractères")
	private String firstName;
	@NotEmpty()
	@Size(min = 2, message = "LastName doit contenir minimum 2 caractères")
	private String lastName;
	@Digits(integer = 3, fraction = 0, message = "age doit contenir au maximum 3 entiers")
	private int age;

	public PersonDTO() {
		super();
	}

	public PersonDTO(Integer id, String firstName, String lastName, int age) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}

	public PersonDTO(String firstName, String lastName, int age) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + "]";
	}
}
