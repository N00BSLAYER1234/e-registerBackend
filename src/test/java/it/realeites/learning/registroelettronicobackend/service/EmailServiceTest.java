package it.realeites.learning.registroelettronicobackend.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.realeites.learning.registroelettronicobackend.model.dto.UtenteDTO;
import it.realeites.learning.registroelettronicobackend.model.entity.Ruolo;
import it.realeites.learning.registroelettronicobackend.model.entity.Utente;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test per EmailService")
class EmailServiceTest {


    @Mock
    private UtenteService utenteService;

    @InjectMocks
    private EmailService emailService;

    private UtenteDTO utenteDTOMock;
    private Utente tutorMock;

    @BeforeEach
    void setUp() throws Exception {
        utenteDTOMock = new UtenteDTO();
        utenteDTOMock.setId(1);
        utenteDTOMock.setNome("Mario");
        utenteDTOMock.setCognome("Rossi");
        utenteDTOMock.setEmail("mario.rossi@email.com");
        utenteDTOMock.setRuolo(Ruolo.TUTORATO);
        utenteDTOMock.setTutorId(2);

        // Create a mock tutor entity
        tutorMock = new Utente();
        tutorMock.setId(2);
        tutorMock.setNome("Giovanni");
        tutorMock.setCognome("Bianchi");
        tutorMock.setEmail("giovanni.bianchi@email.com");
        tutorMock.setRuolo(Ruolo.TUTOR);

        // Mock the utenteService.findEntityById method
        when(utenteService.findEntityById(any(Integer.class))).thenReturn(Optional.of(tutorMock));
    }

    @Test
    @DisplayName("invia Mail avviso presenze")
    void invioMailPresenze() {
        assertDoesNotThrow(() -> {
            emailService.inviaMailAvvisoPresenze(utenteDTOMock, 5);
        });
    }

    @Test
    @DisplayName("invio email avviso assenza")
    void invioEmailAssenza() {
        UtenteDTO utenteDTO = new UtenteDTO();
        utenteDTO.setId(1);
        utenteDTO.setNome("Mario");
        utenteDTO.setCognome("Rossi");
        utenteDTO.setEmail("mario.rossi@example.com");
        utenteDTO.setTutorId(2);
        
        assertDoesNotThrow(() -> {
            emailService.inviaMailAvvisoPresenze(utenteDTO, 5);
        });
    }
}