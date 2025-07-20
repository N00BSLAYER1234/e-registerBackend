package it.realeites.learning.registroelettronicobackend.model.dto;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import it.realeites.learning.registroelettronicobackend.model.entity.Ruolo;

@DisplayName("Test utendeDTO")
class UtenteDTOTest {

    private UtenteDTO utenteDTO;

    @BeforeEach
    void setUp() {
        utenteDTO = new UtenteDTO();
    }

    @Nested
    @DisplayName("Test per il costruttore")
    class TestCostruttore {

        @Test
        @DisplayName("crea un UtenteDTO con valori di default")
        void UtenteDtoDefaultValue() {
            UtenteDTO nuovoUtenteDTO = new UtenteDTO();

            assertNull(nuovoUtenteDTO.getId());
            assertNull(nuovoUtenteDTO.getNome());
            assertNull(nuovoUtenteDTO.getCognome());
            assertNull(nuovoUtenteDTO.getDataNascita());
            assertNull(nuovoUtenteDTO.getIndirizzo());
            assertNull(nuovoUtenteDTO.getCellulare());
            assertNull(nuovoUtenteDTO.getEmail());
            assertNull(nuovoUtenteDTO.getUsername());
            assertNull(nuovoUtenteDTO.getRuolo());
            assertNull(nuovoUtenteDTO.getTutorId());
        }
    }

    @Nested
    @DisplayName("Test getter e setter")
    class TestGetterSetter {

        @Test
        @DisplayName("imposta e ritorna ID")
        void SetReturnId() {

            Integer idAtteso = 1;

            utenteDTO.setId(idAtteso);


            assertEquals(idAtteso, utenteDTO.getId());
        }

        @Test
        @DisplayName("imposta e ritorna  il nome")
        void SetReturnNome() {

            String nomeAtteso = "Mario";


            utenteDTO.setNome(nomeAtteso);
            assertEquals(nomeAtteso, utenteDTO.getNome());
        }

        @Test
        @DisplayName(" imposta e ritorna il cognome")
        void  SetReturnCognome() {

            String cognomeAtteso = "Rossi";

            utenteDTO.setCognome(cognomeAtteso);

            assertEquals(cognomeAtteso, utenteDTO.getCognome());
        }

        @Test
        @DisplayName("imposta e ritorna la data di nascita")
        void  SetReturneDataNascita() {
            LocalDate dataNascitaAttesa = LocalDate.of(1990, 5, 15);

            utenteDTO.setDataNascita(dataNascitaAttesa);

            assertEquals(dataNascitaAttesa, utenteDTO.getDataNascita());
        }

        @Test
        @DisplayName("imposta e ritorna l'indirizzo")
        void  SetReturnIndirizzo() {
            String indirizzoAtteso = "Via Roma 123";

            utenteDTO.setIndirizzo(indirizzoAtteso);

            assertEquals(indirizzoAtteso, utenteDTO.getIndirizzo());
        }

        @Test
        @DisplayName("imposta e ritornil cellulare")
        void  SetReturnCellulare() {
            String cellulareAtteso = "3331234567";

            utenteDTO.setCellulare(cellulareAtteso);

            assertEquals(cellulareAtteso, utenteDTO.getCellulare());
        }

        @Test
        @DisplayName("imposta e ritorna l'email")
        void  SetReturneEmail() {
            String emailAttesa = "mario.rossi@email.com";

            utenteDTO.setEmail(emailAttesa);

            assertEquals(emailAttesa, utenteDTO.getEmail());
        }

        @Test
        @DisplayName("imposta e ritorna l'username")
        void  SetReturnUsername() {
            String usernameAtteso = "mario.rossi";

            utenteDTO.setUsername(usernameAtteso);

            assertEquals(usernameAtteso, utenteDTO.getUsername());
        }

        @Test
        @DisplayName("imposta e ritorna il ruolo")
        void  SetReturnRuolo() {
            Ruolo ruoloAtteso = Ruolo.TUTOR;

            utenteDTO.setRuolo(ruoloAtteso);

            assertEquals(ruoloAtteso, utenteDTO.getRuolo());
        }

        @Test
        @DisplayName("imposta e ritorna il tutorId")
        void  SetReturnTutorId() {
            Integer tutorIdAtteso = 2;

            utenteDTO.setTutorId(tutorIdAtteso);

            assertEquals(tutorIdAtteso, utenteDTO.getTutorId());
        }
    }

    @Nested
    @DisplayName("Test per i valori null")
    class TestValoriNull {

        @Test
        @DisplayName("gestisce valori null")
        void  SetReturnNull() {
            utenteDTO.setId(null);
            utenteDTO.setNome(null);
            utenteDTO.setCognome(null);
            utenteDTO.setDataNascita(null);
            utenteDTO.setIndirizzo(null);
            utenteDTO.setCellulare(null);
            utenteDTO.setEmail(null);
            utenteDTO.setUsername(null);
            utenteDTO.setRuolo(null);
            utenteDTO.setTutorId(null);

            assertNull(utenteDTO.getId());
            assertNull(utenteDTO.getNome());
            assertNull(utenteDTO.getCognome());
            assertNull(utenteDTO.getDataNascita());
            assertNull(utenteDTO.getIndirizzo());
            assertNull(utenteDTO.getCellulare());
            assertNull(utenteDTO.getEmail());
            assertNull(utenteDTO.getUsername());
            assertNull(utenteDTO.getRuolo());
            assertNull(utenteDTO.getTutorId());
        }
    }

    @Nested
    @DisplayName("Test scenari completi")
    class TestScenariCompleti {

        @Test
        @DisplayName("gestisce il DTO di un tutor")
        void  HandlesTutorCompleto() {
            Integer id = 1;
            String nome = "Marco";
            String cognome = "Bianchi";
            LocalDate dataNascita = LocalDate.of(1980, 5, 15);
            String indirizzo = "Via Roma 123";
            String cellulare = "3331234567";
            String email = "marco.bianchi@realeites.com";
            String username = "marco.bianchi";
            Ruolo ruolo = Ruolo.TUTOR;

            utenteDTO.setId(id);
            utenteDTO.setNome(nome);
            utenteDTO.setCognome(cognome);
            utenteDTO.setDataNascita(dataNascita);
            utenteDTO.setIndirizzo(indirizzo);
            utenteDTO.setCellulare(cellulare);
            utenteDTO.setEmail(email);
            utenteDTO.setUsername(username);
            utenteDTO.setRuolo(ruolo);

            utenteDTO.setTutorId(null); 
            //i tutor non hanno il tutorId!!

            assertEquals(id, utenteDTO.getId());
            assertEquals(nome, utenteDTO.getNome());
            assertEquals(cognome, utenteDTO.getCognome());
            assertEquals(dataNascita, utenteDTO.getDataNascita());
            assertEquals(indirizzo, utenteDTO.getIndirizzo());
            assertEquals(cellulare, utenteDTO.getCellulare());
            assertEquals(email, utenteDTO.getEmail());
            assertEquals(username, utenteDTO.getUsername());
            assertEquals(ruolo, utenteDTO.getRuolo());
            assertNull(utenteDTO.getTutorId());
        }

        @Test
        @DisplayName("gestisce il dto di uno studente")
        void  HandleStudente() {
            Integer id = 2;
            String nome = "Mario";
            String cognome = "Rossi";
            LocalDate dataNascita = LocalDate.of(1995, 3, 10);
            String indirizzo = "Via Milano 456";
            String cellulare = "3337654321";
            String email = "mario.rossi@email.com";
            String username = "mario.rossi";
            Ruolo ruolo = Ruolo.TUTORATO;
            Integer tutorId = 1;

            utenteDTO.setId(id);
            utenteDTO.setNome(nome);
            utenteDTO.setCognome(cognome);
            utenteDTO.setDataNascita(dataNascita);
            utenteDTO.setIndirizzo(indirizzo);
            utenteDTO.setCellulare(cellulare);
            utenteDTO.setEmail(email);
            utenteDTO.setUsername(username);
            utenteDTO.setRuolo(ruolo);
            utenteDTO.setTutorId(tutorId);

            assertEquals(id, utenteDTO.getId());
            assertEquals(nome, utenteDTO.getNome());
            assertEquals(cognome, utenteDTO.getCognome());
            assertEquals(dataNascita, utenteDTO.getDataNascita());
            assertEquals(indirizzo, utenteDTO.getIndirizzo());
            assertEquals(cellulare, utenteDTO.getCellulare());
            assertEquals(email, utenteDTO.getEmail());
            assertEquals(username, utenteDTO.getUsername());
            assertEquals(ruolo, utenteDTO.getRuolo());
            assertEquals(tutorId, utenteDTO.getTutorId());
        }

        @Test
        @DisplayName("gestisce i valori limite per le date")
        void  HandlesLimiteDate() {
            LocalDate dataMinima = LocalDate.of(1900, 1, 1);
            LocalDate dataMassima = LocalDate.of(2100, 12, 31);

            utenteDTO.setDataNascita(dataMinima);
            assertEquals(dataMinima, utenteDTO.getDataNascita());

            utenteDTO.setDataNascita(dataMassima);
            assertEquals(dataMassima, utenteDTO.getDataNascita());
        }

        @Test
        @DisplayName("gestisce tutti i ruoli")
        void  HandlesTuttiIRuoli() {
            utenteDTO.setRuolo(Ruolo.TUTOR);
            assertEquals(Ruolo.TUTOR, utenteDTO.getRuolo());

            utenteDTO.setRuolo(Ruolo.TUTORATO);
            assertEquals(Ruolo.TUTORATO, utenteDTO.getRuolo());
        }
    }

    @Nested
    @DisplayName("Test per edge cases")
    class TestEdgeCases {

        @Test
        @DisplayName("gestisce le stringhe vuote")
        void  HandleStringheVuote() {
            utenteDTO.setNome("");
            utenteDTO.setCognome("");
            utenteDTO.setIndirizzo("");
            utenteDTO.setCellulare("");
            utenteDTO.setEmail("");
            utenteDTO.setUsername("");

            assertEquals("", utenteDTO.getNome());
            assertEquals("", utenteDTO.getCognome());
            assertEquals("", utenteDTO.getIndirizzo());
            assertEquals("", utenteDTO.getCellulare());
            assertEquals("", utenteDTO.getEmail());
            assertEquals("", utenteDTO.getUsername());
        }

        @Test
        @DisplayName(" gestisce stringhe con spazi")
        void  HansleStringheConSpazi() {
            String stringaConSpazi = "    Testo con spazi  ";

            utenteDTO.setNome(stringaConSpazi);
            utenteDTO.setCognome(stringaConSpazi);
            utenteDTO.setIndirizzo(stringaConSpazi);

            assertEquals(stringaConSpazi, utenteDTO.getNome());
            assertEquals(stringaConSpazi, utenteDTO.getCognome());
            assertEquals(stringaConSpazi, utenteDTO.getIndirizzo());
        }

        @Test
        @DisplayName("gestisce gli id negativi")
        void  HandlesIdNegativi() {
            Integer idNegativo = -1;
            Integer tutorIdNegativo = -5;

            utenteDTO.setId(idNegativo);
            utenteDTO.setTutorId(tutorIdNegativo);

            assertEquals(idNegativo, utenteDTO.getId());
            assertEquals(tutorIdNegativo, utenteDTO.getTutorId());
        }
    }
}
