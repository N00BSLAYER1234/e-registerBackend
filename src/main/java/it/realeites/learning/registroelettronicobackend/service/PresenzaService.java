package it.realeites.learning.registroelettronicobackend.service;

import java.util.List;


import it.realeites.learning.registroelettronicobackend.model.dto.PresenzaDTO;
import it.realeites.learning.registroelettronicobackend.model.dto.PresenzaRequest;

public interface PresenzaService {

    PresenzaDTO findById(Integer id);
    List<PresenzaDTO> findAll();
    PresenzaDTO save(PresenzaRequest presenzaRequest);
    PresenzaDTO saveByTutor(PresenzaRequest presenzaRequest, Integer tutorId);
    void deleteById(Integer id);
    void setApprovato(Integer idPresenza, Integer idTutor);
    void deleteApprovato(Integer idPresenza);
    PresenzaDTO updatePresenza(Integer id);
    List<PresenzaDTO> findAllByMese_Id(Integer idMese);
    List<PresenzaDTO> findAllByUtente_Id(Integer idUtente);
    void addGiustificativo(Integer id, String descrizione);

    void controllaPresenzeEUtente(Integer utenteId, Integer idMese);

}
