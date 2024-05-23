package fr.formation.app.services;

import java.util.List;

import fr.formation.app.models.Car;

public interface ICarService extends IService<Car> {

	List<Car> getCarsByPersonId(int id);
}
