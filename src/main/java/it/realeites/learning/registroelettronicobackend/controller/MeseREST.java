package it.realeites.learning.registroelettronicobackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.realeites.learning.registroelettronicobackend.service.MeseService;

@RestController
@RequestMapping("api")
public class MeseREST {

    @Autowired
    private MeseService service;

    public MeseREST(MeseService service) {
        this.service = service;
    }

    @Operation(summary = "Chiude un mese", description = "Chiude un mese")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mese chiuso"),
            @ApiResponse(responseCode = "404", description = "Mese non chiuso")
    })
    @PostMapping("/setMeseChiuso")
    public ResponseEntity<Void> setMeseChiuso(@RequestParam Integer meseId) {
        service.chiudiMese(meseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
