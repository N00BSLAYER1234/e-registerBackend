package it.realeites.learning.registroelettronicobackend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.realeites.learning.registroelettronicobackend.model.dto.UtenteDTO;
import it.realeites.learning.registroelettronicobackend.model.entity.Utente;
import jakarta.persistence.EntityNotFoundException;

@Service
public class EmailService {

    private final RestTemplate restTemplate;
    private final String endpoint = "https://apigw-test.grupporealemutua.it/gateway/servizicomuni/mail/v1.0";
    private final String apiKey = "658c4fdf-6e30-49ee-8919-6114c519c969";
 
    private final UtenteService utenteService;
 
    public EmailService(UtenteService utenteService) {
        this.restTemplate = new RestTemplate();
        this.utenteService = utenteService;
    }
 
    public void inviaMailAvvisoPresenze(UtenteDTO utenteDTO, int numeroAssenze) {
       
        Utente tutor = utenteService.findEntityById(utenteDTO.getTutorId())
            .orElseThrow(() -> new EntityNotFoundException("Tutor non trovato con id: " + utenteDTO.getTutorId()));
 
       
        Map<String, Object> body = new HashMap<>();
        body.put("canale", "ITS_GRUPPOx");
        body.put("codiceCompagnia", "RMA");
        body.put("formatoMail", "Testo");
        body.put("mittente", "noreply@realeites.com");
        body.put("destinatari", List.of(tutor.getEmail()));
        body.put("destinatariCC", List.of(utenteDTO.getEmail()));
        body.put("oggetto", "Soglia assenze superata");
        body.put("corpo", "Il tutorato " + utenteDTO.getNome() + utenteDTO.getCognome() + " ha " + numeroAssenze + " assenze non giustificate.");
 
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-Gateway-APIKey", apiKey);
 
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
 
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(endpoint, request, String.class);
            System.out.println("Email inviata correttamente: " + response.getStatusCode());
        } catch (Exception e) {
            System.err.println("Errore nell'invio della mail: " + e.getMessage());
        }
    }

}
