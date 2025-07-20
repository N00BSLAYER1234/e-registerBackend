package it.realeites.learning.registroelettronicobackend.model.entity;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "utente")
@Schema(name = "Utente", description = "Rappresenta un utente del sistema")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID univoco dell'utente")
    private Integer id;

    @Schema(description = "Nome dell'utente")
    private String nome;

    @Schema(description = "Cognome dell'utente")
    private String cognome;

    @Column(name = "data_nascita")
    @Schema(description = "Data di nascita dell'utente")
    private LocalDate dataNascita;

    @Schema(description = "Indirizzo dell'utente")
    private String indirizzo;

    @Schema(description = "Numero di cellulare dell'utente")
    private String cellulare;

    @Schema(description = "Indirizzo e-mail dell'utente")
    private String email;

    @Schema(description = "Username dell'utente")
    private String username;

    @Schema(description = "Password dell'utente")
    private String password;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Ruolo dell'utente")
    private Ruolo ruolo;

    @ManyToOne
    @JoinColumn(name = "id_tutor")
    @Schema(description = "Tutor dell'utente, se il ruolo dell'utente è TUTOR, questo sara null")
    private Utente tutor;

    public Utente() {
    }

    // Getter e Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) throws Exception {
        if (id == null) {
            throw new Exception("L'Id non può essere nullo");
        }
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws Exception {
        if (nome == null || nome.isEmpty() || nome.isBlank()) {
            throw new Exception("Il nome non può essere nullo");
        }
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) throws Exception {
        if (cognome == null || cognome.isEmpty() || cognome.isBlank()) {
            throw new Exception("Il cognome non può essere nullo");
        }
        this.cognome = cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) throws Exception {
        if (dataNascita == null) {
            throw new Exception("La data di nascita non può essere nulla");
        }
        this.dataNascita = dataNascita;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) throws Exception {
        if (indirizzo == null || indirizzo.isEmpty() || indirizzo.isBlank()) {
            throw new Exception("L'indirizzo non può essere nullo");
        }
        this.indirizzo = indirizzo;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) throws Exception {
        if (cellulare == null || cellulare.isEmpty() || cellulare.isBlank()) {
            throw new Exception("Il cellulare non può essere nullo");
        }
        this.cellulare = cellulare;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        if (email == null || email.isEmpty() || email.isBlank()) {
            throw new Exception("La email non può essere nulla");
        }
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws Exception {
        if (username == null || username.isEmpty() || username.isBlank()) {
            throw new Exception("Username non può essere nullo");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {
        if (password == null || password.isEmpty() || password.isBlank()) {
            throw new Exception("La password non può essere nulla");
        }
        this.password = password;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) throws Exception {
        if (ruolo == null) {
            throw new Exception("Il ruolo non può essere nullo");
        }
        this.ruolo = ruolo;
    }

    public Utente getTutor() {
        return tutor;
    }

    public void setTutor(Utente tutor) throws Exception {
        if (tutor != null && tutor.getRuolo() == Ruolo.TUTORATO) {
            throw new Exception("L'utente deve essere un tutor");
        }
        this.tutor = tutor;
    }

}
