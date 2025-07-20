package it.realeites.learning.registroelettronicobackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.realeites.learning.registroelettronicobackend.model.entity.Giustificativo;
import it.realeites.learning.registroelettronicobackend.service.GiustificativoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("api")
public class GiustificativoREST {

    @Autowired
    private GiustificativoService service;

    @Operation(summary = "Recupera tutti giustificativi", description = "Restituisce una lista di tutti i giustificativi")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Giustificativi trovati"),
            @ApiResponse(responseCode = "500", description = "Giustificativi non trovati")
    })
    @GetMapping("/giustificativi")
    public ResponseEntity<List<Giustificativo>> getAllGiustificativi() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Recupera un giustificativo", description = "Restituisce un giustificativo tramite id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Giustificativo trovato"),
            @ApiResponse(responseCode = "404", description = "Giustificativo non trovato"),
            @ApiResponse(responseCode = "400", description = "ID non valido"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    @GetMapping("/giustificativoById")
    public ResponseEntity<Optional<Giustificativo>> getGiustificativoById(@RequestParam Integer giustificativoId) {
        return new ResponseEntity<>(service.findById(giustificativoId), HttpStatus.OK);
    }

    @Operation(summary = "Approva un giustificativo", description = "Approva un giustificativo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Giustificativo approvato"),
            @ApiResponse(responseCode = "404", description = "Giustificativo non trovato"),
            @ApiResponse(responseCode = "400", description = "ID non valido"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    @PostMapping("/setGiustificativoApprovato")
    public ResponseEntity<Void> setApprovato(@RequestParam Integer giustificativoId) {
        service.setAccettato(giustificativoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Modifica un giustificativo", description = "Modifica un giustificativo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Giustificativo aggiornato"),
            @ApiResponse(responseCode = "404", description = "Giustificativo non trovato"),
            @ApiResponse(responseCode = "400", description = "Dati non validi"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    @PostMapping("/updateGiustificativo")
    public ResponseEntity<Void> updateGiustificativo(@RequestParam Integer giustificativoId, String descrizione) {
        service.update(giustificativoId, descrizione);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
