package fr.formation.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.app.dao.CarRepository;
import fr.formation.app.models.Car;

@Service
public class CarService implements ICarService {
	
	@Autowired
	private CarRepository carRepository;

	@Override
	public List<Car> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Car saveOrUpdate(Car o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Car> findById(int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public boolean deleteById(int id) {
		return false;		
	}

	@Override
	public List<Car> getCarsByPersonId(int id) {
		return carRepository.findCarsByPersonId(id);
	}

}
