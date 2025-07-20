package it.realeites.learning.registroelettronicobackend.model.entity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Test Utente")
public class UtenteTest {

    private Utente utente;

    @BeforeEach
    void impostaUtente() {
        utente = new Utente();
    }

    @Nested
    @DisplayName("Testa campo nome")
    class TestNome {

        @Test
        @DisplayName("imposta un nome valido")
        void ImpostaNomeValido() throws Exception {
            String nomeValido = "Mario";
            
            utente.setNome(nomeValido);
            
            assertEquals(nomeValido, utente.getNome());
        }

        @Test
        @DisplayName("lancia eccezione per nome vuoto")
        void LanciaEccezionePerNomeVuoto() {
            String nomeVuoto = "";
            
            Exception eccezione = assertThrows(Exception.class, () -> {
                utente.setNome(nomeVuoto);
            });
            
            assertEquals("Il nome non può essere nullo", eccezione.getMessage());
        }

        @Test
        @DisplayName("lancia eccezione per nomecon solo spazi")
        void lanciaEccezzionerNomeConSoliSpazi() {
            String nomeConSoliSpazi = "     ";
            
            Exception eccezione = assertThrows(Exception.class, () -> {
                utente.setNome(nomeConSoliSpazi);
            });
            
            assertEquals("Il nome non può essere nullo", eccezione.getMessage());
        }
    }

    @Nested
    @DisplayName("Test per i campi obbligatori")
    class TestCampiObbligatori {

        @Test
        @DisplayName("imposta e ritorna id")
        void setReturnId() throws Exception {
            Integer idAtteso = 1;
            
            utente.setId(idAtteso);
            
            assertEquals(idAtteso, utente.getId());
        }

        @Test
        @DisplayName("imposta e ritorna il cognome")
        void setReturnCognome() throws Exception {
            String cognomeAtteso = "Rossi";
            
            utente.setCognome(cognomeAtteso);
            
            assertEquals(cognomeAtteso, utente.getCognome());
        }

        @Test
        @DisplayName("imposta e ritorna la data di nascita")
        void setReturnDataNascita() throws Exception {
            LocalDate dataNascitaAttesa = LocalDate.of(1990, 5, 15);
            
            utente.setDataNascita(dataNascitaAttesa);
            
            assertEquals(dataNascitaAttesa, utente.getDataNascita());
        }
    }

    @Nested
    @DisplayName("Test per i campi di contatto")
    class TestCampiContatto {

        @Test
        @DisplayName("imposta e ritorna l'indirizzo")
        void setReturnIndirizzo() throws Exception {
            String indirizzoAtteso = "Via Roma 123";
            
            utente.setIndirizzo(indirizzoAtteso);
            
            assertEquals(indirizzoAtteso, utente.getIndirizzo());
        }

        @Test
        @DisplayName("imposta e ritorna  il cellulare")
        void setReturnCellulare() throws Exception {
            String cellulareAtteso = "3331234567";
            
            utente.setCellulare(cellulareAtteso);
            
            assertEquals(cellulareAtteso, utente.getCellulare());
        }

        @Test
        @DisplayName("imposta e ritorna  l'email")
        void setReturnEmail() throws Exception {
            String emailAttesa = "mario.rossi@email.com";
            
            utente.setEmail(emailAttesa);
            
            assertEquals(emailAttesa, utente.getEmail());
        }
    }

    @Nested
    @DisplayName("Test per le credenziali")
    class TestCredenziali {

        @Test
        @DisplayName("imposta e ritorna l'username")
        void setReturnUsername() throws Exception {
            String usernameAtteso = "mario.rossi";
            
            utente.setUsername(usernameAtteso);
            
            assertEquals(usernameAtteso, utente.getUsername());
        }

        @Test
        @DisplayName("imposta e ritorna la password")
        void setReturnPassword() throws Exception {
            String passwordAttesa = "password123";
            
            utente.setPassword(passwordAttesa);
            
            assertEquals(passwordAttesa, utente.getPassword());
        }
    }

    @Nested
    @DisplayName("Test per il ruolo e le relazioni")
    class TestRuoloERelazioni {

        @Test
        @DisplayName("imposta e ritorna  il ruolo")
        void setReturnRuolo() throws Exception {
            Ruolo ruoloAtteso = Ruolo.TUTOR;
            
            utente.setRuolo(ruoloAtteso);
            
            assertEquals(ruoloAtteso, utente.getRuolo());
        }

        @Test
        @DisplayName("imposta e ritorna il tutor")
        void setReturnTutor() throws Exception {
            Utente tutor = new Utente();
            tutor.setId(2);
            
            utente.setTutor(tutor);
            
            assertEquals(tutor, utente.getTutor());
            assertEquals(Integer.valueOf(2), utente.getTutor().getId());
        }

        @Test
        @DisplayName(" tutor nullo per utenti con ruolo TUTOR")
        void NullTutorForRoleTutor() throws Exception {
            utente.setRuolo(Ruolo.TUTOR);
            
            utente.setTutor(null);
            
            assertNull(utente.getTutor());
        }
    }

    @Nested
    @DisplayName("Test per il costruttore")
    class TestCostruttore {

        @Test
        @DisplayName("crea un utente con valori null")
        void CreaUtenteConValoriNull() {
            Utente nuovoUtente = new Utente();
            
            assertNull(nuovoUtente.getId());
            assertNull(nuovoUtente.getNome());
            assertNull(nuovoUtente.getCognome());
            assertNull(nuovoUtente.getDataNascita());
            assertNull(nuovoUtente.getIndirizzo());
            assertNull(nuovoUtente.getCellulare());
            assertNull(nuovoUtente.getEmail());
            assertNull(nuovoUtente.getUsername());
            assertNull(nuovoUtente.getPassword());
            assertNull(nuovoUtente.getRuolo());
            assertNull(nuovoUtente.getTutor());
        }
    }
}
