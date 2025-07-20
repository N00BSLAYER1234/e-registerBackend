package it.realeites.learning.registroelettronicobackend.utils;

import it.realeites.learning.registroelettronicobackend.model.dto.UtenteDTO;

public class EmailRequest {

    private UtenteDTO utente;
    private int numeroAssenze;

    public UtenteDTO getUtente() {
        return utente;
    }

    public void setUtente(UtenteDTO utente) {
        this.utente = utente;
    }

    public int getNumeroAssenze() {
        return numeroAssenze;
    }

    public void setNumeroAssenze(int numeroAssenze) {
        this.numeroAssenze = numeroAssenze;
    }
}