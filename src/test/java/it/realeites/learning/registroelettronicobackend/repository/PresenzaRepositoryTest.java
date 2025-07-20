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

import it.realeites.learning.registroelettronicobackend.model.entity.Mese;
import it.realeites.learning.registroelettronicobackend.model.entity.Presenza;
import it.realeites.learning.registroelettronicobackend.model.entity.Ruolo;
import it.realeites.learning.registroelettronicobackend.model.entity.Utente;

@DataJpaTest
@DisplayName("Test per PresenzaRepository")
class PresenzaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PresenzaRepository presenzaRepository;


    private Utente utenteMock;
    private Mese meseMock;
    private Presenza presenzaMock;

    @BeforeEach
    void setUp() throws Exception {
        utenteMock = new Utente();
        utenteMock.setNome("Mario");
        utenteMock.setCognome("Rossi");
        utenteMock.setUsername("mario.rossi");
        utenteMock.setEmail("mario@test.com");
        utenteMock.setPassword("password");
        utenteMock.setRuolo(Ruolo.TUTORATO);
        utenteMock.setDataNascita(LocalDate.of(1995, 1, 1));
        utenteMock.setIndirizzo("Via Roma 1");
        utenteMock.setCellulare("1234567890");
        utenteMock = entityManager.persistAndFlush(utenteMock);

        meseMock = new Mese();
        meseMock.setNumeroMese(1);
        meseMock.setAnno(2024);
        meseMock.setUtente(utenteMock);
        meseMock = entityManager.persistAndFlush(meseMock);

        presenzaMock = new Presenza();
        presenzaMock.setUtente(utenteMock);
        presenzaMock.setMese(meseMock);
        presenzaMock.setData(LocalDate.now());
        presenzaMock.setStato(true);
    }

    @Test
    @DisplayName("salva presenza")
    void testSavePresenza() {
        Presenza presenzaSalvata = presenzaRepository.save(presenzaMock);
        
        assertNotNull(presenzaSalvata.getId());
        assertTrue(presenzaSalvata.getStato());
    }

    @Test
    @DisplayName("trova presenza da id")
    void testFindPresenzaPerId() {
        Presenza presenzaSalvata = entityManager.persistAndFlush(presenzaMock);
        
        Optional<Presenza> presenzaRecuperata = presenzaRepository.findById(presenzaSalvata.getId());
        
        assertTrue(presenzaRecuperata.isPresent());
        assertEquals(presenzaSalvata.getId(), presenzaRecuperata.get().getId());
    }

    @Test
    @DisplayName("ritorna tutte le presenze")
    void testFindAllPresenze() {
        entityManager.persistAndFlush(presenzaMock);
        
        List<Presenza> presenze = presenzaRepository.findAll();
        
        assertFalse(presenze.isEmpty());
    }

    @Test
    @DisplayName("trova presenze per mese")
    void testFindByMeseId() {
        entityManager.persistAndFlush(presenzaMock);
        
        List<Presenza> presenze = presenzaRepository.findByMese_Id(meseMock.getId());
        
        assertEquals(1, presenze.size());
        assertEquals(meseMock.getId(), presenze.get(0).getMese().getId());
    }

    @Test
    @DisplayName("elimina presenza")
    void testDeletePresenza() {
        Presenza presenzaSalvata = entityManager.persistAndFlush(presenzaMock);
        Integer id = presenzaSalvata.getId();
        
        presenzaRepository.deleteById(id);
        entityManager.flush();
        
        Optional<Presenza> presenzaEliminata = presenzaRepository.findById(id);
        assertFalse(presenzaEliminata.isPresent());
    }
}