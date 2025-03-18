package ma.emsi.patientapp.repositories;

import ma.emsi.patientapp.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    // Custom finder methods
    List<Patient> findByNomContains(String name);
    List<Patient> findByMalade(boolean malade);
    List<Patient> findByScoreGreaterThan(int score);

    // JPQL query method
    @Query("select p from Patient p where p.nom like %:keyword% and p.score > :score")
    List<Patient> searchPatients(@Param("keyword") String keyword, @Param("score") int score);

    // Find patients by date range
    List<Patient> findByDateNaissanceBetween(Date startDate, Date endDate);
}