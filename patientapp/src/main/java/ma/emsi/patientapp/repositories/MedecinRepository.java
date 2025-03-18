package ma.emsi.patientapp.repositories;

import ma.emsi.patientapp.entities.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    List<Medecin> findByNomContains(String name);
    Medecin findByEmail(String email);
    List<Medecin> findBySpecialite(String specialite);
}