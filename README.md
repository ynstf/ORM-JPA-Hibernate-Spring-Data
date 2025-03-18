# Hospital Management System with Spring Boot & JPA

A comprehensive hospital management system built using Spring Boot, JPA/Hibernate, and H2/MySQL databases. This application manages patients, doctors, appointments, consultations, and includes user authentication.

##  Technologies Used

- **Spring Boot 3.1.5**: Main framework for building the application
- **Spring Data JPA**: For database operations
- **Hibernate ORM**: For object-relational mapping
- **H2 Database**: In-memory database for development
- **MySQL**: For production database
- **Project Lombok**: For reducing boilerplate code
- **Spring Boot Web**: For creating RESTful APIs

##  Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── ma/
│   │       └── emsi/
│   │           └── patientapp/
│   │               ├── PatientAppApplication.java
│   │               ├── entities/
│   │               ├── repositories/
│   │               └── web/
│   └── resources/
│       ├── application.properties
│       └── application-mysql.properties
├── pom.xml
```

## Key Files & Components

### Entities

The system uses several JPA entities to model the domain:

#### Patient Entity
```java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    private boolean malade;
    private int score;
}
```

#### Medecin Entity
```java
@Entity
@Data
public class Medecin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String email;
    private String specialite;
    
    @OneToMany(mappedBy = "medecin", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<RendezVous> rendezVous;
}
```

#### RendezVous Entity
```java
@Entity
@Data
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    
    @Enumerated(EnumType.STRING)
    private StatusRDV status;
    
    @ManyToOne
    private Patient patient;
    
    @ManyToOne
    private Medecin medecin;
    
    @OneToOne(mappedBy = "rendezVous")
    private Consultation consultation;
}
```

### Repositories

Each entity has a corresponding repository interface that extends JPA Repository:

#### PatientRepository
```java
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByNomContains(String name);
    List<Patient> findByMalade(boolean malade);
    List<Patient> findByScoreGreaterThan(int score);
    
    @Query("select p from Patient p where p.nom like %:keyword% and p.score > :score")
    List<Patient> searchPatients(@Param("keyword") String keyword, @Param("score") int score);
}
```

### Web Controllers

RESTful controllers expose endpoints for interacting with the system:

#### PatientController
```java
@RestController
@RequestMapping("/api/patients")
public class PatientController {
    
    @Autowired
    private PatientRepository patientRepository;
    
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
    
    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }
    
    // Additional endpoints...
}
```

### Main Application

```java
@SpringBootApplication
public class PatientAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientAppApplication.class, args);
    }

    @Bean
    CommandLineRunner start(
            PatientRepository patientRepository,
            MedecinRepository medecinRepository,
            // Other repositories...
    ) {
        return args -> {
            // Initialize sample data
            Stream.of("Hassan", "Mohammed", "Yasmine", "Farah").forEach(name -> {
                Patient patient = new Patient();
                patient.setNom(name);
                patient.setDateNaissance(new Date());
                patient.setMalade(Math.random() > 0.5);
                patient.setScore((int)(Math.random() * 100));
                patientRepository.save(patient);
            });
            
            // Additional initialization code...
        };
    }
}
```

### Configuration Files

#### application.properties (H2)
```properties
# H2 Database configuration
spring.datasource.url=jdbc:h2:mem:patient-db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA/Hibernate configuration
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

## Relationships

The system uses various JPA relationships:

- **OneToMany**: A doctor can have multiple appointments
- **ManyToOne**: An appointment belongs to one doctor and one patient
- **OneToOne**: An appointment can have one consultation
- **ManyToMany**: Users can have multiple roles

## Features

1. **Patient Management**:
   - Add, update, delete patients
   - Search patients by name, health status, and score

2. **Doctor Management**:
   - Add, update, delete doctors
   - Search doctors by speciality or name

3. **Appointment Scheduling**:
   - Create appointments between patients and doctors  
   - Track appointment status (PENDING, CANCELED, DONE)

4. **Consultation Records**:
   - Create consultation records for completed appointments
   - Store consultation reports

5. **User Authentication**:
   - Role-based access control
   - Different roles: ADMIN, USER, DOCTOR

## How to Run

1. **Clone the repository**
   ```
   git clone https://github.com/yourusername/hospital-management.git
   ```

2. **Navigate to the project directory**
   ```
   cd hospital-management
   ```

3. **Run with H2 database (development)**
   ```
   mvn spring-boot:run
   ```

4. **Run with MySQL database (production)**
   ```
   mvn spring-boot:run -Dspring-boot.run.profiles=mysql
   ```

5. **Access the H2 console**
   - URL: http://localhost:8085/h2-console
   - JDBC URL: jdbc:h2:mem:patient-db
   - Username: sa
   - Password: [empty]

6. **Access API endpoints**
   - Base URL: http://localhost:8085/api/
