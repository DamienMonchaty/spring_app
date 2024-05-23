package fr.formation.app.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ROLE")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Enumerated(EnumType.STRING)
	private RoleName title;

	public Role() {
		super();
	}

	public Role(RoleName title) {
		super();
		this.title = title;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RoleName getTitle() {
		return title;
	}

	public void setTitle(RoleName title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", title=" + title + "]";
	}

}
