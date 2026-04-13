package com.example.etudiants;

import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.entity.Departement;
import com.example.etudiants.repository.EtudiantRepository;
import com.example.etudiants.repository.DepartementRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;

@SpringBootApplication
@EnableCaching
public class EtudiantsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EtudiantsApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(EtudiantRepository etudiantRepo, DepartementRepository departementRepo) {
        return args -> {
            // Créer les départements d'abord
            if (departementRepo.count() == 0) {
                Departement info = new Departement();
                info.setNom("Informatique");
                departementRepo.save(info);

                Departement civil = new Departement();
                civil.setNom("Civil");
                departementRepo.save(civil);

                Departement mecanique = new Departement();
                mecanique.setNom("Mecanique");
                departementRepo.save(mecanique);

                Departement electrique = new Departement();
                electrique.setNom("Electrique");
                departementRepo.save(electrique);
            }

            // Créer les étudiants
            if (etudiantRepo.count() == 0) {
                Departement info = departementRepo.findAll().stream()
                    .filter(d -> d.getNom().equals("Informatique"))
                    .findFirst()
                    .orElse(null);

                Etudiant e1 = new Etudiant();
                e1.setCin("12345678");
                e1.setNom("Ali Ben Salah");
                e1.setDateNaissance(LocalDate.of(2000, 3, 15));
                e1.setEmail("ali@example.com");
                e1.setAnneePremiereInscription(2020);
                e1.setDepartement(info);
                etudiantRepo.save(e1);

                Etudiant e2 = new Etudiant();
                e2.setCin("23456789");
                e2.setNom("Sana Trabelsi");
                e2.setDateNaissance(LocalDate.of(2001, 7, 22));
                e2.setEmail("sana@example.com");
                e2.setAnneePremiereInscription(2021);
                e2.setDepartement(info);
                etudiantRepo.save(e2);

                Etudiant e3 = new Etudiant();
                e3.setCin("34567890");
                e3.setNom("Mohamed Karim");
                e3.setDateNaissance(LocalDate.of(1999, 11, 5));
                e3.setEmail("mohamed@example.com");
                e3.setAnneePremiereInscription(2019);
                e3.setDepartement(info);
                etudiantRepo.save(e3);

                Etudiant e4 = new Etudiant();
                e4.setCin("45678901");
                e4.setNom("Rim Bouaziz");
                e4.setDateNaissance(LocalDate.of(2002, 1, 30));
                e4.setEmail("rim@example.com");
                e4.setAnneePremiereInscription(2022);
                e4.setDepartement(info);
                etudiantRepo.save(e4);

                Etudiant e5 = new Etudiant();
                e5.setCin("56789012");
                e5.setNom("Yassine Hamdi");
                e5.setDateNaissance(LocalDate.of(2000, 9, 18));
                e5.setEmail("yassine@example.com");
                e5.setAnneePremiereInscription(2020);
                e5.setDepartement(info);
                etudiantRepo.save(e5);
            }
        };
    }
}
