package it.realeites.learning.registroelettronicobackend.service;

import java.util.List;
import java.util.Optional;

import it.realeites.learning.registroelettronicobackend.model.entity.Giustificativo;

public interface GiustificativoService {

    List<Giustificativo> findAll();
    Optional<Giustificativo> findById(Integer id);
    Giustificativo save(Giustificativo giustificativo);
    Giustificativo update(Integer idGiustificativo, String descrizione);

    public void setAccettato(Integer idGiustificativo);

}
