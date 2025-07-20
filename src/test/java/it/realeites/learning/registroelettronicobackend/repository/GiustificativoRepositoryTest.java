  package it.realeites.learning.registroelettronicobackend.repository;

  import java.time.LocalDate;
  import java.util.List;
  import java.util.Optional;

  import static org.junit.jupiter.api.Assertions.assertEquals;
  import static org.junit.jupiter.api.Assertions.assertFalse;
  import static org.junit.jupiter.api.Assertions.assertNotNull;
  import static org.junit.jupiter.api.Assertions.assertTrue;
  import org.junit.jupiter.api.BeforeEach;
  import org.junit.jupiter.api.Test;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
  import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

  import it.realeites.learning.registroelettronicobackend.model.entity.Giustificativo;
  import it.realeites.learning.registroelettronicobackend.model.entity.Ruolo;
  import it.realeites.learning.registroelettronicobackend.model.entity.Utente;

  @DataJpaTest
  class GiustificativoRepositoryTest {

      @Autowired
      private TestEntityManager entityManager;

      @Autowired
      private GiustificativoRepository repository;

      private Utente utente1;
      private Utente utente2;
      private Giustificativo giustificativo1;
      private Giustificativo giustificativo2;
      private Giustificativo giustificativo3;

      @BeforeEach
      void init() throws Exception {
          utente1 = new Utente();
          utente1.setNome("Mario");
          utente1.setCognome("Rossi");
          utente1.setUsername("mario.rossi");
          utente1.setEmail("mario@test.com");
          utente1.setPassword("password");
          utente1.setRuolo(Ruolo.TUTORATO);
          utente1.setDataNascita(LocalDate.of(1995, 1, 1));
          utente1.setIndirizzo("Via Roma 1");
          utente1.setCellulare("1234567890");



          utente2 = new Utente();
          utente2.setNome("Luca");
          utente2.setCognome("Bianchi");
          utente2.setUsername("luca.bianchi");
          utente2.setEmail("luca@test.com");
          utente2.setPassword("password");
          utente2.setRuolo(Ruolo.TUTORATO);
          utente2.setDataNascita(LocalDate.of(1996, 2, 2));
          utente2.setIndirizzo("Via Milano 2");
          utente2.setCellulare("0987654321");

          utente1 = entityManager.persistAndFlush(utente1);
          utente2 = entityManager.persistAndFlush(utente2);

          giustificativo1 = new Giustificativo();
          giustificativo1.setDescrizione("visita medica");
          giustificativo1.setAccettata(false);

          giustificativo2 = new Giustificativo();
          giustificativo2.setDescrizione("malattia");
          giustificativo2.setAccettata(true);

          giustificativo3 = new Giustificativo();
          giustificativo3.setDescrizione("motivi familiari");
          giustificativo3.setAccettata(false);
      }

      @Test
      void saveReturnGiustificativo() {
          Giustificativo giustificativoSalvato = repository.save(giustificativo1);

          assertNotNull(giustificativoSalvato.getId());
          assertEquals("visita medica", giustificativoSalvato.getDescrizione());
          assertFalse(giustificativoSalvato.isAccettata());
      }

      @Test
      void returnGiustificativoPerId() {
          Giustificativo giustificativoSalvato = entityManager.persistAndFlush(giustificativo1);

          Optional<Giustificativo> giustificativoRecuperato = repository.findById(giustificativoSalvato.getId());

          assertTrue(giustificativoRecuperato.isPresent());
          assertEquals("visita medica", giustificativoRecuperato.get().getDescrizione());
      }

      @Test
      void optionalVuotoPerIdInesistente() {
          Optional<Giustificativo> risultato = repository.findById(999);

          assertFalse(risultato.isPresent());
      }

      @Test
      void returnTuttiGiustificativi() {
          entityManager.persistAndFlush(giustificativo1);
          entityManager.persistAndFlush(giustificativo2);
          entityManager.persistAndFlush(giustificativo3);

          List<Giustificativo> giustificativi = repository.findAll();

          assertEquals(3, giustificativi.size());
      }

      @Test
      void deleteGiustificativo() {
          Giustificativo giustificativoSalvato = entityManager.persistAndFlush(giustificativo1);
          Integer idGiustificativo = giustificativoSalvato.getId();

          repository.deleteById(idGiustificativo);
          entityManager.flush();

          Optional<Giustificativo> giustificativoEliminato = repository.findById(idGiustificativo);
          assertFalse(giustificativoEliminato.isPresent());
      }

      @Test
      void aggiornaStatoAccettazione() {
          Giustificativo giustificativoSalvato = entityManager.persistAndFlush(giustificativo1);

          giustificativoSalvato.setAccettata(true);
          Giustificativo giustificativoAggiornato = repository.save(giustificativoSalvato);

          assertTrue(giustificativoAggiornato.isAccettata());
      }

      @Test
      void countGiustificativi() {
          entityManager.persistAndFlush(giustificativo1);
          entityManager.persistAndFlush(giustificativo2);

          long count = repository.count();

          assertEquals(2, count);
      }

      @Test
      void verifyExistenceGiustificativo() {
          Giustificativo giustificativoSalvato = entityManager.persistAndFlush(giustificativo1);

          boolean esiste = repository.existsById(giustificativoSalvato.getId());
          boolean nonEsiste = repository.existsById(999);

          assertTrue(esiste);
          assertFalse(nonEsiste);
      }

      @Test
      void handlesRelazionConUtente() {
          Giustificativo giustificativoSalvato = entityManager.persistAndFlush(giustificativo1);

          Optional<Giustificativo> giustificativoRecuperato = repository.findById(giustificativoSalvato.getId());

          assertTrue(giustificativoRecuperato.isPresent());
          assertEquals("visita medica", giustificativoRecuperato.get().getDescrizione());
      }
  }
