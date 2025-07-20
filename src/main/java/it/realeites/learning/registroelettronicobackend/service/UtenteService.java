package it.realeites.learning.registroelettronicobackend.service;

import java.util.List;
import java.util.Optional;

import it.realeites.learning.registroelettronicobackend.model.dto.UtenteDTO;
import it.realeites.learning.registroelettronicobackend.model.entity.Ruolo;
import it.realeites.learning.registroelettronicobackend.model.entity.Utente;

public interface UtenteService {

    Optional<UtenteDTO> findById(Integer id);
    Optional<Utente> findEntityById(Integer id);
    Optional<UtenteDTO> findByEmail(String email);
    List<UtenteDTO> findByRuolo(Ruolo ruolo);
    List<UtenteDTO> getAllUtenti();
    List<UtenteDTO> getTutoratiByTutor(Integer idTutor);
    List<Utente> findAll();

    UtenteDTO toUtenteDTO(Utente utente);
}