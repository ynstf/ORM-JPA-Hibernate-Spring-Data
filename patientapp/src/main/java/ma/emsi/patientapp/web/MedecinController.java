package ma.emsi.patientapp.web;

import ma.emsi.patientapp.entities.Medecin;
import ma.emsi.patientapp.repositories.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medecins")
public class MedecinController {

    @Autowired
    private MedecinRepository medecinRepository;

    @GetMapping
    public List<Medecin> getAllMedecins() {
        return medecinRepository.findAll();
    }

    @GetMapping("/{id}")
    public Medecin getMedecinById(@PathVariable Long id) {
        return medecinRepository.findById(id).orElse(null);
    }

    @GetMapping("/search")
    public List<Medecin> searchMedecins(@RequestParam String keyword) {
        return medecinRepository.findByNomContains(keyword);
    }

    @GetMapping("/specialite")
    public List<Medecin> getMedecinsBySpecialite(@RequestParam String specialite) {
        return medecinRepository.findBySpecialite(specialite);
    }

    @PostMapping
    public Medecin createMedecin(@RequestBody Medecin medecin) {
        return medecinRepository.save(medecin);
    }

    @PutMapping("/{id}")
    public Medecin updateMedecin(@PathVariable Long id, @RequestBody Medecin medecinDetails) {
        Medecin medecin = medecinRepository.findById(id).orElse(null);
        if (medecin != null) {
            medecin.setNom(medecinDetails.getNom());
            medecin.setEmail(medecinDetails.getEmail());
            medecin.setSpecialite(medecinDetails.getSpecialite());
            return medecinRepository.save(medecin);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteMedecin(@PathVariable Long id) {
        medecinRepository.deleteById(id);
    }
}