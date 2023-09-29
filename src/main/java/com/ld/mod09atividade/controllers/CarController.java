package com.ld.mod09atividade.controllers;

import com.ld.mod09atividade.models.Car;
import com.ld.mod09atividade.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Async("executorService")
@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Car>>> findAll() {
        System.out.println("Controller Thread GET: " + Thread.currentThread().getName());
        CompletableFuture<List<Car>> cars = this.carService.findAll();
        return cars.thenApply(result -> new ResponseEntity<>(result, HttpStatus.OK));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Car>> findById(@PathVariable("id") final Long id) throws ExecutionException, InterruptedException {
        System.out.println("Controller Thread GET: " + Thread.currentThread().getName());
        CompletableFuture<Optional<Car>> car = this.carService.findById(id);
        CompletableFuture<Car> carFuture = car.thenApply(result -> result.orElseThrow(RuntimeException::new));
        return carFuture.thenApply(result1 -> new ResponseEntity<>(result1, HttpStatus.OK));
    }

    @PostMapping
    public CompletableFuture<Car> save(@RequestBody final Car car) {
        System.out.println("Controller Thread POST: " + Thread.currentThread().getName());
        CompletableFuture<Car> savedCar = this.carService.save(car);
        return savedCar;
    }

    @PutMapping
    public CompletableFuture<Car> update(@RequestBody final Car car) {
        System.out.println("Controller Thread PUT: " + Thread.currentThread().getName());
        return this.carService.update(car);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity> delete(@PathVariable("id") final Long id) {
        System.out.println("Controller Thread DELETE: " + Thread.currentThread().getName());
        this.carService.delete(id);
        return CompletableFuture.completedFuture(new ResponseEntity(HttpStatus.NO_CONTENT));
    }
}
