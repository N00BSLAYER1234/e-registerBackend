package it.realeites.learning.registroelettronicobackend.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import it.realeites.learning.registroelettronicobackend.model.entity.Mese;
import it.realeites.learning.registroelettronicobackend.model.entity.Ruolo;
import it.realeites.learning.registroelettronicobackend.model.entity.Utente;
import it.realeites.learning.registroelettronicobackend.repository.MeseRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test per MeseServiceImpl")
class MeseServiceImplTest {

    @Mock
    private MeseRepository meseRepository;

    @InjectMocks
    private MeseServiceImpl meseService;

    private Mese meseMock;
    private Utente utenteMock;

    @BeforeEach
    void init() throws Exception {
        utenteMock = new Utente();
        utenteMock.setId(1);
        utenteMock.setNome("Mario");
        utenteMock.setCognome("Rossi");
        utenteMock.setEmail("mario.rossi@email.com");
        utenteMock.setUsername("mario.rossi");
        utenteMock.setPassword("password");
        utenteMock.setRuolo(Ruolo.TUTORATO);
        utenteMock.setDataNascita(LocalDate.of(1995, 1, 1));
        utenteMock.setIndirizzo("Via Roma 1");
        utenteMock.setCellulare("1234567890");

        meseMock = new Mese();
        meseMock.setId(1);
        meseMock.setNumeroMese(1);
        meseMock.setAnno(2024);
        meseMock.setUtente(utenteMock);
        meseMock.setMeseChiuso(false);
    }

    @Test
    @DisplayName("trova mese da is")
    void testFindById() {
        when(meseRepository.findById(1)).thenReturn(Optional.of(meseMock));

        Optional<Mese> result = meseService.findById(1);

        assertTrue(result.isPresent());
        assertEquals(meseMock.getId(), result.get().getId());
        verify(meseRepository).findById(1);
    }

    @Test
    @DisplayName("ritorna tutti i mesi")
    void testFindAll() {
        List<Mese> mesi = Arrays.asList(meseMock);
        when(meseRepository.findAll()).thenReturn(mesi);

        List<Mese> result = meseService.findAll();

        assertEquals(1, result.size());
        assertEquals(meseMock.getId(), result.get(0).getId());
        verify(meseRepository).findAll();
    }

    @Test
    @DisplayName("chiude un mese")
    void testChiudiMese() {
        when(meseRepository.findById(1)).thenReturn(Optional.of(meseMock));
        when(meseRepository.save(meseMock)).thenReturn(meseMock);

        Mese result = meseService.chiudiMese(1);

        assertTrue(result.isMeseChiuso());
        verify(meseRepository).findById(1);
        verify(meseRepository).save(meseMock);
    }

    }
