package it.realeites.learning.registroelettronicobackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.realeites.learning.registroelettronicobackend.model.entity.Giustificativo;

public interface GiustificativoRepository extends JpaRepository<Giustificativo, Integer>{
}
