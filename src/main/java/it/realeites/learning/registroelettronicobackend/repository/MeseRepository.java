package it.realeites.learning.registroelettronicobackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.realeites.learning.registroelettronicobackend.model.entity.Mese;

public interface MeseRepository extends JpaRepository<Mese, Integer>{
    Optional<Mese> findByUtente_IdAndNumeroMese(Integer idUtente, Integer numeroMese);
}
