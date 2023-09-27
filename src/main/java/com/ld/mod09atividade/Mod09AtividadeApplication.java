package com.ld.mod09atividade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Mod09AtividadeApplication {

	public static void main(String[] args) {
		SpringApplication.run(Mod09AtividadeApplication.class, args);
	}

}
