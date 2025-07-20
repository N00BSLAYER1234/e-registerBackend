package it.realeites.learning.registroelettronicobackend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import it.realeites.learning.registroelettronicobackend.model.entity.Ruolo;
import it.realeites.learning.registroelettronicobackend.model.entity.Utente;

@DataJpaTest
@DisplayName("Test UtenteRepo")
class UtenteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UtenteRepository utenteRepository;

    private Utente tutorMock;
    private Utente tutoratoMock;

    @BeforeEach
    void setUp() throws Exception {
        tutorMock = new Utente();
        tutorMock.setNome("Mario");
        tutorMock.setCognome("Rossi");
        tutorMock.setUsername("mario.rossi");
        tutorMock.setEmail("mario@test.com");
        tutorMock.setPassword("password");
        tutorMock.setRuolo(Ruolo.TUTOR);
        tutorMock.setDataNascita(LocalDate.of(1980, 1, 1));
        tutorMock.setIndirizzo("Via Roma 1");
        tutorMock.setCellulare("1234567890");

        tutoratoMock = new Utente();
        tutoratoMock.setNome("Luca");
        tutoratoMock.setCognome("Bianchi");
        tutoratoMock.setUsername("luca.bianchi");
        tutoratoMock.setEmail("luca@test.com");
        tutoratoMock.setPassword("password");
        tutoratoMock.setRuolo(Ruolo.TUTORATO);
        tutoratoMock.setDataNascita(LocalDate.of(1995, 2, 2));
        tutoratoMock.setIndirizzo("Via Milano 2");
        tutoratoMock.setCellulare("0987654321");
        tutoratoMock.setTutor(tutorMock);
    }

    @Test
    @DisplayName("salva un utente")
    void testSalvaUtente() {
        Utente utenteSalvato = utenteRepository.save(tutorMock);
        
        assertNotNull(utenteSalvato.getId());
        assertEquals("Mario", utenteSalvato.getNome());
        assertEquals("Rossi", utenteSalvato.getCognome());
    }

    @Test
    @DisplayName("trova utente da l'id")
    void testTrovaUtenteDaId() {
        Utente utenteSalvato = entityManager.persistAndFlush(tutorMock);
        
        Optional<Utente> utenteRecuperato = utenteRepository.findById(utenteSalvato.getId());
        
        assertTrue(utenteRecuperato.isPresent());
        assertEquals("Mario", utenteRecuperato.get().getNome());
    }

    @Test
    @DisplayName("ritorna tutti gli utenti")
    void testTrovaTuttiUtenti() {
        entityManager.persistAndFlush(tutorMock);
        entityManager.persistAndFlush(tutoratoMock);
        
        List<Utente> utenti = utenteRepository.findAll();
        
        assertEquals(2, utenti.size());
    }

    @Test
    @DisplayName("elimina utente")
    void testEliminaUtente() {
        Utente utenteSalvato = entityManager.persistAndFlush(tutorMock);
        Integer id = utenteSalvato.getId();
        
        utenteRepository.deleteById(id);
        entityManager.flush();
        
        Optional<Utente> utenteEliminato = utenteRepository.findById(id);
        assertFalse(utenteEliminato.isPresent());
    }
}