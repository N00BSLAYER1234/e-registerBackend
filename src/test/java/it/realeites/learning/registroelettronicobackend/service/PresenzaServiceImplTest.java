package it.realeites.learning.registroelettronicobackend.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import it.realeites.learning.registroelettronicobackend.model.dto.PresenzaDTO;
import it.realeites.learning.registroelettronicobackend.model.dto.PresenzaRequest;
import it.realeites.learning.registroelettronicobackend.model.dto.UtenteDTO;
import it.realeites.learning.registroelettronicobackend.model.entity.Giustificativo;
import it.realeites.learning.registroelettronicobackend.model.entity.Mese;
import it.realeites.learning.registroelettronicobackend.model.entity.Presenza;
import it.realeites.learning.registroelettronicobackend.model.entity.Ruolo;
import it.realeites.learning.registroelettronicobackend.model.entity.Utente;
import it.realeites.learning.registroelettronicobackend.repository.MeseRepository;
import it.realeites.learning.registroelettronicobackend.repository.PresenzaRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test per PresenzaServiceImpl")
class PresenzaServiceImplTest {

    @Mock
    private PresenzaRepository presenzaRepository;

    @Mock
    private UtenteService utenteService;


    @Mock
    private MeseRepository meseRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private PresenzaServiceImpl presenzaService;

    private Presenza presenzaMock;
    private Utente utenteMock;
    private UtenteDTO utenteDTOMock;
    private Mese meseMock;
    private PresenzaRequest presenzaRequestMock;
    private Giustificativo giustificativoMock;

    @BeforeEach
    void setUp() {

        utenteMock = new Utente();
        try {
            utenteMock.setId(1);
            utenteMock.setNome("Mario");
            utenteMock.setCognome("Rossi");
            utenteMock.setRuolo(Ruolo.TUTORATO);
            utenteMock.setEmail("mario@email.com");
        } catch (Exception e) {
            fail("errore nella creazione dell'utente mock: " + e.getMessage());
        }


        utenteDTOMock = new UtenteDTO();
        utenteDTOMock.setId(1);
        utenteDTOMock.setNome("Mario");
        utenteDTOMock.setCognome("Rossi");
        utenteDTOMock.setRuolo(Ruolo.TUTORATO);
        utenteDTOMock.setEmail("mario@email.com");


        meseMock = new Mese();
        meseMock.setId(1);
        meseMock.setNumeroMese(1);
        meseMock.setAnno(2024);
        meseMock.setMeseChiuso(false);
        meseMock.setUtente(utenteMock);


        presenzaMock = new Presenza();
        try {
            presenzaMock.setId(1);
        } catch (Exception e) {
            fail("Error setting presenzaMock id: " + e.getMessage());
        }
        presenzaMock.setData(LocalDate.of(2024, 1, 15));
        presenzaMock.setStato(true);
        presenzaMock.setApprovato(false);
        presenzaMock.setUtente(utenteMock);
        presenzaMock.setMese(meseMock);


        giustificativoMock = new Giustificativo();
        giustificativoMock.setId(1);
        giustificativoMock.setDescrizione("Motivi di salute");
        giustificativoMock.setAccettata(false);


        presenzaRequestMock = new PresenzaRequest();
        presenzaRequestMock.setData(LocalDate.of(2024, 1, 15));
        presenzaRequestMock.setStato(true);
        presenzaRequestMock.setUtente(utenteDTOMock);
    }

    @Nested
    @DisplayName("metodo findById")
    class FindByIdTest {

        @Test
        @DisplayName("trova presenza da un id che esiste")
        void TrovaPresenzaPerIdEsistente() {

            Integer id = 1;
            when(presenzaRepository.findById(id)).thenReturn(Optional.of(presenzaMock));
            when(utenteService.toUtenteDTO(utenteMock)).thenReturn(utenteDTOMock);


            PresenzaDTO risultato = presenzaService.findById(id);


            assertNotNull(risultato);
            assertEquals(presenzaMock.getId(), risultato.getId());
            assertEquals(presenzaMock.getData(), risultato.getData());
            assertEquals(presenzaMock.getStato(), risultato.getStato());
            assertEquals(presenzaMock.isApprovato(), risultato.isApprovato());
            verify(presenzaRepository, times(1)).findById(id);
        }

        @Test
        @DisplayName("lancia errore per id insestitente ")
        void lanciaErroreIdInesistente() {

            Integer id = 999;
            when(presenzaRepository.findById(id)).thenReturn(Optional.empty());

            EntityNotFoundException eccezione = assertThrows(EntityNotFoundException.class, () -> {
                presenzaService.findById(id);
            });

            assertEquals("Presenza non trovata con id: " + id, eccezione.getMessage());
            verify(presenzaRepository, times(1)).findById(id);
        }
    }

    @Nested
    @DisplayName("findAll")
    class FindAllTest {

        @Test
        @DisplayName("ritorna tutte le presenze")
        void ritornaTutteLePresenze() {

            List<Presenza> presenze = Arrays.asList(presenzaMock);
            when(presenzaRepository.findAll()).thenReturn(presenze);
            when(utenteService.toUtenteDTO(utenteMock)).thenReturn(utenteDTOMock);


            List<PresenzaDTO> risultato = presenzaService.findAll();


            assertEquals(1, risultato.size());
            assertEquals(presenzaMock.getId(), risultato.get(0).getId());
            verify(presenzaRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("ritonra lista vuota quando non ci sono presenze")
        void ritornaListaVuotaSenzaPresenze() {
    
            when(presenzaRepository.findAll()).thenReturn(Arrays.asList());

            List<PresenzaDTO> risultato = presenzaService.findAll();

            assertTrue(risultato.isEmpty());
            verify(presenzaRepository, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("Test per il metodo save")
    class SaveTest {

        @Test
        @DisplayName("salva una presenza valida")
        void salvaPresenzaValida() {
    
            when(utenteService.findEntityById(1)).thenReturn(Optional.of(utenteMock));
            when(presenzaRepository.findByUtenteIdAndData(1, LocalDate.of(2024, 1, 15)))
                    .thenReturn(Optional.empty());
            when(meseRepository.findByUtente_IdAndNumeroMese(1, 1)).thenReturn(Optional.of(meseMock));
            when(presenzaRepository.save(any(Presenza.class))).thenReturn(presenzaMock);
            when(utenteService.toUtenteDTO(utenteMock)).thenReturn(utenteDTOMock);

            PresenzaDTO risultato = presenzaService.save(presenzaRequestMock);

            assertNotNull(risultato);
            assertEquals(presenzaMock.getId(), risultato.getId());
            verify(presenzaRepository, times(1)).save(any(Presenza.class));
        }

        @Test
        @DisplayName("lancia eccezione se l'utente non esiste")
        void lanciaEccezionePerUtenteNonTrovato() {
    
            when(utenteService.findEntityById(1)).thenReturn(Optional.empty());

            IllegalArgumentException eccezione = assertThrows(IllegalArgumentException.class, () -> {
                presenzaService.save(presenzaRequestMock);
            });

            assertEquals("Utente non trovato", eccezione.getMessage());
            verify(presenzaRepository, never()).save(any(Presenza.class));
        }

        @Test
        @DisplayName("lancia eccezione per presenza già presente")
        void laniaEccezionePerPresenzaGiaPresente() {
    
            when(utenteService.findEntityById(1)).thenReturn(Optional.of(utenteMock));
            when(presenzaRepository.findByUtenteIdAndData(1, LocalDate.of(2024, 1, 15)))
                    .thenReturn(Optional.of(presenzaMock));

            IllegalArgumentException eccezione = assertThrows(IllegalArgumentException.class, () -> {
                presenzaService.save(presenzaRequestMock);
            });

            assertEquals("Presenza già inserita per questa data", eccezione.getMessage());
            verify(presenzaRepository, never()).save(any(Presenza.class));
        }

        @Test
        @DisplayName("lancia eccezione per utente tutor")
        void lanciaEccezioneUtenteTutor() {
    
            utenteDTOMock.setRuolo(Ruolo.TUTOR);
            when(utenteService.findEntityById(1)).thenReturn(Optional.of(utenteMock));
            when(presenzaRepository.findByUtenteIdAndData(1, LocalDate.of(2024, 1, 15)))
                    .thenReturn(Optional.empty());

            IllegalArgumentException eccezione = assertThrows(IllegalArgumentException.class, () -> {
                presenzaService.save(presenzaRequestMock);
            });

            assertEquals("Solo i tutorati possono aggiungere una presenza!", eccezione.getMessage());
            verify(presenzaRepository, never()).save(any(Presenza.class));
        }

        @Test
        @DisplayName("salva assenza con giustificativo valido")
        void salvaAssenzaConGiustificativoValido() {
    
            presenzaRequestMock.setStato(false);
            presenzaRequestMock.setGiustificativo(giustificativoMock);
            
            when(utenteService.findEntityById(1)).thenReturn(Optional.of(utenteMock));
            when(presenzaRepository.findByUtenteIdAndData(1, LocalDate.of(2024, 1, 15)))
                    .thenReturn(Optional.empty());
            when(meseRepository.findByUtente_IdAndNumeroMese(1, 1)).thenReturn(Optional.of(meseMock));
            when(presenzaRepository.save(any(Presenza.class))).thenReturn(presenzaMock);
            when(utenteService.toUtenteDTO(utenteMock)).thenReturn(utenteDTOMock);

            PresenzaDTO risultato = presenzaService.save(presenzaRequestMock);

            assertNotNull(risultato);
            verify(presenzaRepository, times(1)).save(any(Presenza.class));
        }

        @Test
        @DisplayName("salva assenza senza giustificativo")
        void salvaAssenzaSenzaGiustificativo() {
    
            presenzaRequestMock.setStato(false);
            presenzaRequestMock.setGiustificativo(null);
            
            when(utenteService.findEntityById(1)).thenReturn(Optional.of(utenteMock));
            when(presenzaRepository.findByUtenteIdAndData(1, LocalDate.of(2024, 1, 15)))
                    .thenReturn(Optional.empty());
            when(meseRepository.findByUtente_IdAndNumeroMese(1, 1)).thenReturn(Optional.of(meseMock));
            when(presenzaRepository.save(any(Presenza.class))).thenReturn(presenzaMock);
            when(utenteService.toUtenteDTO(utenteMock)).thenReturn(utenteDTOMock);

            PresenzaDTO risultato = presenzaService.save(presenzaRequestMock);

            assertNotNull(risultato);
            verify(presenzaRepository, times(1)).save(any(Presenza.class));
        }

        @Test
        @DisplayName("lancia eccezione mese chiuso")
        void lanciaEccezioneConMeseChiuso() {
    
            meseMock.setMeseChiuso(true);
            
            when(utenteService.findEntityById(1)).thenReturn(Optional.of(utenteMock));
            when(presenzaRepository.findByUtenteIdAndData(1, LocalDate.of(2024, 1, 15)))
                    .thenReturn(Optional.empty());
            when(meseRepository.findByUtente_IdAndNumeroMese(1, 1)).thenReturn(Optional.of(meseMock));

            RuntimeException eccezione = assertThrows(RuntimeException.class, () -> {
                presenzaService.save(presenzaRequestMock);
            });

            assertEquals("Non è possibile inserire una presenza: il mese è chiuso", eccezione.getMessage());
            verify(presenzaRepository, never()).save(any(Presenza.class));
        }

        @Test
        @DisplayName("crea nuovo mese se non esiste")
        void CreareNuovoMeseSeNonEsiste() {
    
            when(utenteService.findEntityById(1)).thenReturn(Optional.of(utenteMock));
            when(presenzaRepository.findByUtenteIdAndData(1, LocalDate.of(2024, 1, 15)))
                    .thenReturn(Optional.empty());
            when(meseRepository.findByUtente_IdAndNumeroMese(1, 1)).thenReturn(Optional.empty());
            when(meseRepository.save(any(Mese.class))).thenReturn(meseMock);
            when(presenzaRepository.save(any(Presenza.class))).thenReturn(presenzaMock);
            when(utenteService.toUtenteDTO(utenteMock)).thenReturn(utenteDTOMock);

            PresenzaDTO risultato = presenzaService.save(presenzaRequestMock);

            assertNotNull(risultato);
            verify(meseRepository, times(1)).save(any(Mese.class));
            verify(presenzaRepository, times(1)).save(any(Presenza.class));
        }
    }

    @Nested
    @DisplayName("updatePresenza")
    class UpdatePresenzaTest {

        @Test
        @DisplayName("aggiorna presenza da assente a presente")
        void aggiornaPresenzaDaAssenteAPresente() {
    
            presenzaMock.setStato(false);
            presenzaMock.setApprovato(false);
            
            when(presenzaRepository.findById(1)).thenReturn(Optional.of(presenzaMock));
            when(presenzaRepository.save(any(Presenza.class))).thenReturn(presenzaMock);
            when(utenteService.toUtenteDTO(utenteMock)).thenReturn(utenteDTOMock);

            PresenzaDTO risultato = presenzaService.updatePresenza(1);

            assertNotNull(risultato);
            assertTrue(presenzaMock.getStato());
            verify(presenzaRepository, times(1)).save(presenzaMock);
        }

        @Test
        @DisplayName("lancia eccezione per presenza non trovata")
        void LanciaEccezionePerPresenzaNonTrovata() {
    
            when(presenzaRepository.findById(1)).thenReturn(Optional.empty());

            IllegalArgumentException eccezione = assertThrows(IllegalArgumentException.class, () -> {
                presenzaService.updatePresenza(1);
            });

            assertEquals("Presenza non trovata", eccezione.getMessage());
            verify(presenzaRepository, never()).save(any(Presenza.class));
        }

        @Test
        @DisplayName("lancia eccezione per presenza già approvata")
        void lanciaEccezionePerPresenzaGiaApprovata() {
    
            presenzaMock.setStato(false);
            presenzaMock.setApprovato(true);
            
            when(presenzaRepository.findById(1)).thenReturn(Optional.of(presenzaMock));

            IllegalStateException eccezione = assertThrows(IllegalStateException.class, () -> {
                presenzaService.updatePresenza(1);
            });

            assertEquals("Presenza già approvata", eccezione.getMessage());
            verify(presenzaRepository, never()).save(any(Presenza.class));
        }

        @Test
        @DisplayName("lancia eccezione per presenza già registrata")
        void lanciaEccezionePerPresenzaGiaRegistrata() {
    
            presenzaMock.setStato(true);
            
            when(presenzaRepository.findById(1)).thenReturn(Optional.of(presenzaMock));

            IllegalStateException eccezione = assertThrows(IllegalStateException.class, () -> {
                presenzaService.updatePresenza(1);
            });

            assertEquals("Presenza già registrata", eccezione.getMessage());
            verify(presenzaRepository, never()).save(any(Presenza.class));
        }
    }

    @Nested
    @DisplayName("metodo deleteById")
    class DeleteByIdTest {

        @Test
        @DisplayName("elimina presenza esistente")
        void dovrebbeEliminarePresenzaEsistente() {
    
            Integer id = 1;
            when(presenzaRepository.findById(id)).thenReturn(Optional.of(presenzaMock));

            presenzaService.deleteById(id);

            verify(presenzaRepository, times(1)).findById(id);
            verify(presenzaRepository, times(1)).deleteById(id);
        }

        @Test
        @DisplayName("lancia eccezione per presenza non trovata")
        void lanciaEccezionePerPresenzaNonTrovata() {
    
            Integer id = 999;
            when(presenzaRepository.findById(id)).thenReturn(Optional.empty());

            IllegalArgumentException eccezione = assertThrows(IllegalArgumentException.class, () -> {
                presenzaService.deleteById(id);
            });

            assertEquals("Presenza non trovata", eccezione.getMessage());
            verify(presenzaRepository, never()).deleteById(id);
        }
    }

    @Nested
    @DisplayName("metodo setApprovato")
    class SetApprovatoTest {

        @Test
        @DisplayName("imposta presenza come approvata")
        void impostaPresenzaComeApprovata() {
    
            Integer id = 1;
            Integer tutorId = 2;
            Utente tutorMock = new Utente();
            try {
                tutorMock.setId(tutorId);
                utenteMock.setTutor(tutorMock);
            } catch (Exception e) {
                fail("Error setting up test data: " + e.getMessage());
            }
            
            when(presenzaRepository.findById(id)).thenReturn(Optional.of(presenzaMock));
            when(utenteService.findEntityById(tutorId)).thenReturn(Optional.of(tutorMock));
            when(presenzaRepository.save(any(Presenza.class))).thenReturn(presenzaMock);

            presenzaService.setApprovato(id, tutorId);

            assertTrue(presenzaMock.isApprovato());
            verify(presenzaRepository, times(1)).save(presenzaMock);
        }

        @Test
        @DisplayName("lancia eccezione per presenza non trovata")
        void lanciaEccezionePresenzaNonTrovata() {
    
            Integer id = 999;
            Integer tutorId = 2;
            when(presenzaRepository.findById(id)).thenReturn(Optional.empty());

            EntityNotFoundException eccezione = assertThrows(EntityNotFoundException.class, () -> {
                presenzaService.setApprovato(id, tutorId);
            });

            assertEquals("Presenza non trovata con id: " + id, eccezione.getMessage());
            verify(presenzaRepository, never()).save(any(Presenza.class));
        }
    }

    @Nested
    @DisplayName("Test per il metodo deleteApprovato")
    class DeleteApprovatoTest {

        @Test
        @DisplayName("rimuove approvazione dalla presenza")
        void rimuoveApprovazioneDallaPresenza() {
    
            Integer id = 1;
            presenzaMock.setApprovato(true);
            when(presenzaRepository.findById(id)).thenReturn(Optional.of(presenzaMock));
            when(presenzaRepository.save(any(Presenza.class))).thenReturn(presenzaMock);

            presenzaService.deleteApprovato(id);

            assertFalse(presenzaMock.isApprovato());
            verify(presenzaRepository, times(1)).save(presenzaMock);
        }

        @Test
        @DisplayName("lancia per presenza non trovata")
        void lanciaEccezionePresenzaNonTrovata() {
    
            Integer id = 999;
            when(presenzaRepository.findById(id)).thenReturn(Optional.empty());

            EntityNotFoundException eccezione = assertThrows(EntityNotFoundException.class, () -> {
                presenzaService.deleteApprovato(id);
            });

            assertEquals("Presenza non trovata con id: " + id, eccezione.getMessage());
            verify(presenzaRepository, never()).save(any(Presenza.class));
        }
    }

    @Nested
    @DisplayName("Test per il metodo findAllByMese_Id")
    class FindAllByMeseIdTest {

        @Test
        @DisplayName("trova presenze per un determinato mese")
        void trovaPresenzePerMeseSpecifico() {
    
            Integer idMese = 1;
            when(presenzaRepository.findByMese_Id(idMese)).thenReturn(Arrays.asList(presenzaMock));
            when(utenteService.toUtenteDTO(utenteMock)).thenReturn(utenteDTOMock);

            List<PresenzaDTO> risultato = presenzaService.findAllByMese_Id(idMese);

            assertEquals(1, risultato.size());
            assertEquals(presenzaMock.getId(), risultato.get(0).getId());
            verify(presenzaRepository, times(1)).findByMese_Id(idMese);
        }

        @Test
        @DisplayName("ritorna lista vuota per mese senza presenze")
        void ritornaListaVuotaPerMeseSenzaPresenze() {
    
            Integer idMese = 999;
            when(presenzaRepository.findByMese_Id(idMese)).thenReturn(Arrays.asList());

            List<PresenzaDTO> risultato = presenzaService.findAllByMese_Id(idMese);

            assertTrue(risultato.isEmpty());
            verify(presenzaRepository, times(1)).findByMese_Id(idMese);
        }
    }

    @Nested
    @DisplayName("metodo controllaPresenzeEUtente")
    class ControllaPresenzeEUtenteTest {

        @Test
        @DisplayName("invia mail sudente con troppe assenze")
        void inviaEmailUtentConTroppeAssenze() {
    
            Integer utenteId = 1;
            Integer idMese = 1;
            
            when(utenteService.findById(utenteId)).thenReturn(Optional.of(utenteDTOMock));
            when(presenzaRepository.countAssenzeNonGiustificateByUtenteIdAndMese(utenteId, idMese)).thenReturn(4);

            presenzaService.controllaPresenzeEUtente(utenteId, idMese);

            verify(emailService, times(1)).inviaMailAvvisoPresenze(utenteDTOMock, 4);
        }

        @Test
        @DisplayName("non invia email con un utente con poche assenze")
        void nonInviaMailStudentePocheAssenze() {
    
            Integer utenteId = 1;
            Integer idMese = 1;
            
            when(utenteService.findById(utenteId)).thenReturn(Optional.of(utenteDTOMock));
            when(presenzaRepository.countAssenzeNonGiustificateByUtenteIdAndMese(utenteId, idMese)).thenReturn(2);

            presenzaService.controllaPresenzeEUtente(utenteId, idMese);

            verify(emailService, never()).inviaMailAvvisoPresenze(any(), anyInt());
        }

        @Test
        @DisplayName("lancia eccezzione per utente non trovato")
        void lanciaEccezioneUtenteNonTrovato() {
    
            Integer utenteId = 999;
            Integer idMese = 1;
            when(utenteService.findById(utenteId)).thenReturn(Optional.empty());

            EntityNotFoundException eccezione = assertThrows(EntityNotFoundException.class, () -> {
                presenzaService.controllaPresenzeEUtente(utenteId, idMese);
            });

            assertEquals("Utente non trovato con id: " + utenteId, eccezione.getMessage());
            verify(emailService, never()).inviaMailAvvisoPresenze(any(), anyInt());
        }
    }

}
