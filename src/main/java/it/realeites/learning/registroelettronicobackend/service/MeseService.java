package it.realeites.learning.registroelettronicobackend.service;

import java.util.List;
import java.util.Optional;
import it.realeites.learning.registroelettronicobackend.model.entity.Mese;

public interface MeseService {

    Optional<Mese> findById(Integer id);
    List<Mese> findAll();
    Mese chiudiMese(Integer meseId);

}
