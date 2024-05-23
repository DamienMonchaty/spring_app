package fr.formation.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.formation.app.models.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer>  {

	List<Car> findCarsByPersonId(int id);
}
