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
        System.out.println("Controller Thread: " + Thread.currentThread().getName());
        CompletableFuture<List<Car>> cars = this.carService.findAll();
        return cars.thenApply(result -> new ResponseEntity<>(result, HttpStatus.OK));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Car>> findById(@PathVariable("id") final Long id) throws ExecutionException, InterruptedException {
        System.out.println("Controller Thread: " + Thread.currentThread().getName());
        CompletableFuture<Optional<Car>> car = this.carService.findById(id);
        CompletableFuture<Car> carFuture = car.thenApply(result -> result.orElseThrow(RuntimeException::new));
        return carFuture.thenApply(result1 -> new ResponseEntity<>(result1, HttpStatus.OK));
    }

    @PostMapping(consumes = "application/json;charset=UTF-8")
    public CompletableFuture saveAsync(@RequestBody final Car car) throws ExecutionException, InterruptedException {
        System.out.println("Controller Thread: " + Thread.currentThread().getName());
//        CompletableFuture<Car> future = CompletableFuture.completedFuture(carService.save(car));
//        this.carService.save(car);
//        CompletableFuture<Car> future = CompletableFuture.supplyAsync(() -> carService.save(car));
//        CompletableFuture.completedFuture(this.carService.save(car));
//        String message = "Car saved successfully.";
        return this.carService.saveAsync(car);
//        CompletableFuture<ResponseEntity<String>> retorno =
//                CompletableFuture.completedFuture(new ResponseEntity<>(message, HttpStatus.CREATED));
////        CompletableFuture.allOf(futureCar, retorno);
//        return retorno;
//        return CompletableFuture.completedFuture(new ResponseEntity<>(message, HttpStatus.CREATED));
//        return futureCar.thenApply(result -> new ResponseEntity<>(message, HttpStatus.CREATED));
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody final Car car) {
        System.out.println("Controller Thread: " + Thread.currentThread().getName());
        carService.update(car);
        String message = "Car updated successfully.";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity delete(@PathVariable("id") final Long id) {
//        System.out.println("Controller Thread: " + Thread.currentThread().getName());
//        carService.delete(id);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
}
