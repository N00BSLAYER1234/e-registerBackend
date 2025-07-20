// package it.realeites.learning.registroelettronicobackend.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import io.swagger.v3.oas.annotations.Operation;

// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
// import it.realeites.learning.registroelettronicobackend.service.EmailService;
// import it.realeites.learning.registroelettronicobackend.utils.EmailRequest;

// @RestController
// @RequestMapping("/api/mail")
// public class MailREST {

//     @Autowired
//     private EmailService emailService;

//     @Operation(summary = "Invia una mail di avviso se la soglia assenze è superata", description = "Invia una mail di avviso se la soglia assenze è superata")
//     @ApiResponses({
//             @ApiResponse(responseCode = "200", description = "Mail inviata"),
//             @ApiResponse(responseCode = "500", description = "Mail non inviata")
//     })
//     @PostMapping("/send")
//     public ResponseEntity<String> inviaMail(@RequestBody EmailRequest request) {
//         try {
//             emailService.inviaMailAvvisoPresenze(request.getUtente(), request.getNumeroAssenze());
//             return ResponseEntity.ok("Email inviata con successo.");
//         } catch (Exception e) {
//             return ResponseEntity.status(500).body("Errore nell'invio della mail: " + e.getMessage());
//         }
//     }

// }
