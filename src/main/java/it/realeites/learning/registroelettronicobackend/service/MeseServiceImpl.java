package it.realeites.learning.registroelettronicobackend.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.realeites.learning.registroelettronicobackend.model.entity.Mese;
import it.realeites.learning.registroelettronicobackend.repository.MeseRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class MeseServiceImpl implements MeseService {

    @Autowired
    private MeseRepository repo;

    // Trova un mese per ID
    @Override
    public Optional<Mese> findById(Integer id) {
        return repo.findById(id);
    }

    // Restituisce tutti i mesi
    @Override
    public List<Mese> findAll() {
        return repo.findAll();
    }

    // Chiude un mese specificato
    @Override
    public Mese chiudiMese(Integer meseId) {
        Mese mese = repo.findById(meseId).orElseThrow(() -> new EntityNotFoundException("Mese non trovato con id: " + meseId));
        mese.setMeseChiuso(true);
        return repo.save(mese);
    }

}
