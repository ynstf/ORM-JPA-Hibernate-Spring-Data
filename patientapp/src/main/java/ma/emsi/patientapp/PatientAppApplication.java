//// V1
//package ma.emsi.patientapp;
//
//import ma.emsi.patientapp.entities.Patient;
//import ma.emsi.patientapp.repositories.PatientRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//
//import java.util.Date;
//import java.util.List;
//
//@SpringBootApplication
//public class PatientAppApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(PatientAppApplication.class, args);
//    }
//
//    @Bean
//    CommandLineRunner start(PatientRepository patientRepository) {
//        return args -> {
//            System.out.println("========== Starting Patient Management Application ==========");
//
//            // 1. Add patients
//            System.out.println("----------- Adding Patients -----------");
//            Patient patient1 = new Patient(null, "Hassan", new Date(), true, 75);
//            Patient patient2 = new Patient(null, "Mohammed", new Date(), false, 50);
//            Patient patient3 = new Patient(null, "Yasmine", new Date(), true, 90);
//
//            patientRepository.save(patient1);
//            patientRepository.save(patient2);
//            patientRepository.save(patient3);
//
//            // 2. Find all patients
//            System.out.println("----------- All Patients -----------");
//            List<Patient> patients = patientRepository.findAll();
//            patients.forEach(p -> {
//                System.out.println("ID: " + p.getId());
//                System.out.println("Nom: " + p.getNom());
//                System.out.println("Date naissance: " + p.getDateNaissance());
//                System.out.println("Malade: " + p.isMalade());
//                System.out.println("Score: " + p.getScore());
//                System.out.println("----------------------------------");
//            });
//
//            // 3. Find patient by ID
//            System.out.println("----------- Find Patient By ID -----------");
//            Patient patient = patientRepository.findById(1L).orElse(null);
//            if (patient != null) {
//                System.out.println(patient);
//            } else {
//                System.out.println("Patient not found!");
//            }
//
//            // 4. Search patients
//            System.out.println("----------- Search Patients by Name -----------");
//            List<Patient> patientsWithName = patientRepository.findByNomContains("a");
//            patientsWithName.forEach(System.out::println);
//
//            System.out.println("----------- Search Patients by Health Status -----------");
//            List<Patient> sickPatients = patientRepository.findByMalade(true);
//            sickPatients.forEach(System.out::println);
//
//            System.out.println("----------- Search Patients by Score -----------");
//            List<Patient> highScorePatients = patientRepository.findByScoreGreaterThan(70);
//            highScorePatients.forEach(System.out::println);
//
//            System.out.println("----------- Custom Search JPQL -----------");
//            List<Patient> searchResults = patientRepository.searchPatients("a", 60);
//            searchResults.forEach(System.out::println);
//
//            // 5. Update a patient
//            System.out.println("----------- Update Patient -----------");
//            patient = patientRepository.findById(1L).orElse(null);
//            if (patient != null) {
//                System.out.println("Before update: " + patient);
//                patient.setScore(95);
//                patient.setMalade(false);
//                patientRepository.save(patient);
//                System.out.println("After update: " + patientRepository.findById(1L).orElse(null));
//            }
//
//            // 6. Delete a patient
//            System.out.println("----------- Delete Patient -----------");
//            patientRepository.deleteById(2L);
//            System.out.println("Patient with ID 2 deleted");
//            System.out.println("Remaining patients count: " + patientRepository.count());
//
//            System.out.println("========== Patient Management Application Complete ==========");
//        };
//    }
//}

//V2
package ma.emsi.patientapp;

import ma.emsi.patientapp.entities.*;
import ma.emsi.patientapp.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class PatientAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientAppApplication.class, args);
    }

    @Bean
    CommandLineRunner start(
            PatientRepository patientRepository,
            MedecinRepository medecinRepository,
            RendezVousRepository rendezVousRepository,
            ConsultationRepository consultationRepository,
            UserRepository userRepository,
            RoleRepository roleRepository
    ) {
        return args -> {
            System.out.println("========== Starting Hospital Management Application ==========");

            // 1. Add patients
            System.out.println("----------- Adding Patients -----------");
            Stream.of("Hassan", "Mohammed", "Yasmine", "Farah").forEach(name -> {
                Patient patient = new Patient();
                patient.setNom(name);
                patient.setDateNaissance(new Date());
                patient.setMalade(Math.random() > 0.5); // Randomly set as sick or not
                patient.setScore((int)(Math.random() * 100)); // Random score between 0-100
                patientRepository.save(patient);
            });

            // 2. Add doctors
            System.out.println("----------- Adding Doctors -----------");
            Stream.of("Amal", "Karim", "Samir", "Laila").forEach(name -> {
                Medecin medecin = new Medecin();
                medecin.setNom(name);
                medecin.setEmail(name.toLowerCase() + "@hospital.ma");
                medecin.setSpecialite(Math.random() > 0.5 ? "Cardiologue" : "Dentiste");
                medecinRepository.save(medecin);
            });

            // 3. Create appointments
            System.out.println("----------- Creating Appointments -----------");
            Patient patient1 = patientRepository.findById(1L).orElse(null);
            Patient patient2 = patientRepository.findById(2L).orElse(null);

            Medecin medecin1 = medecinRepository.findById(1L).orElse(null);
            Medecin medecin2 = medecinRepository.findById(2L).orElse(null);

            // First appointment
            RendezVous rendezVous1 = new RendezVous();
            rendezVous1.setDate(new Date());
            rendezVous1.setStatus(StatusRDV.PENDING);
            rendezVous1.setPatient(patient1);
            rendezVous1.setMedecin(medecin1);
            RendezVous savedRDV1 = rendezVousRepository.save(rendezVous1);

            // Second appointment
            RendezVous rendezVous2 = new RendezVous();
            rendezVous2.setDate(new Date());
            rendezVous2.setStatus(StatusRDV.DONE);
            rendezVous2.setPatient(patient2);
            rendezVous2.setMedecin(medecin2);
            RendezVous savedRDV2 = rendezVousRepository.save(rendezVous2);

            // 4. Create a consultation for the completed appointment
            System.out.println("----------- Creating Consultation -----------");
            Consultation consultation = new Consultation();
            consultation.setDateConsultation(new Date());
            consultation.setRendezVous(savedRDV2);
            consultation.setRapport("Patient présentant des symptômes de grippe. Prescription de repos et d'antipyrétiques.");
            consultationRepository.save(consultation);

            // 5. Create users and roles
            System.out.println("----------- Creating Users and Roles -----------");

            // Create roles
            Role roleAdmin = new Role(null, "ADMIN", "Administrator");
            Role roleUser = new Role(null, "USER", "Regular user");
            Role roleDoctor = new Role(null, "DOCTOR", "Medical doctor");

            roleRepository.save(roleAdmin);
            roleRepository.save(roleUser);
            roleRepository.save(roleDoctor);

            // Create users
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("12345");
            admin.setActive(true);
            admin.getRoles().add(roleAdmin);
            userRepository.save(admin);

            User user = new User();
            user.setUsername("user");
            user.setPassword("12345");
            user.setActive(true);
            user.getRoles().add(roleUser);
            userRepository.save(user);

            User doctorUser = new User();
            doctorUser.setUsername("doctor");
            doctorUser.setPassword("12345");
            doctorUser.setActive(true);
            doctorUser.getRoles().add(roleDoctor);
            userRepository.save(doctorUser);

            // 6. Display all patients
            System.out.println("----------- All Patients -----------");
            patientRepository.findAll().forEach(p -> {
                System.out.println(p.toString());
            });

            // 7. Display all doctors
            System.out.println("----------- All Doctors -----------");
            medecinRepository.findAll().forEach(m -> {
                System.out.println(m.toString());
            });

            // 8. Display all appointments
            System.out.println("----------- All Appointments -----------");
            rendezVousRepository.findAll().forEach(rdv -> {
                System.out.println(
                        "ID: " + rdv.getId() +
                                ", Date: " + rdv.getDate() +
                                ", Status: " + rdv.getStatus() +
                                ", Patient: " + rdv.getPatient().getNom() +
                                ", Doctor: " + rdv.getMedecin().getNom()
                );
            });

            // 9. Display all consultations
            System.out.println("----------- All Consultations -----------");
            consultationRepository.findAll().forEach(c -> {
                System.out.println(
                        "ID: " + c.getId() +
                                ", Date: " + c.getDateConsultation() +
                                ", For Appointment ID: " + c.getRendezVous().getId() +
                                ", Report: " + c.getRapport()
                );
            });

            System.out.println("========== Hospital Management Application Complete ==========");
        };
    }
}