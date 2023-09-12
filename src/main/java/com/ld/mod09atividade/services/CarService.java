package com.ld.mod09atividade.services;

import com.ld.mod09atividade.models.Car;
import com.ld.mod09atividade.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car findById(Long id) {
        return carRepository.findById(id).get();
    }

    public void save(Car car) {
        carRepository.save(car);
    }

    public void update(Car carUpdate) {
        Car carToUpdate = carRepository.findAll().stream()
                .filter(car -> car.getId().equals(carUpdate.getId()))
                .findFirst().get();
        carToUpdate.setBrand(carUpdate.getBrand());
        carToUpdate.setReleaseYear(carUpdate.getReleaseYear());
        carRepository.save(carToUpdate);
    }

    public void delete(Long id) {
        carRepository.deleteById(id);
    }

}
