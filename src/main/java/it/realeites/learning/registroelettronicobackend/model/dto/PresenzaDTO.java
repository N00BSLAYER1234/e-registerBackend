package it.realeites.learning.registroelettronicobackend.model.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import it.realeites.learning.registroelettronicobackend.model.entity.Giustificativo;

@Schema(name = "PresenzaDTO", description = "DTO per il trasferimento dati della presenza")
public class PresenzaDTO {

    @Schema(description = "ID univoco della presenza")
    private Integer id;

    @Schema(description = "Data della presenza")
    private LocalDate data;

    @Schema(description = "Stato della presenza (true = presente, false = assente)")
    private Boolean stato;

    @Schema(description = "Flag che indica se la presenza è stata approvata")
    private boolean approvato;

    @Schema(description = "ID dell'utente associato alla presenza")
    private UtenteDTO utente;

    @Schema(description = "ID del giustificativo associato alla presenza (se presente)")
    private Giustificativo giustificativo;

    @Schema(description = "ID del mese associato alla presenza")
    private Integer idMese;

        public PresenzaDTO() {
    }
    

    // Getter e Setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Boolean getStato() {
        return stato;
    }

    public void setStato(Boolean stato) {
        this.stato = stato;
    }

    public boolean isApprovato() {
        return approvato;
    }

    public void setApprovato(boolean approvato) {
        this.approvato = approvato;
    }

    public UtenteDTO getUtente() {
        return utente;
    }

    public void setUtente(UtenteDTO utente) {
        this.utente = utente;
    }

    public Giustificativo getGiustificativo() {
        return giustificativo;
    }

    public void setGiustificativo(Giustificativo giustificativo) {
        this.giustificativo = giustificativo;
    }

    public Integer getIdMese() {
        return idMese;
    }

    public void setIdMese(Integer idMese) {
        this.idMese = idMese;
    }

}
