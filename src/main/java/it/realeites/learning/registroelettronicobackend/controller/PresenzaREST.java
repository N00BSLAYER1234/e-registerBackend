package it.realeites.learning.registroelettronicobackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.realeites.learning.registroelettronicobackend.model.dto.PresenzaDTO;
import it.realeites.learning.registroelettronicobackend.model.dto.PresenzaRequest;
import it.realeites.learning.registroelettronicobackend.service.PresenzaService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api")
public class PresenzaREST {

    @Autowired
    private PresenzaService service;

    @Operation(summary = "Recupera tutte le presenze", description = "Restituisce una lista di tutte le presenze")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Presenze trovate"),
            @ApiResponse(responseCode = "404", description = "Presenze non trovate")
    })
    @GetMapping("/presenze")
    public ResponseEntity<List<PresenzaDTO>> getAllPresenze() {
        List<PresenzaDTO> presenze = service.findAll();
        return ResponseEntity.ok(presenze);
    }

    @Operation(summary = "Recupera una presenza tramite id", description = "Restituisce una presenza tramite id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Presenza trovata"),
            @ApiResponse(responseCode = "404", description = "Presenza non trovata")
    })
    @GetMapping("/presenzaById")
    public ResponseEntity<PresenzaDTO> getPresenzaById(@RequestParam Integer presenzaId) {
        PresenzaDTO presenza = service.findById(presenzaId);
        return ResponseEntity.ok(presenza);
    }

    @Operation(summary = "Aggiunge una nuova presenza", description = "Aggiunge una nuova presenza")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Presenza aggiunta"),
            @ApiResponse(responseCode = "404", description = "Presenza non aggiunta")
    })
    @PostMapping("/addPresenza")
    public ResponseEntity<PresenzaDTO> addPresenza(@RequestBody PresenzaRequest presenzaRequest) {
        PresenzaDTO presenza = service.save(presenzaRequest);
        return ResponseEntity.ok(presenza);
    }


    @Operation(summary = "Aggiunge una nuova presenza da parte del tutor", description = "Aggiunge una nuova presenza da parte del tutor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Presenza aggiunta"),
            @ApiResponse(responseCode = "404", description = "Presenza non aggiunta")
    })
    @PostMapping("/addPresenzaByTutor")
    public ResponseEntity<PresenzaDTO> addPresenzaByTutor(@RequestBody PresenzaRequest presenzaRequest,@RequestParam Integer tutorId) {
        PresenzaDTO presenza = service.saveByTutor(presenzaRequest, tutorId);
        return ResponseEntity.ok(presenza);
    }

    @Operation(summary = "Cambia stato da assenza a presenza", description = "Cambia stato da assenza a presenza")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stato cambiato"),
            @ApiResponse(responseCode = "500", description = "Stato non cambiato")
    })
    @PostMapping("/changeStatoPresenza")
    public ResponseEntity<Void> changeStato(@RequestParam Integer presenzaId) {
        service.updatePresenza(presenzaId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Approva una presenza", description = "Approva una presenza")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Presenza approvata"),
            @ApiResponse(responseCode = "404", description = "Presenza non approvata")
    })
    @PostMapping("/setPresenzaApprovata")
    public ResponseEntity<Void> setApprovato(@RequestParam Integer presenzaId, Integer tutorId) {
        service.setApprovato(presenzaId, tutorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Aggiunge un giustificativo ad una assenza", description = "Aggiunge un giustificativo ad una assenza")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Giustificativo aggiunto"),
            @ApiResponse(responseCode = "404", description = "Giustificativo non aggiunto")
    })
    @PostMapping("/setGiustificativo")
    public ResponseEntity<Void> setGiustificativo(@RequestParam Integer presenzaId, String descrizione) {
        service.addGiustificativo(presenzaId, descrizione);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Disapprova una presenza", description = "Disapprova una presenza")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Presenza disapprovata"),
            @ApiResponse(responseCode = "404", description = "Presenza non disapprovata")
    })
    @PostMapping("/deletePresenzaApprovata")
    public ResponseEntity<Void> deleteApprovato(@RequestParam Integer presenzaId) {
        service.deleteApprovato(presenzaId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Recupera tutte le presenze di un mese", description = "Restituisce una lista di tutte le presenze di mese")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Presenze trovate"),
            @ApiResponse(responseCode = "404", description = "Presenze non trovate")
    })
    @GetMapping("/presenzeMese")
    public ResponseEntity<List<PresenzaDTO>> getAllPresenzeByMese(@RequestParam Integer meseId) {
        List<PresenzaDTO> presenze = service.findAllByMese_Id(meseId);
        return ResponseEntity.ok(presenze);
    }

    @Operation(summary = "Recupera tutte le presenze di un utente", description = "Restituisce una lista di tutte le presenze di utente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Presenze trovate"),
            @ApiResponse(responseCode = "404", description = "Presenze non trovate")
    })
    @GetMapping("/presenzeUtente")
    public ResponseEntity<List<PresenzaDTO>> getAllPresenzeByUtente(@RequestParam Integer utenteId) {
        List<PresenzaDTO> presenze = service.findAllByUtente_Id(utenteId);
        return ResponseEntity.ok(presenze);
    }

    @Operation(summary = "Elimina una presenza", description = "Elimina una presenza")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Presenza eliminata"),
            @ApiResponse(responseCode = "404", description = "Presenza non eliminata")
    })
    @DeleteMapping("/deletePresenza")
    public ResponseEntity<String> deletePresenzaById(@RequestParam Integer presenzaId, Integer tutorId) {
        service.deleteById(presenzaId);
        return ResponseEntity.ok("Presenza eliminata con successo");
    }

    @Operation(summary = "Controlla le assenze dell'utente e invia automaticamente una mail se la soglia è superata", description = "Controlla le assenze dell'utente e invia automaticamente una mail se la soglia è superata")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Controllo completato"),
            @ApiResponse(responseCode = "404", description = "Utente non trovato"),
            @ApiResponse(responseCode = "500", description = "Errore nel controllo")
    })
    @GetMapping("/checkAssenzeEmail")
    public ResponseEntity<String> checkAssenze(@RequestParam Integer utenteId, @RequestParam Integer idMese) {
        service.controllaPresenzeEUtente(utenteId, idMese);
        return ResponseEntity.ok("Controllo presenze completato.");
    }

}
