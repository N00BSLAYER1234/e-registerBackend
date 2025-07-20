package it.realeites.learning.registroelettronicobackend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.realeites.learning.registroelettronicobackend.model.dto.UtenteDTO;
import it.realeites.learning.registroelettronicobackend.model.entity.Ruolo;
import it.realeites.learning.registroelettronicobackend.model.entity.Utente;
import it.realeites.learning.registroelettronicobackend.repository.UtenteRepository;

@Service
public class UtenteServiceImpl implements UtenteService {

    @Autowired
    private UtenteRepository repo;

    public UtenteServiceImpl(UtenteRepository repo) {
        this.repo = repo;
    }

    // Trova un utente per ID
    @Override
    public Optional<UtenteDTO> findById(Integer id) {
        return repo.findById(id)
                .map(this::toUtenteDTO); 
    }

    // Trova un utente per email
    @Override
    public Optional<UtenteDTO> findByEmail(String email) {
        if (email == null) {
            return Optional.empty();
        }
        return repo.findAll().stream()
                .filter(utente -> utente.getEmail() != null && utente.getEmail().equals(email))
                .findFirst()
                .map(this::toUtenteDTO);
    }

    // Trova tutti gli utenti con un ruolo specifico
    @Override
    public List<UtenteDTO> findByRuolo(Ruolo ruolo) {
        return repo.findAll().stream()
                .filter(utente -> utente.getRuolo() != null && utente.getRuolo().equals(ruolo))
                .map(this::toUtenteDTO)
                .collect(Collectors.toList());
    }

    // Restituisce tutti gli utenti
    @Override
    public List<UtenteDTO> getAllUtenti() {
        return repo.findAll().stream()
                .map(this::toUtenteDTO)
                .collect(Collectors.toList());
    }

    // Trova tutti i tutorati di un tutor specifico
    @Override
    public List<UtenteDTO> getTutoratiByTutor(Integer idTutor) {
        return repo.findAll().stream()
                .filter(utente -> utente.getTutor() != null && utente.getTutor().getId().equals(idTutor))
                .map(this::toUtenteDTO)
                .collect(Collectors.toList());
    }

    // Trova un'entità utente per ID
    @Override
    public Optional<Utente> findEntityById(Integer id) {
        return repo.findById(id);
    }

    // Trova tutti gli utenti (entità)
    @Override
    public List<Utente> findAll() {
        return repo.findAll();
    }

    // Converte un'entità Utente in UtenteDTO
    @Override
    public UtenteDTO toUtenteDTO(Utente utente) {
        if (utente == null)
            return null;

        UtenteDTO dto = new UtenteDTO();

        dto.setId(utente.getId());
        dto.setNome(utente.getNome());
        dto.setCognome(utente.getCognome());
        dto.setDataNascita(utente.getDataNascita());
        dto.setIndirizzo(utente.getIndirizzo());
        dto.setCellulare(utente.getCellulare());
        dto.setEmail(utente.getEmail());
        dto.setUsername(utente.getUsername());
        dto.setRuolo(utente.getRuolo());
        dto.setTutorId(utente.getTutor() != null ? utente.getTutor().getId() : null);

        return dto;
    }

}
