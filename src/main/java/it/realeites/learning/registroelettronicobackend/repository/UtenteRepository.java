package it.realeites.learning.registroelettronicobackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.realeites.learning.registroelettronicobackend.model.entity.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Integer>{
}
