package it.realeites.learning.registroelettronicobackend.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Presenza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate data;

    @Column(name = "stato")
    private Boolean stato;

    private boolean approvato = false;

    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "id_giustificativo")
    private Giustificativo giustificativo;

    @ManyToOne
    @JoinColumn(name = "id_mese")
    private Mese mese;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("L'Id non può essere null");
            }
            this.id = id;
        } catch (Exception e) {
            throw new IllegalArgumentException("Errore nell'impostazione dell' Id: " + e.getMessage());
        }
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        try {
            if (data == null) {
                throw new IllegalArgumentException("La Data non può essere null");
            }
            this.data = data;
        } catch (Exception e) {
            throw new IllegalArgumentException("Errore nell'impostazione della Data: " + e.getMessage());
        }
    }

    public Boolean getStato() {
        return stato;
    }

    public void setStato(Boolean stato) {
        try {
            if (stato == null) {
                throw new IllegalArgumentException("Lo Stato non può essere null");
            }
            this.stato = stato;
        } catch (Exception e) {
            throw new IllegalArgumentException("Errore nell'impostazione dello Stato: " + e.getMessage());
        }
    }

    public boolean isApprovato() {
        return approvato;
    }

    public void setApprovato(boolean approvato) {
        this.approvato = approvato;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        try {
            if (utente == null) {
                throw new IllegalArgumentException("L'Utente non può essere null");
            }
            this.utente = utente;
        } catch (Exception e) {
            throw new IllegalArgumentException("Errore nell'impostazione dell' Utente: " + e.getMessage());
        }
    }

    public Giustificativo getGiustificativo() {
        return giustificativo;
    }

    public void setGiustificativo(Giustificativo giustificativo) {
        this.giustificativo = giustificativo;
    }

    public Mese getMese() {
        return mese;
    }

    public void setMese(Mese mese) {
        try {
            if (mese == null) {
                throw new IllegalArgumentException("Il Mese non può essere null");
            }
            this.mese = mese;
        } catch (Exception e) {
            throw new IllegalArgumentException("Errore nell'impostazione del Mese: " + e.getMessage());
        }
        
    }

}
