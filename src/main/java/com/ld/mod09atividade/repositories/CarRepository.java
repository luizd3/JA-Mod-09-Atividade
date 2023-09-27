package com.ld.mod09atividade.repositories;

import com.ld.mod09atividade.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Async("executorRepositories")
//@Async
@Repository

public interface CarRepository extends JpaRepository<Car, Long> {

    public CompletableFuture<List<Car>> findAllBy();
    public CompletableFuture<Optional<Car>> findOneById(Long id);

//    @Async
//    @NonNull
//    <S extends Car> CompletableFuture<S> save(S car);

}
