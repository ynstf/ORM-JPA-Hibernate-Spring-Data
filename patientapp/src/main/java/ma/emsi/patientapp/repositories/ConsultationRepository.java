package ma.emsi.patientapp.repositories;

import ma.emsi.patientapp.entities.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByDateConsultationBetween(Date startDate, Date endDate);
    Consultation findByRendezVousId(Long rendezVousId);
}