package fr.formation.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CAR")
public class Car {

	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	@Column(name = "TYPE")
	private String type;
	@Column(name = "BRAND")
	private String brand;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "PERSON_ID", referencedColumnName = "id")
	private Person person;

	public Car() {
		super();
	}

	public Car(String type, String brand, Person person) {
		super();
		this.type = type;
		this.brand = brand;
		this.person = person;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", type=" + type + ", brand=" + brand + ", person=" + person + "]";
	}

}
