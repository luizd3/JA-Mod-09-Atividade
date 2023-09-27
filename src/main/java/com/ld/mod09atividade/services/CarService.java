package com.ld.mod09atividade.services;

import com.ld.mod09atividade.models.Car;
import com.ld.mod09atividade.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Async("executorService")
@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public CompletableFuture<List<Car>> findAll() {
        System.out.println("Service thread: " + Thread.currentThread().getName());
        return this.carRepository.findAllBy();
    }

    public CompletableFuture<Optional<Car>> findById(Long id) {
        System.out.println("Service thread: " + Thread.currentThread().getName());
        return this.carRepository.findOneById(id);
    }

    public CompletableFuture<Car> saveAsync(Car car) throws ExecutionException, InterruptedException {
        System.out.println("Service thread: " + Thread.currentThread().getName());
//        CompletableFuture<Car> futureCar = new CompletableFuture<>();
//        futureCar.complete(saveCar(car));
//        return futureCar;
//        return CompletableFuture.runAsync(() -> carRepository.save(car));
        return CompletableFuture.completedFuture(this.carRepository.save(car));
    }

    private Car saveCar(Car car) {
        return this.carRepository.save(car);
    }

    public CompletableFuture<Car> update(Car carUpdate) {
        System.out.println("Service thread: " + Thread.currentThread().getName());
        Car carToUpdate = carRepository.findAll().stream()
                .filter(car -> car.getId().equals(carUpdate.getId()))
                .findFirst().get();
        carToUpdate.setBrand(carUpdate.getBrand());
        carToUpdate.setReleaseYear(carUpdate.getReleaseYear());
        return CompletableFuture.supplyAsync(() -> carRepository.save(carToUpdate));
    }

//    public CompletableFuture delete(Long id) {
//        System.out.println("Service thread: " + Thread.currentThread().getName());
//        return CompletableFuture.runAsync(carRepository.deleteById(id));
//    }

}
