package it.realeites.learning.registroelettronicobackend.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Schema(name = "Mese", description = "Rappresenta un mese")
public class Mese {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID univoco del mese")
    private Integer id;

    @Column(name = "mese_chiuso")
    @Schema(description = "Boolean rappresentante la chiusura del mese")
    private boolean meseChiuso = false;

    @Column(name = "numero_mese")
    @Schema(description = "Numero del mese")
    private Integer numeroMese;

    @Column(name = "anno")
    @Schema(description = "Anno")
    private Integer anno;

    @ManyToOne
    @JoinColumn(name = "id_utente")
    @Schema(description = "Utente associato")
    private Utente utente;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("L'id non può essere null");
            }
            this.id = id;
        } catch (Exception e) {
            throw new IllegalArgumentException("Errore nell'impostazione dell' ID: " + e.getMessage());
        }
    }

    public boolean isMeseChiuso() {
        return meseChiuso;
    }

    public void setMeseChiuso(boolean meseChiuso) {
        this.meseChiuso = meseChiuso;
    }

    public Boolean getMeseChiuso() {
        return meseChiuso;
    }

    public Integer getNumeroMese() {
        return numeroMese;
    }

    public void setNumeroMese(Integer numeroMese) {
        try {
            if (numeroMese == null) {
                throw new IllegalArgumentException("Il numero del mese non può essere null");
            }
            if (numeroMese < 1 || numeroMese > 12) {
                throw new IllegalArgumentException("Il numero del mese deve essere compreso tra 1 e 12");
            }
            this.numeroMese = numeroMese;
        } catch (Exception e) {
            throw new IllegalArgumentException("Errore nell'impostazione del numero del mese: " + e.getMessage());
        }
    }

    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        try {
            if (anno == null) {
                throw new IllegalArgumentException("L'anno non può essere null");
            }
            this.anno = anno;
        } catch (Exception e) {
            throw new IllegalArgumentException("Errore nell'impostazione dell' Anno: " + e.getMessage());
        }
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        try {
            if (utente == null) {
                throw new IllegalArgumentException("L'utente non può essere null");
            }
            this.utente = utente;
        } catch (Exception e) {
            throw new IllegalArgumentException("Errore nell'impostazione dell' Utente: " + e.getMessage());
        }
    }

}
