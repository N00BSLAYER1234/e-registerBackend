package it.realeites.learning.registroelettronicobackend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.realeites.learning.registroelettronicobackend.model.dto.UtenteDTO;
import it.realeites.learning.registroelettronicobackend.model.entity.Ruolo;
import it.realeites.learning.registroelettronicobackend.service.UtenteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api")
public class UtenteREST {

    @Autowired
    private UtenteService service;

    public UtenteREST(UtenteService service) {
        this.service = service;
    }

    @Operation(summary = "Recupera tutti gli utenti", description = "Restituisce una lista di tutti gli utenti")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Utenti trovati"),
            @ApiResponse(responseCode = "404", description = "Utenti non trovati")
    })
    @GetMapping("/utenti")
    public ResponseEntity<List<UtenteDTO>> getAllUtenti() {
        List<UtenteDTO> utenti = service.getAllUtenti();
        if (utenti.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(utenti, HttpStatus.OK);
    }

    @Operation(summary = "Recupera tutti i tutor", description = "Restituisce una lista di tutti i tutor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tutor trovati"),
            @ApiResponse(responseCode = "404", description = "Tutor non trovati")
    })
    @GetMapping("/tutor")
    public ResponseEntity<List<UtenteDTO>> getTutor() {
        List<UtenteDTO> tutor = service.findByRuolo(Ruolo.TUTOR);
        if (tutor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(tutor, HttpStatus.OK);
    }

    @Operation(summary = "Recupera tutti i tutorati", description = "Restituisce una lista di tutti i tutorati")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tutorati trovati"),
            @ApiResponse(responseCode = "404", description = "Tutorati non trovati")
    })
    @GetMapping("/tutorati")
    public ResponseEntity<List<UtenteDTO>> getTutorati() {
        List<UtenteDTO> tutorati = service.findByRuolo(Ruolo.TUTORATO);
        if (tutorati.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(tutorati, HttpStatus.OK);
    }

    @Operation(summary = "Recupera tutti i tutorati di un tutor", description = "Restituisce una lista di tutti i tutorati di un determinato tutor tramite id del Tutor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tutorati trovati"),
            @ApiResponse(responseCode = "404", description = "Tutorati non trovati")
    })
    @GetMapping("/tutor/{tutorId}/tutorati")
    public ResponseEntity<List<UtenteDTO>> getTutoratiByTutor(@PathVariable Integer tutorId) {
        List<UtenteDTO> tutorati = service.getTutoratiByTutor(tutorId);
        if (tutorati.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(tutorati, HttpStatus.OK);
    }

}
