package it.realeites.learning.registroelettronicobackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.realeites.learning.registroelettronicobackend.model.entity.Presenza;
import it.realeites.learning.registroelettronicobackend.repository.PresenzaRepository;

@Service
public class CsvServiceImpl implements CsvService {

    @Autowired
    private PresenzaRepository repo;
    
    public String exportPresenzeMesiChiusi(Integer numeroMese, Integer anno, Integer idUtente) {
        
        if (numeroMese == null || numeroMese < 1 || numeroMese > 12) {
            throw new IllegalArgumentException("Il mese deve essere tra 1 e 12");
        }
        if (anno == null || anno < 2025 ) {
            throw new IllegalArgumentException("L'anno deve essere a partire dal 2025");
        }
        
        List<Presenza> presenze = repo.findByMeseChiusoAnnoUtente(numeroMese, anno, idUtente);
        
        if (presenze.isEmpty()) {
            throw new RuntimeException("Nessuna presenza trovata per il mese " + numeroMese);
        }
        
        StringBuilder csv = new StringBuilder();
        
        // Header del CSV
        csv.append("Data,Stato,Approvato,Utente,Giustificativo,Mese,Anno\n");
        
        // Dati da inserire nel csv
        for (Presenza presenza : presenze) {
            csv.append(presenza.getData()).append(",")
               .append(presenza.getStato() ? "Presente" : "Assente").append(",")
               .append(presenza.isApprovato() ? "Sì" : "No").append(",")
               .append(presenza.getUtente().getNome()).append(" ").append(presenza.getUtente().getCognome()).append(",")
               .append(presenza.getGiustificativo() != null ? presenza.getGiustificativo().getDescrizione() : "Nessuno").append(",")
               .append(presenza.getMese().getNumeroMese()).append(",")
               .append(presenza.getMese().getAnno()).append("\n");
        }
        
        return csv.toString();
    }

}
