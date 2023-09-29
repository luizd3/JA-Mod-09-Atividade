package com.ld.mod09atividade.services;

import com.ld.mod09atividade.models.Car;
import com.ld.mod09atividade.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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

    public CompletableFuture<Car> save(Car car) {
        System.out.println("Service thread: " + Thread.currentThread().getName());
        return CompletableFuture.completedFuture(this.carRepository.saveAndFlush(car));
    }

    public CompletableFuture<Car> update(Car carUpdate) {
        System.out.println("Service thread: " + Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(() -> carRepository.save(carUpdate));
    }

    public CompletableFuture<Void> delete(Long id) {
        System.out.println("Service thread: " + Thread.currentThread().getName());
        return CompletableFuture.runAsync(() -> carRepository.deleteById(id));
    }

}
