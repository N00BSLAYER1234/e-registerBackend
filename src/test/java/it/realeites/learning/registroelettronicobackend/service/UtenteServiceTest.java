package it.realeites.learning.registroelettronicobackend.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
@DisplayName("test JUnit per utenteService")
class UtenteServiceTest {

    @Mock
    private UtenteRepository utenteRepository;

    @InjectMocks
    private UtenteServiceImpl utenteService;

    private Utente tutorMock;
    private Utente tutoratoMock;
    private Utente altroTutoratoMock;

    @BeforeEach
    void setUp() throws Exception {

        tutorMock = new Utente();
        tutorMock.setId(1);
        tutorMock.setNome("Mario");
        tutorMock.setCognome("Alighieri");
        tutorMock.setDataNascita(LocalDate.of(1980, 5, 15));
        tutorMock.setIndirizzo("Via Roma 123");
        tutorMock.setCellulare("3331234567");
        tutorMock.setEmail("dante.alighieri@realeites.com");
        tutorMock.setUsername("dante.alighieri");
        tutorMock.setPassword("password123");
        tutorMock.setRuolo(Ruolo.TUTOR);
        tutorMock.setTutor(null);


        tutoratoMock = new Utente();
        tutoratoMock.setId(2);
        tutoratoMock.setNome("Virgilio");
        tutoratoMock.setCognome("Marone");
        tutoratoMock.setDataNascita(LocalDate.of(1995, 3, 10));
        tutoratoMock.setIndirizzo("Via Limbo 456");
        tutoratoMock.setCellulare("3337654321");
        tutoratoMock.setEmail("virgilio.marone@inferno.com");
        tutoratoMock.setUsername("virgilio.marone");
        tutoratoMock.setPassword("password456");
        tutoratoMock.setRuolo(Ruolo.TUTORATO);
        tutoratoMock.setTutor(tutorMock);

        altroTutoratoMock = new Utente();
        altroTutoratoMock.setId(3);
        altroTutoratoMock.setNome("Francesca");
        altroTutoratoMock.setCognome("da Rimini");
        altroTutoratoMock.setDataNascita(LocalDate.of(1996, 7, 20));
        altroTutoratoMock.setIndirizzo("Via Torino 789");
        altroTutoratoMock.setCellulare("3339876543");
        altroTutoratoMock.setEmail("francesca.daRimini@test.com");
        altroTutoratoMock.setUsername("francesca.daRimini");
        altroTutoratoMock.setPassword("password789");
        altroTutoratoMock.setRuolo(Ruolo.TUTORATO);
        altroTutoratoMock.setTutor(tutorMock);


    }

    @Nested
    @DisplayName("Test UtenteDTO")
    class ToUtenteDTOTest {

        @Test
        @DisplayName("converte correttamente Utente in utenteDTO")
        void converteUtenteInUtenteDTO() {

            UtenteDTO result = utenteService.toUtenteDTO(tutoratoMock);
            assertNotNull(result);
            assertEquals(tutoratoMock.getId(), result.getId());
            assertEquals(tutoratoMock.getNome(), result.getNome());
            assertEquals(tutoratoMock.getCognome(), result.getCognome());
            assertEquals(tutoratoMock.getDataNascita(), result.getDataNascita());
            assertEquals(tutoratoMock.getIndirizzo(), result.getIndirizzo());
            assertEquals(tutoratoMock.getCellulare(), result.getCellulare());
            assertEquals(tutoratoMock.getEmail(), result.getEmail());
            assertEquals(tutoratoMock.getUsername(), result.getUsername());
            assertEquals(tutoratoMock.getRuolo(), result.getRuolo());
            assertEquals(tutoratoMock.getTutor().getId(), result.getTutorId());
        }

        @Test
        @DisplayName(" convertire tutor senza tutorId")
        void converteTutorSenzaTutorId() {

            UtenteDTO result = utenteService.toUtenteDTO(tutorMock);

            assertNotNull(result);
            assertEquals(tutorMock.getId(), result.getId());
            assertEquals(tutorMock.getNome(), result.getNome());
            assertEquals(tutorMock.getCognome(), result.getCognome());
            assertEquals(tutorMock.getRuolo(), result.getRuolo());
            assertNull(result.getTutorId());
        }

        @Test
        @DisplayName(" restituire null per utente null")
        void ritornaNullPerUtenteNull() {
            UtenteDTO result = utenteService.toUtenteDTO(null);

            assertNull(result);
        }
    }

    @Nested
    @DisplayName("Test per il metodo findById")
    class FindByIdTest {

        @Test
        @DisplayName(" trovare utente per ID")
        void CercaUtentePerId() {
            when(utenteRepository.findById(1)).thenReturn(Optional.of(tutorMock));

            Optional<UtenteDTO> result = utenteService.findById(1);

            assertTrue(result.isPresent());
            assertEquals(tutorMock.getId(), result.get().getId());
            assertEquals(tutorMock.getNome(), result.get().getNome());
            assertEquals(tutorMock.getCognome(), result.get().getCognome());
            verify(utenteRepository).findById(1);
        }

        @Test
        @DisplayName(" restituire Optional vuoto per ID inesistente")
        void ritornaOptionalVuotoPerIdInesistente() {
            when(utenteRepository.findById(999)).thenReturn(Optional.empty());

            Optional<UtenteDTO> result = utenteService.findById(999);

            assertFalse(result.isPresent());
            verify(utenteRepository).findById(999);
        }
    }

    @Nested
    @DisplayName("Test per il metodo findEntityById")
    class FindEntityByIdTest {

        @Test
        @DisplayName(" trovare entity utente con id")
        void TrovaEntityUtentePerId() {
            when(utenteRepository.findById(1)).thenReturn(Optional.of(tutorMock));

            Optional<Utente> result = utenteService.findEntityById(1);

            assertTrue(result.isPresent());
            assertEquals(tutorMock, result.get());
            verify(utenteRepository).findById(1);
        }

        @Test
        @DisplayName(" restituisce optional vuoto per id inesistente")
        void ritornaOptionalVuotoPerIdInesistente() {
            when(utenteRepository.findById(999)).thenReturn(Optional.empty());

            Optional<Utente> result = utenteService.findEntityById(999);

            assertFalse(result.isPresent());
            verify(utenteRepository).findById(999);
        }
    }


    @Nested
    @DisplayName("Test per il metodo findByEmail")
    class FindByEmailTest {

        @Test
        @DisplayName(" trova utente con email esistente")
        void TrovaUtentePerEmailEsistente() {
            when(utenteRepository.findAll()).thenReturn(Arrays.asList(tutorMock, tutoratoMock));

            Optional<UtenteDTO> result = utenteService.findByEmail("virgilio.marone@inferno.com");

            assertTrue(result.isPresent());
            assertEquals(tutoratoMock.getId(), result.get().getId());
            assertEquals(tutoratoMock.getEmail(), result.get().getEmail());
            verify(utenteRepository).findAll();
        }

        @Test
        @DisplayName(" restituisce optional vuoto con email inesistente")
        void ritornaOptionalVuotoPerEmailInesistente() {
            when(utenteRepository.findAll()).thenReturn(Arrays.asList(tutorMock, tutoratoMock));

            Optional<UtenteDTO> result = utenteService.findByEmail("email.inesistente@test.com");

            assertFalse(result.isPresent());
            verify(utenteRepository).findAll();
        }

        @Test
        @DisplayName(" restituisce optional vuoto con email null")
        void ritornaOptionalVuotoPerEmailNull() {
            Optional<UtenteDTO> result = utenteService.findByEmail(null);

            assertFalse(result.isPresent());
        }
    }

    @Nested
    @DisplayName("Test per il metodo findByRuolo")
    class FindByRuoloTest {

        @Test
        @DisplayName(" trova tutti i tutor")
        void TrovaTuttiITutor() {
            when(utenteRepository.findAll()).thenReturn(Arrays.asList(tutorMock, tutoratoMock, altroTutoratoMock));

            List<UtenteDTO> result = utenteService.findByRuolo(Ruolo.TUTOR);

            assertEquals(1, result.size());
            assertEquals(tutorMock.getId(), result.get(0).getId());
            assertEquals(Ruolo.TUTOR, result.get(0).getRuolo());
            verify(utenteRepository).findAll();
        }

        @Test
        @DisplayName(" trovare tutti i gli studenti")
        void TrovaTuttiStudenti() {
            when(utenteRepository.findAll()).thenReturn(Arrays.asList(tutorMock, tutoratoMock, altroTutoratoMock));

            List<UtenteDTO> result = utenteService.findByRuolo(Ruolo.TUTORATO);

            assertEquals(2, result.size());
            assertTrue(result.stream().allMatch(u -> u.getRuolo() == Ruolo.TUTORATO));
            verify(utenteRepository).findAll();
        }
    }

    @Nested
    @DisplayName("Test per il metodo getAllUtenti")
    class GetAllUtentiTest {

        @Test
        @DisplayName(" restituire tutti gli utenti")
        void RestituireTuttiGliUtenti() {
            when(utenteRepository.findAll()).thenReturn(Arrays.asList(tutorMock, tutoratoMock, altroTutoratoMock));

            List<UtenteDTO> result = utenteService.getAllUtenti();

            assertEquals(3, result.size());
            verify(utenteRepository).findAll();
        }

        @Test
        @DisplayName("restituisce lista vuota quando non ci sono utenti")
        void RitornaListaVuotaQuandoNonCiSonoUtenti() {
            when(utenteRepository.findAll()).thenReturn(Arrays.asList());

            List<UtenteDTO> result = utenteService.getAllUtenti();

            assertTrue(result.isEmpty());
            verify(utenteRepository).findAll();
        }
    }

    @Nested
    @DisplayName("Test per il metodo getTutoratiByTutor")
    class GetTutoratiByTutorTest {

        @Test
        @DisplayName(" trovare tutti gli studenti di un tutor")
        void TrovaTuttiITutoratiDiUnTutor() {
            when(utenteRepository.findAll()).thenReturn(Arrays.asList(tutorMock, tutoratoMock, altroTutoratoMock));

            List<UtenteDTO> result = utenteService.getTutoratiByTutor(tutorMock.getId());

            assertEquals(2, result.size());
            assertTrue(result.stream().allMatch(u -> u.getTutorId().equals(tutorMock.getId())));
            verify(utenteRepository).findAll();
        }

        @Test
        @DisplayName("ritorna lista vuota per tutor senza studenti")
        void RitornaListaVuotaPerTutorSenzaStudenti() {
            when(utenteRepository.findAll()).thenReturn(Arrays.asList(tutorMock));

            List<UtenteDTO> result = utenteService.getTutoratiByTutor(tutorMock.getId());

            assertTrue(result.isEmpty());
            verify(utenteRepository).findAll();
        }

        @Test
        @DisplayName("ritorna lista vuota per tutor inesistente")
        void RitornaListaVuotaPerTutorInesistente() {
            when(utenteRepository.findAll()).thenReturn(Arrays.asList(tutorMock, tutoratoMock));

            List<UtenteDTO> result = utenteService.getTutoratiByTutor(999);

            assertTrue(result.isEmpty());
            verify(utenteRepository).findAll();
        }
    }

    @Nested
    @DisplayName("Test ")
    class EdgeCaseTest {

        @Test
        @DisplayName("gestisce repos che a sua volta ritorna lista vuota")
        void GestireRepositoryCheritornaListaVuota() {

            when(utenteRepository.findAll()).thenReturn(Arrays.asList());


            List<UtenteDTO> allUtenti = utenteService.getAllUtenti();
            List<UtenteDTO> tutoratiByTutor = utenteService.getTutoratiByTutor(1);
            Optional<UtenteDTO> byEmail = utenteService.findByEmail("test@email.com");


            assertTrue(allUtenti.isEmpty());
            assertTrue(tutoratiByTutor.isEmpty());
            assertFalse(byEmail.isPresent());
        }

        @Test
        @DisplayName("gestire studenti senza tutor")
        void GestioneStudnetisenzaTutor() throws Exception {

            Utente tutoratoSenzaTutor = new Utente();
            tutoratoSenzaTutor.setId(4);
            tutoratoSenzaTutor.setNome("Tizio");
            tutoratoSenzaTutor.setCognome("Caio");
            tutoratoSenzaTutor.setRuolo(Ruolo.TUTORATO);
            tutoratoSenzaTutor.setTutor(null);

            when(utenteRepository.findAll()).thenReturn(Arrays.asList(tutoratoSenzaTutor));


            List<UtenteDTO> tutoratiByTutor = utenteService.getTutoratiByTutor(1);
            UtenteDTO dto = utenteService.toUtenteDTO(tutoratoSenzaTutor);


            assertTrue(tutoratiByTutor.isEmpty());
            assertNull(dto.getTutorId());
        }
    }
}