package it.realeites.learning.registroelettronicobackend.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import it.realeites.learning.registroelettronicobackend.model.dto.UtenteDTO;
import it.realeites.learning.registroelettronicobackend.model.entity.Ruolo;
import it.realeites.learning.registroelettronicobackend.model.entity.Utente;
import it.realeites.learning.registroelettronicobackend.repository.UtenteRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test per UtenteServiceImpl")
class UtenteServiceImplTest {

    @Mock
    private UtenteRepository utenteRepository;

    @InjectMocks
    private UtenteServiceImpl utenteService;

    private Utente tutorMock;
    private Utente tutoratoMock;

    @BeforeEach
    void init() throws Exception {
        tutorMock = new Utente();
        tutorMock.setId(1);
        tutorMock.setNome("Mario");
        tutorMock.setCognome("Rossi");
        tutorMock.setEmail("mario.rossi@email.com");
        tutorMock.setUsername("mario.rossi");
        tutorMock.setPassword("password");
        tutorMock.setRuolo(Ruolo.TUTOR);
        tutorMock.setDataNascita(LocalDate.of(1980, 1, 1));
        tutorMock.setIndirizzo("Via Roma 1");
        tutorMock.setCellulare("1234567890");

        tutoratoMock = new Utente();
        tutoratoMock.setId(2);
        tutoratoMock.setNome("Luca");
        tutoratoMock.setCognome("Bianchi");
        tutoratoMock.setEmail("luca.bianchi@email.com");
        tutoratoMock.setUsername("luca.bianchi");
        tutoratoMock.setPassword("password");
        tutoratoMock.setRuolo(Ruolo.TUTORATO);
        tutoratoMock.setDataNascita(LocalDate.of(1995, 2, 2));
        tutoratoMock.setIndirizzo("Via Milano 2");
        tutoratoMock.setCellulare("0987654321");
        tutoratoMock.setTutor(tutorMock);
    }

    @Test
    @DisplayName("converte Utente in UtenteDTO")
    void testToUtenteDTO() {
        UtenteDTO result = utenteService.toUtenteDTO(tutorMock);

        assertNotNull(result);
        assertEquals(tutorMock.getId(), result.getId());
        assertEquals(tutorMock.getNome(), result.getNome());
        assertEquals(tutorMock.getCognome(), result.getCognome());
        assertEquals(tutorMock.getRuolo(), result.getRuolo());
    }

    @Test
    @DisplayName("trova utente da id")
    void testFindById() {
        when(utenteRepository.findById(1)).thenReturn(Optional.of(tutorMock));

        Optional<UtenteDTO> result = utenteService.findById(1);

        assertTrue(result.isPresent());
        assertEquals(tutorMock.getId(), result.get().getId());
        verify(utenteRepository).findById(1);
    }

    @Test
    @DisplayName("trova entity utente")
    void testFindEntityById() {
        when(utenteRepository.findById(1)).thenReturn(Optional.of(tutorMock));

        Optional<Utente> result = utenteService.findEntityById(1);

        assertTrue(result.isPresent());
        assertEquals(tutorMock, result.get());
        verify(utenteRepository).findById(1);
    }

    @Test
    @DisplayName("ritorna  tutti gli utenti")
    void testGetAllUtenti() {
        when(utenteRepository.findAll()).thenReturn(Arrays.asList(tutorMock, tutoratoMock));

        List<UtenteDTO> result = utenteService.getAllUtenti();

        assertEquals(2, result.size());
        verify(utenteRepository).findAll();
    }

    @Test
    @DisplayName("trova utenti per ruolo")
    void testFindByRuolo() {
        when(utenteRepository.findAll()).thenReturn(Arrays.asList(tutorMock, tutoratoMock));

        List<UtenteDTO> result = utenteService.findByRuolo(Ruolo.TUTOR);

        assertEquals(1, result.size());
        assertEquals(Ruolo.TUTOR, result.get(0).getRuolo());
        verify(utenteRepository).findAll();
    }

    
}