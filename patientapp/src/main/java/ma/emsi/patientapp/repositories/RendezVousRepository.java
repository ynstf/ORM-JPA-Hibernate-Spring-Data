package ma.emsi.patientapp.repositories;

import ma.emsi.patientapp.entities.RendezVous;
import ma.emsi.patientapp.entities.StatusRDV;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findByStatus(StatusRDV status);
    List<RendezVous> findByDateBetween(Date startDate, Date endDate);
    List<RendezVous> findByPatientId(Long patientId);
    List<RendezVous> findByMedecinId(Long medecinId);
}