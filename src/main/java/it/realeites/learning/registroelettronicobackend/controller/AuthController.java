package it.realeites.learning.registroelettronicobackend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.realeites.learning.registroelettronicobackend.model.dto.LoginRequest;
import it.realeites.learning.registroelettronicobackend.model.dto.LoginResponse;
import it.realeites.learning.registroelettronicobackend.model.dto.UtenteDTO;
import it.realeites.learning.registroelettronicobackend.model.entity.Utente;
import it.realeites.learning.registroelettronicobackend.service.UtenteService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {

    @Autowired
    private UtenteService utenteService;

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return user details")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            Optional<UtenteDTO> userOpt = utenteService.findByEmail(loginRequest.getUsername());
            
            if (userOpt.isEmpty()) {
                // Try to find by username
                Optional<Utente> entityOpt = utenteService.findAll().stream()
                    .filter(u -> u.getUsername().equals(loginRequest.getUsername()))
                    .findFirst();
                
                if (entityOpt.isPresent()) {
                    userOpt = Optional.of(utenteService.toUtenteDTO(entityOpt.get()));
                }
            }

            if (userOpt.isPresent()) {
                UtenteDTO user = userOpt.get();
                // Simple password check (in production, use proper password hashing)
                Optional<Utente> fullUser = utenteService.findEntityById(user.getId());
                
                if (fullUser.isPresent() && fullUser.get().getPassword().equals(loginRequest.getPassword())) {
                    LoginResponse response = new LoginResponse();
                    response.setSuccess(true);
                    response.setMessage("Login successful");
                    response.setUser(user);
                    response.setUserType(user.getRuolo().toString().toLowerCase());
                    
                    return ResponseEntity.ok(response);
                }
            }

            LoginResponse response = new LoginResponse();
            response.setSuccess(false);
            response.setMessage("Invalid credentials");
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            
        } catch (Exception e) {
            LoginResponse response = new LoginResponse();
            response.setSuccess(false);
            response.setMessage("Login failed: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}