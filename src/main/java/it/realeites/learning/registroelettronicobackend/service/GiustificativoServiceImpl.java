package it.realeites.learning.registroelettronicobackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.realeites.learning.registroelettronicobackend.model.entity.Giustificativo;
import it.realeites.learning.registroelettronicobackend.model.entity.Presenza;
import it.realeites.learning.registroelettronicobackend.repository.GiustificativoRepository;
import it.realeites.learning.registroelettronicobackend.repository.PresenzaRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class GiustificativoServiceImpl implements GiustificativoService{

    @Autowired
    private GiustificativoRepository repo;

    @Autowired
    private PresenzaRepository presenzaRepo;

    // Trova un giustificativo per ID
    @Override
    public Optional<Giustificativo> findById(Integer id) {
        return repo.findById(id);
    }

    // Restituisce tutti i giustificativi
    @Override
    public List<Giustificativo> findAll() {
        return repo.findAll();
    }

    // Salva un giustificativo
    @Override
    public Giustificativo save(Giustificativo giustificativo) {
       return repo.save(giustificativo);
    }

    // Accetta un giustificativo
    @Override
    public void setAccettato(Integer idGiustificativo) {
        Giustificativo giustificativo = repo.findById(idGiustificativo)
                .orElseThrow(() -> new EntityNotFoundException("Giustificativo non trovato con id: " + idGiustificativo));
        
        giustificativo.setAccettata(true);
        repo.save(giustificativo);
    }

    // Aggiorna la descrizione di un giustificativo
    @Override
    public Giustificativo update(Integer idGiustificativo, String descrizione) {

        Giustificativo giustificativo = repo.findById(idGiustificativo)
                .orElseThrow(() -> new EntityNotFoundException("Giustificativo non trovato con id: " + idGiustificativo));

        Presenza presenza = presenzaRepo.findByGiustificativo_Id(idGiustificativo);

        if(presenza.isApprovato()){
            throw new IllegalStateException("Non è possibile aggiornare il giustificativo: la assenza è già approvata");
        }

        giustificativo.setDescrizione(descrizione);
        giustificativo.setAccettata(false);
        return repo.save(giustificativo);
    }
    

}
