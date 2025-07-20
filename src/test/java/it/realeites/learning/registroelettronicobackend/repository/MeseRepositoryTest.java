package it.realeites.learning.registroelettronicobackend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import it.realeites.learning.registroelettronicobackend.model.entity.Mese;
import it.realeites.learning.registroelettronicobackend.model.entity.Ruolo;
import it.realeites.learning.registroelettronicobackend.model.entity.Utente;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Test per MeseRepository")
class MeseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MeseRepository meseRepository;

    private Utente tutorMock;
    private Utente tutoratoMock;
    private Utente altroTutoratoMock;
    private Mese meseGennaio;
    private Mese meseFebbraio;
    private Mese meseMarzo;

    @BeforeEach
    void setUp() throws Exception {
        tutorMock = new Utente();
        tutorMock.setNome("Marco");
        tutorMock.setCognome("Bianchi");
        tutorMock.setDataNascita(LocalDate.of(1980, 5, 15));
        tutorMock.setIndirizzo("Via Roma 123");
        tutorMock.setCellulare("3331234567");
        tutorMock.setEmail("marco.bianchi@realeites.com");
        tutorMock.setUsername("marco.bianchi");
        tutorMock.setPassword("password123");
        tutorMock.setRuolo(Ruolo.TUTOR);
        tutorMock.setTutor(null);
        tutorMock = entityManager.persistAndFlush(tutorMock);

        tutoratoMock = new Utente();
        tutoratoMock.setNome("Mario");
        tutoratoMock.setCognome("Rossi");
        tutoratoMock.setDataNascita(LocalDate.of(1995, 3, 10));
        tutoratoMock.setIndirizzo("Via Milano 456");
        tutoratoMock.setCellulare("3337654321");
        tutoratoMock.setEmail("mario.rossi@email.com");
        tutoratoMock.setUsername("mario.rossi");
        tutoratoMock.setPassword("password456");
        tutoratoMock.setRuolo(Ruolo.TUTORATO);
        tutoratoMock.setTutor(tutorMock);
        tutoratoMock = entityManager.persistAndFlush(tutoratoMock);

        altroTutoratoMock = new Utente();
        altroTutoratoMock.setNome("Anna");
        altroTutoratoMock.setCognome("Verdi");
        altroTutoratoMock.setDataNascita(LocalDate.of(1996, 7, 20));
        altroTutoratoMock.setIndirizzo("Via Torino 789");
        altroTutoratoMock.setCellulare("3339876543");
        altroTutoratoMock.setEmail("anna.verdi@email.com");
        altroTutoratoMock.setUsername("anna.verdi");
        altroTutoratoMock.setPassword("password789");
        altroTutoratoMock.setRuolo(Ruolo.TUTORATO);
        altroTutoratoMock.setTutor(tutorMock);
        altroTutoratoMock = entityManager.persistAndFlush(altroTutoratoMock);

        meseGennaio = new Mese();
        meseGennaio.setNumeroMese(1);
        meseGennaio.setAnno(2024);
        meseGennaio.setMeseChiuso(false);
        meseGennaio.setUtente(tutoratoMock);
        meseGennaio = entityManager.persistAndFlush(meseGennaio);

        meseFebbraio = new Mese();
        meseFebbraio.setNumeroMese(2);
        meseFebbraio.setAnno(2024);
        meseFebbraio.setMeseChiuso(true);
        meseFebbraio.setUtente(tutoratoMock);
        meseFebbraio = entityManager.persistAndFlush(meseFebbraio);

        meseMarzo = new Mese();
        meseMarzo.setNumeroMese(3);
        meseMarzo.setAnno(2024);
        meseMarzo.setMeseChiuso(false);
        meseMarzo.setUtente(altroTutoratoMock);
        meseMarzo = entityManager.persistAndFlush(meseMarzo);

        entityManager.clear();
    }

    @Nested
    @DisplayName("Test per i metodi crud base")
    class CrudBaseTest {

        @Test
        @DisplayName("salva un nuovo mese")
        void salvaNuovoMese() {
            Mese nuovoMese = new Mese();
            nuovoMese.setNumeroMese(4);
            nuovoMese.setAnno(2024);
            nuovoMese.setMeseChiuso(false);
            nuovoMese.setUtente(tutoratoMock);

            Mese meseSalvato = meseRepository.save(nuovoMese);

            assertNotNull(meseSalvato);
            assertNotNull(meseSalvato.getId());
            assertEquals(Integer.valueOf(4), meseSalvato.getNumeroMese());
            assertEquals(Integer.valueOf(2024), meseSalvato.getAnno());
            assertFalse(meseSalvato.isMeseChiuso());
            assertEquals(tutoratoMock.getId(), meseSalvato.getUtente().getId());
        }

        @Test
        @DisplayName("trova mese per id esistente")
        void findMesePerIdEsistente() {
            Optional<Mese> meseOptional = meseRepository.findById(meseGennaio.getId());

            assertTrue(meseOptional.isPresent());
            Mese meseTrovato = meseOptional.get();
            assertEquals(meseGennaio.getId(), meseTrovato.getId());
            assertEquals(Integer.valueOf(1), meseTrovato.getNumeroMese());
            assertEquals(Integer.valueOf(2024), meseTrovato.getAnno());
            assertFalse(meseTrovato.isMeseChiuso());
        }

        @Test
        @DisplayName("ritonra optional vuoto per id inesistente")
        void OptionalVuotoPerIdInesistente() {
            Integer idInesistente = 99999;
            Optional<Mese> meseOptional = meseRepository.findById(idInesistente);
            assertFalse(meseOptional.isPresent());
        }

        @Test
        @DisplayName("elimina mese esistente")
        void eliminaMeseEsistente() {
            Integer idDaEliminare = meseGennaio.getId();

            meseRepository.deleteById(idDaEliminare);

            Optional<Mese> meseEliminato = meseRepository.findById(idDaEliminare);
            assertFalse(meseEliminato.isPresent());
        }

        @Test
        @DisplayName("ritonra tutti i mesi")
        void ritornaTuttiIMesi() {
            List<Mese> tuttiIMesi = meseRepository.findAll();

            assertEquals(3, tuttiIMesi.size());
            
            long mesiAperti = tuttiIMesi.stream()
                    .filter(m -> !m.isMeseChiuso())
                    .count();
            long mesiChiusi = tuttiIMesi.stream()
                    .filter(Mese::isMeseChiuso)
                    .count();
            
            assertEquals(2, mesiAperti);
            assertEquals(1, mesiChiusi);
        }
    }

    @Nested
    @DisplayName("Test per il metodo findByUtente_IdAndNumeroMese")
    class FindByUtenteIdAndNumeroMeseTest {

        @Test
        @DisplayName("trova mese per utente e numero mese esistenti")
        void trovaMesePerUtenteENumeroMeseEsistenti() {
            Optional<Mese> meseOptional = meseRepository.findByUtente_IdAndNumeroMese(
                tutoratoMock.getId(), 1);

            assertTrue(meseOptional.isPresent());
            Mese meseTrovato = meseOptional.get();
            assertEquals(meseGennaio.getId(), meseTrovato.getId());
            assertEquals(Integer.valueOf(1), meseTrovato.getNumeroMese());
            assertEquals(tutoratoMock.getId(), meseTrovato.getUtente().getId());
        }

        @Test
        @DisplayName("trova mese chiuso per utente e numero mese")
        void trovaMeseChiusoPerUtenteENumeroMese() {
            Optional<Mese> meseOptional = meseRepository.findByUtente_IdAndNumeroMese(
                tutoratoMock.getId(), 2);

            assertTrue(meseOptional.isPresent());
            Mese meseTrovato = meseOptional.get();
            assertEquals(meseFebbraio.getId(), meseTrovato.getId());
            assertEquals(Integer.valueOf(2), meseTrovato.getNumeroMese());
            assertTrue(meseTrovato.isMeseChiuso());
            assertEquals(tutoratoMock.getId(), meseTrovato.getUtente().getId());
        }

        @Test
        @DisplayName("ritorna optional vuoto per numero mese inesistente")
        void optionalVuotoPerNumeroMeseInesistente() {
            Optional<Mese> meseOptional = meseRepository.findByUtente_IdAndNumeroMese(
                tutoratoMock.getId(), 12);

            assertFalse(meseOptional.isPresent());
        }

        @Test
        @DisplayName("optional vuoto per utente inesistente")
        void optionalVuotoPerUtenteInesistente() {
            Optional<Mese> meseOptional = meseRepository.findByUtente_IdAndNumeroMese(
                99999, 1);

            assertFalse(meseOptional.isPresent());
        }

        @Test
        @DisplayName("distingue mesi dello stesso numero per utenti diversi")
        void distingueMesiStessoNumeroPerUtentiDiversi() {
            Mese gennaiooAltroUtente = new Mese();
            gennaiooAltroUtente.setNumeroMese(1);
            gennaiooAltroUtente.setAnno(2024);
            gennaiooAltroUtente.setMeseChiuso(false);
            gennaiooAltroUtente.setUtente(altroTutoratoMock);
            gennaiooAltroUtente = entityManager.persistAndFlush(gennaiooAltroUtente);

            Optional<Mese> gennaioPrimoUtente = meseRepository.findByUtente_IdAndNumeroMese(
                tutoratoMock.getId(), 1);
            Optional<Mese> gennaioSecondoUtente = meseRepository.findByUtente_IdAndNumeroMese(
                altroTutoratoMock.getId(), 1);

            assertTrue(gennaioPrimoUtente.isPresent());
            assertTrue(gennaioSecondoUtente.isPresent());
            
            assertNotEquals(gennaioPrimoUtente.get().getId(), gennaioSecondoUtente.get().getId());
            assertEquals(tutoratoMock.getId(), gennaioPrimoUtente.get().getUtente().getId());
            assertEquals(altroTutoratoMock.getId(), gennaioSecondoUtente.get().getUtente().getId());
        }

        @Test
        @DisplayName("gestisce numeri fine'anno")
        void dovrebbeGestireNumeriMeseAiConfiniDellAnno() {
            Mese dicembre = new Mese();
            dicembre.setNumeroMese(12);
            dicembre.setAnno(2024);
            dicembre.setMeseChiuso(true);
            dicembre.setUtente(tutoratoMock);
            dicembre = entityManager.persistAndFlush(dicembre);

            Optional<Mese> dicembreTrovato = meseRepository.findByUtente_IdAndNumeroMese(
                tutoratoMock.getId(), 12);
            Optional<Mese> gennaioTrovato = meseRepository.findByUtente_IdAndNumeroMese(
                tutoratoMock.getId(), 1);

            assertTrue(dicembreTrovato.isPresent());
            assertTrue(gennaioTrovato.isPresent());
            assertEquals(Integer.valueOf(12), dicembreTrovato.get().getNumeroMese());
            assertEquals(Integer.valueOf(2024), dicembreTrovato.get().getAnno());
            
            assertEquals(Integer.valueOf(1), gennaioTrovato.get().getNumeroMese());
            assertEquals(Integer.valueOf(2024), gennaioTrovato.get().getAnno());
        }
    }

    @Nested
    @DisplayName("test per le relazioni tra entità")
    class RelazioniTest {

        @Test
        @DisplayName("carica la relazione con utente")
        void caricaRelazioneConUtente() {
            Mese mese = meseRepository.findById(meseGennaio.getId()).orElseThrow();

            assertNotNull(mese.getUtente());
            assertEquals(tutoratoMock.getId(), mese.getUtente().getId());
            assertEquals("Mario", mese.getUtente().getNome());
            assertEquals("Rossi", mese.getUtente().getCognome());
            assertEquals(Ruolo.TUTORATO, mese.getUtente().getRuolo());
        }

        @Test
        @DisplayName("relazioni durante aggiornamenti")
        void relazioniDuranteAggiornamenti() {
            Mese meseDaAggiornare = meseRepository.findById(meseGennaio.getId()).orElseThrow();
            
            meseDaAggiornare.setMeseChiuso(true);
            Mese meseAggiornato = meseRepository.save(meseDaAggiornare);

            assertTrue(meseAggiornato.isMeseChiuso());
            assertNotNull(meseAggiornato.getUtente());
            assertEquals(tutoratoMock.getId(), meseAggiornato.getUtente().getId());
            assertEquals(Integer.valueOf(1), meseAggiornato.getNumeroMese());
            assertEquals(Integer.valueOf(2024), meseAggiornato.getAnno());
        }
    }

    @Nested
    @DisplayName("test validazione dei dati")
    class ValidazioneDatiTest {

        @Test
        @DisplayName("tutti i campi durante il salvataggio")
        void tuttiICampiDuranteIlSalvataggio() {
            Mese mese = meseRepository.findById(meseFebbraio.getId()).orElseThrow();

            assertEquals(Integer.valueOf(2), mese.getNumeroMese());
            assertEquals(Integer.valueOf(2024), mese.getAnno());
            assertTrue(mese.isMeseChiuso());
            assertEquals(Boolean.TRUE, mese.getMeseChiuso());
            assertNotNull(mese.getUtente());
        }

        @Test
        @DisplayName("gestisce boolean")
        void gestisceBoolean() {
            Mese meseAperto = meseRepository.findById(meseGennaio.getId()).orElseThrow();
            Mese meseChiuso = meseRepository.findById(meseFebbraio.getId()).orElseThrow();

            assertFalse(meseAperto.isMeseChiuso());
            assertEquals(Boolean.FALSE, meseAperto.getMeseChiuso());
            
            assertTrue(meseChiuso.isMeseChiuso());
            assertEquals(Boolean.TRUE, meseChiuso.getMeseChiuso());
        }

        @Test
        @DisplayName("gestisce aggiornamento di campi numerici")
        void gestisceAggiornamentoDiCampiNumerici() {
            Mese meseDaAggiornare = meseRepository.findById(meseGennaio.getId()).orElseThrow();
            
            meseDaAggiornare.setAnno(2025);
            Mese meseAggiornato = meseRepository.save(meseDaAggiornare);

            assertEquals(Integer.valueOf(2025), meseAggiornato.getAnno());
            assertEquals(Integer.valueOf(1), meseAggiornato.getNumeroMese()); // Rimane invariato
        }
    }

    @Nested
    @DisplayName("Test per operazioni di conteggio")
    class ConteggioEEsistenzaTest {

        @Test
        @DisplayName("conta tutti i mesi")
        void contaTuttiIMesi() {
            long count = meseRepository.count();

            assertEquals(3, count);
        }

        @Test
        @DisplayName("verifica mese per ID")
        void verificaMesePerId() {
            boolean esisteGennaio = meseRepository.existsById(meseGennaio.getId());
            boolean esisteInesistente = meseRepository.existsById(99999);

            assertTrue(esisteGennaio);
            assertFalse(esisteInesistente);
        }
    }

    @Nested
    @DisplayName("Test scenari complessi")
    class ScenariComplessiTest {

        @Test
        @DisplayName("gestisce diversi mesi per stesso utente")
        void gestiscePiuMesiPerStessoUtente() {
            List<Mese> tuttiIMesi = meseRepository.findAll();
            
            List<Mese> mesiPrimoTutorato = tuttiIMesi.stream()
                    .filter(m -> m.getUtente().getId().equals(tutoratoMock.getId()))
                    .toList();

            assertEquals(2, mesiPrimoTutorato.size());
            
            for (Mese mese : mesiPrimoTutorato) {
                assertEquals(tutoratoMock.getId(), mese.getUtente().getId());
            }
            
            List<Integer> numeriMese = mesiPrimoTutorato.stream()
                    .map(Mese::getNumeroMese)
                    .toList();
            assertEquals(2, numeriMese.size());
            assertTrue(numeriMese.contains(1));
            assertTrue(numeriMese.contains(2));
        }

        @Test
        @DisplayName("gestisce query di ricerca con più parametri ")
        void gestiscePiuQueryConDiversiParametri() {
            Optional<Mese> gennaioPrimoUtente = meseRepository.findByUtente_IdAndNumeroMese(
                tutoratoMock.getId(), 1);
            Optional<Mese> marzoSecondoUtente = meseRepository.findByUtente_IdAndNumeroMese(
                altroTutoratoMock.getId(), 3);
            Optional<Mese> meseInesistente = meseRepository.findByUtente_IdAndNumeroMese(
                tutoratoMock.getId(), 6);

            assertTrue(gennaioPrimoUtente.isPresent());
            assertTrue(marzoSecondoUtente.isPresent());
            assertFalse(meseInesistente.isPresent());
            
            assertEquals(tutoratoMock.getId(), gennaioPrimoUtente.get().getUtente().getId());
            assertEquals(altroTutoratoMock.getId(), marzoSecondoUtente.get().getUtente().getId());
        }

        @Test
        @DisplayName("gestisce aggiornamenti batch di stato mese")
        void aggiornamentiBatchMese() {
            List<Mese> tuttiIMesi = meseRepository.findAll();
            List<Mese> mesiAperti = tuttiIMesi.stream()
                    .filter(m -> !m.isMeseChiuso())
                    .toList();
            
            assertEquals(2, mesiAperti.size()); 
            //eg gennaio e marzo

            for (Mese mese : mesiAperti) {
                mese.setMeseChiuso(true);
                meseRepository.save(mese);
            }

            List<Mese> tuttiIMesiDopoAggiornamento = meseRepository.findAll();
            long mesiChiusiDopoAggiornamento = tuttiIMesiDopoAggiornamento.stream()
                    .filter(Mese::isMeseChiuso)
                    .count();
            
            assertEquals(3, mesiChiusiDopoAggiornamento);
        }
    }
}
