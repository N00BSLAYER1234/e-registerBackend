package it.realeites.learning.registroelettronicobackend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Giustificativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descrizione;
    private boolean accettata = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("L' Id non può essere null");
            }
            this.id = id;
        } catch (Exception e) {
            throw new IllegalArgumentException("Errore nell'impostazione dell' Id: " + e.getMessage());
        }
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        try {
            if (descrizione == null) {
                throw new IllegalArgumentException("Laa Descrizione non può essere null");
            }
            this.descrizione = descrizione;
        } catch (Exception e) {
            throw new IllegalArgumentException("Errore nell'impostazione della Descrizione: " + e.getMessage());
        }
    }

    public boolean isAccettata() {
        return accettata;
    }

    public void setAccettata(boolean accettata) {
        this.accettata = accettata;
    }
}
