package it.realeites.learning.registroelettronicobackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.realeites.learning.registroelettronicobackend.service.CsvService;

@RestController
@RequestMapping("/exportCSV")
public class CsvREST {

    @Autowired
    private CsvService service;

    @Operation(summary = "Esporta presenze per mese chiuso per singolo utente", description = "Esporta in formato CSV le presenze relative a un mese specifico già chiuso per singolo utente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "CSV generato con successo"),
            @ApiResponse(responseCode = "400", description = "Parametri non validi"),
            @ApiResponse(responseCode = "404", description = "Dati non trovati per il mese/anno specificato"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server durante l'esportazione")
    })
    @Parameter(name = "mese", description = "Numero del mese (1-12)", required = true)
    @Parameter(name = "anno", description = "Anno di riferimento", required = true)
    @GetMapping("/presenzeMesiChiusi")
    public ResponseEntity<String> exportPresenzeMesiChiusi(
            @RequestParam("mese") Integer numeroMese,
            @RequestParam("anno") Integer anno,
            @RequestParam("id_utente") Integer idUtente) {
        try {
            String csv = service.exportPresenzeMesiChiusi(numeroMese, anno, idUtente);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.setContentDispositionFormData("attachment",
                    "presenze_Utente: " + idUtente + "Mese: " + numeroMese + "_" + anno + "_chiuso.csv");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(csv);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Errore parametri: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante l'esportazione: " + e.getMessage());
        }
    }

}
