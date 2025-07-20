package it.realeites.learning.registroelettronicobackend.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import it.realeites.learning.registroelettronicobackend.model.dto.UtenteDTO;
import it.realeites.learning.registroelettronicobackend.model.entity.Ruolo;
import it.realeites.learning.registroelettronicobackend.service.UtenteService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test per UtenteREST Controller")
class UtenteRESTTest {

    @Mock
    private UtenteService utenteService;

    @InjectMocks
    private UtenteREST utenteREST;

    private MockMvc mockMvc;

    private UtenteDTO tutorDTO;
    private UtenteDTO tutoratoDTO1;
    private UtenteDTO tutoratoDTO2;
    private List<UtenteDTO> tuttiGliUtenti;
    private List<UtenteDTO> soloTutor;
    private List<UtenteDTO> soloTutorati;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(utenteREST).build();
        
        tutorDTO = new UtenteDTO();
        tutorDTO.setId(1);
        tutorDTO.setNome("Marco");
        tutorDTO.setCognome("Bianchi");
        tutorDTO.setDataNascita(LocalDate.of(1980, 5, 15));
        tutorDTO.setEmail("marco.bianchi@realeites.com");
        tutorDTO.setUsername("marco.bianchi");
        tutorDTO.setRuolo(Ruolo.TUTOR);
        tutorDTO.setTutorId(null);

        tutoratoDTO1 = new UtenteDTO();
        tutoratoDTO1.setId(2);
        tutoratoDTO1.setNome("Mario");
        tutoratoDTO1.setCognome("Rossi");
        tutoratoDTO1.setDataNascita(LocalDate.of(1995, 3, 10));
        tutoratoDTO1.setEmail("mario.rossi@email.com");
        tutoratoDTO1.setUsername("mario.rossi");
        tutoratoDTO1.setRuolo(Ruolo.TUTORATO);
        tutoratoDTO1.setTutorId(1);

        tutoratoDTO2 = new UtenteDTO();
        tutoratoDTO2.setId(3);
        tutoratoDTO2.setNome("Anna");
        tutoratoDTO2.setCognome("Verdi");
        tutoratoDTO2.setDataNascita(LocalDate.of(1996, 7, 20));
        tutoratoDTO2.setEmail("anna.verdi@email.com");
        tutoratoDTO2.setUsername("anna.verdi");
        tutoratoDTO2.setRuolo(Ruolo.TUTORATO);
        tutoratoDTO2.setTutorId(1);

        tuttiGliUtenti = Arrays.asList(tutorDTO, tutoratoDTO1, tutoratoDTO2);
        soloTutor = Arrays.asList(tutorDTO);
        soloTutorati = Arrays.asList(tutoratoDTO1, tutoratoDTO2);
    }

    @Nested
    @DisplayName("Test per GET /api/utenti")
    class TestGetAllUsers {

        @Test
        @DisplayName("restituisce   200 OK  con lista di utenti quando ci sono utenti")
        void ritornaDuecentoOk() throws Exception {
            when(utenteService.getAllUtenti()).thenReturn(tuttiGliUtenti);

            mockMvc.perform(get("/api/utenti")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()").value(3))
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].nome").value("Marco"))
                    .andExpect(jsonPath("$[0].cognome").value("Bianchi"))
                    .andExpect(jsonPath("$[0].ruolo").value("TUTOR"))
                    .andExpect(jsonPath("$[1].id").value(2))
                    .andExpect(jsonPath("$[1].nome").value("Mario"))
                    .andExpect(jsonPath("$[1].ruolo").value("TUTORATO"))
                    .andExpect(jsonPath("$[2].id").value(3))
                    .andExpect(jsonPath("$[2].nome").value("Anna"));

            verify(utenteService).getAllUtenti();
        }

        @Test
        @DisplayName("ritorna    404 NOT FOUND     quando NON ci sono utenti")
        void Ritorna404() throws Exception {
            when(utenteService.getAllUtenti()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/utenti")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());

            verify(utenteService).getAllUtenti();
        }
    }

    @Nested
    @DisplayName("Test per GET /api/tutor")
    class TestGetTutor {

        @Test
        @DisplayName("ritorna    200 OK    con lista di tutor quando ci sono tutor")
        void ritorna200ConTutor() throws Exception {
            when(utenteService.findByRuolo(Ruolo.TUTOR)).thenReturn(soloTutor);

            mockMvc.perform(get("/api/tutor")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()").value(1))
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].nome").value("Marco"))
                    .andExpect(jsonPath("$[0].cognome").value("Bianchi"))
                    .andExpect(jsonPath("$[0].ruolo").value("TUTOR"))
                    .andExpect(jsonPath("$[0].tutorId").isEmpty());

            verify(utenteService).findByRuolo(Ruolo.TUTOR);
        }

        @Test
        @DisplayName("ritorna   404 NOT FOUND    quando non ci sono tutor")
        void ritorna404SenzaTutor() throws Exception {
            when(utenteService.findByRuolo(Ruolo.TUTOR)).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/tutor")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());

            verify(utenteService).findByRuolo(Ruolo.TUTOR);
        }
    }

    @Nested
    @DisplayName("Test per i  GET di /api/tutorati")
    class TestGetTutorati {

        @Test
        @DisplayName("ritorna  200 OK quando ci sono gli studenti")
        void ritorna200ConStudenti() throws Exception {
            when(utenteService.findByRuolo(Ruolo.TUTORATO)).thenReturn(soloTutorati);

            mockMvc.perform(get("/api/tutorati")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$[0].id").value(2))
                    .andExpect(jsonPath("$[0].nome").value("Mario"))
                    .andExpect(jsonPath("$[0].cognome").value("Rossi"))
                    .andExpect(jsonPath("$[0].ruolo").value("TUTORATO"))
                    .andExpect(jsonPath("$[0].tutorId").value(1))
                    .andExpect(jsonPath("$[1].id").value(3))
                    .andExpect(jsonPath("$[1].nome").value("Anna"))
                    .andExpect(jsonPath("$[1].ruolo").value("TUTORATO"))
                    .andExpect(jsonPath("$[1].tutorId").value(1));

            verify(utenteService).findByRuolo(Ruolo.TUTORATO);
        }

        @Test
        @DisplayName("ritorna   404 NOT FOUND   quando non ci sono studenti")
        void ritorna400SenzaStudenti() throws Exception {
            when(utenteService.findByRuolo(Ruolo.TUTORATO)).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/tutorati")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());

            verify(utenteService).findByRuolo(Ruolo.TUTORATO);
        }
    }

    @Nested
    @DisplayName("Test per GET /api/tutor/{tutorId}/tutorati")
    class TestGetTutoratiByTutor {

        @Test
        @DisplayName("ritorna 200 OK con la lista degli studenti per il tutor esistente")
        void ritorna200ListaStudentiPerTutor() throws Exception {
            Integer tutorId = 1;
            when(utenteService.getTutoratiByTutor(tutorId)).thenReturn(soloTutorati);

            mockMvc.perform(get("/api/tutor/{tutorId}/tutorati", tutorId)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$[0].id").value(2))
                    .andExpect(jsonPath("$[0].nome").value("Mario"))
                    .andExpect(jsonPath("$[0].tutorId").value(1))
                    .andExpect(jsonPath("$[1].id").value(3))
                    .andExpect(jsonPath("$[1].nome").value("Anna"))
                    .andExpect(jsonPath("$[1].tutorId").value(1));

            verify(utenteService).getTutoratiByTutor(tutorId);
        }

        @Test
        @DisplayName("ritorna 404 NOT FOUND quando tutor non ha studenti")
        void ritorna400TutorSenzaStudenti() throws Exception {
            Integer tutorId = 2;
            when(utenteService.getTutoratiByTutor(tutorId)).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/tutor/{tutorId}/tutorati", tutorId)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());

            verify(utenteService).getTutoratiByTutor(tutorId);
        }

        @Test
        @DisplayName("ritorna 404 NOT FOUND per tutor inesistente")
        void ritorna400TutorInestistente () throws Exception {
            Integer tutorIdInesistente = 999;
            when(utenteService.getTutoratiByTutor(tutorIdInesistente)).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/tutor/{tutorId}/tutorati", tutorIdInesistente)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());

            verify(utenteService).getTutoratiByTutor(tutorIdInesistente);
        }

        @Test
        @DisplayName("gestisce path variable con un id negativo")
        void PathVariableConIdNegativo() throws Exception {
            Integer tutorIdNegativo = -1;
            when(utenteService.getTutoratiByTutor(tutorIdNegativo)).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/tutor/{tutorId}/tutorati", tutorIdNegativo)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());

            verify(utenteService).getTutoratiByTutor(tutorIdNegativo);
        }
    }

    @Nested
    @DisplayName("validazione dei parametri")
    class TestValidazioneParametri {

        @Test
        @DisplayName("gestisce path variable id non numerico con err 400")
        void PathVariableErr400() throws Exception {
            mockMvc.perform(get("/api/tutor/abc/tutorati")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(utenteService, never()).getTutoratiByTutor(any());
        }
    }

    @Nested
    @DisplayName("scenari di integrazione")
    class TestScenariIntegrazione {

        @Test
        @DisplayName("verifica che gli endpoint chiamino il service corretto")
        void CallCorrectEndpointForService() throws Exception {
            when(utenteService.getAllUtenti()).thenReturn(tuttiGliUtenti);
            when(utenteService.findByRuolo(Ruolo.TUTOR)).thenReturn(soloTutor);
            when(utenteService.findByRuolo(Ruolo.TUTORATO)).thenReturn(soloTutorati);
            when(utenteService.getTutoratiByTutor(1)).thenReturn(soloTutorati);

            mockMvc.perform(get("/api/utenti")).andExpect(status().isOk());
            mockMvc.perform(get("/api/tutor")).andExpect(status().isOk());
            mockMvc.perform(get("/api/tutorati")).andExpect(status().isOk());
            mockMvc.perform(get("/api/tutor/1/tutorati")).andExpect(status().isOk());

            verify(utenteService).getAllUtenti();
            verify(utenteService).findByRuolo(Ruolo.TUTOR);
            verify(utenteService).findByRuolo(Ruolo.TUTORATO);
            verify(utenteService).getTutoratiByTutor(1);
        }

        @Test
        @DisplayName("gestisce i content-type e accept headers")
        void HandlesContenTypeAcceptHeader() throws Exception {
            when(utenteService.getAllUtenti()).thenReturn(tuttiGliUtenti);

            mockMvc.perform(get("/api/utenti")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("ritorna JSON per tutti gli endpoint")
        void ritornaJSONEndpoint() throws Exception {
            when(utenteService.getAllUtenti()).thenReturn(tuttiGliUtenti);
            when(utenteService.findByRuolo(Ruolo.TUTOR)).thenReturn(soloTutor);
            when(utenteService.findByRuolo(Ruolo.TUTORATO)).thenReturn(soloTutorati);
            when(utenteService.getTutoratiByTutor(1)).thenReturn(soloTutorati);

            mockMvc.perform(get("/api/utenti"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray());

            mockMvc.perform(get("/api/tutor"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray());

            mockMvc.perform(get("/api/tutorati"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray());

            mockMvc.perform(get("/api/tutor/1/tutorati"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray());
        }
    }
}
