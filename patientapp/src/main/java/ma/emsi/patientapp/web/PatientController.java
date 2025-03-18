package ma.emsi.patientapp.web;

import ma.emsi.patientapp.entities.Patient;
import ma.emsi.patientapp.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    // Get all patients
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // Get patient by ID
    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    // Search patients by name
    @GetMapping("/search")
    public List<Patient> searchPatients(@RequestParam String keyword) {
        return patientRepository.findByNomContains(keyword);
    }

    // Search patients by health status
    @GetMapping("/malade")
    public List<Patient> getPatientsByHealthStatus(@RequestParam boolean status) {
        return patientRepository.findByMalade(status);
    }

    // Create a new patient
    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }

    // Update patient
    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient != null) {
            patient.setNom(patientDetails.getNom());
            patient.setDateNaissance(patientDetails.getDateNaissance());
            patient.setMalade(patientDetails.isMalade());
            patient.setScore(patientDetails.getScore());
            return patientRepository.save(patient);
        }
        return null;
    }

    // Delete patient
    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientRepository.deleteById(id);
    }
}