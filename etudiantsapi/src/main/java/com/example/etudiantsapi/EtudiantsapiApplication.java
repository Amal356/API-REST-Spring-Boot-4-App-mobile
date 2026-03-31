package com.example.etudiantsapi;

import com.example.etudiantsapi.model.Etudiant;
import com.example.etudiantsapi.repository.EtudiantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;

@SpringBootApplication
public class EtudiantsapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtudiantsapiApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(EtudiantRepository repo) {
		return args -> {
			if (repo.count() == 0) {
				repo.save(new Etudiant(null, "12345678", "Ali Ben Salah", LocalDate.of(2000, 3, 15)));
				repo.save(new Etudiant(null, "23456789", "Sana Trabelsi", LocalDate.of(2001, 7, 22)));
				repo.save(new Etudiant(null, "34567890", "Mohamed Karim", LocalDate.of(1999, 11, 5)));
				repo.save(new Etudiant(null, "45678901", "Rim Bouaziz", LocalDate.of(2002, 1, 30)));
				repo.save(new Etudiant(null, "56789012", "Yassine Hamdi", LocalDate.of(2000, 9, 18)));
			}
		};
	}
}