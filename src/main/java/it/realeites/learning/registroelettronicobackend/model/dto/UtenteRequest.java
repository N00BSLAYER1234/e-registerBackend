package it.realeites.learning.registroelettronicobackend.model.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import it.realeites.learning.registroelettronicobackend.model.entity.Ruolo;

public class UtenteRequest {

    @Schema(description = "ID univoco dell'utente")
    private Integer id;

    @Schema(description = "Nome dell'utente")
    private String nome;

    @Schema(description = "Cognome dell'utente")
    private String cognome;

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

    @Schema(description = "Ruolo dell'utente")
    private Ruolo ruolo;

    @Schema(description = "ID del tutor dell'utente, se presente")
    private Integer tutorId;

    public UtenteRequest() {
    }

    // Getter e Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public Integer getTutorId() {
        return tutorId;
    }

    public void setTutorId(Integer tutorId) {
        this.tutorId = tutorId;
    }

}
