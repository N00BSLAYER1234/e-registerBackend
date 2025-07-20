package it.realeites.learning.registroelettronicobackend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.realeites.learning.registroelettronicobackend.model.dto.UtenteDTO;
import it.realeites.learning.registroelettronicobackend.model.entity.Presenza;
import it.realeites.learning.registroelettronicobackend.model.entity.Utente;

public interface PresenzaRepository extends JpaRepository<Presenza, Integer> {

    List<Presenza> findByUtente_Id(Integer idUtente);
    List<Presenza> findByUtenteAndMese_Id(UtenteDTO utente, Integer idMese);
    Optional<Presenza> findByUtenteIdAndData(Integer idUtente, LocalDate data);
    List<Presenza> findByMese_Id(Integer idMese);
    List<Presenza> findByUtenteAndStato(Utente utente, Boolean stato);
    List<Presenza> findAll();
    Presenza findByGiustificativo_Id(Integer idGiustificativo);

    @Query("SELECT p FROM Presenza p WHERE p.mese.meseChiuso = true AND p.mese.numeroMese = :numeroMese AND p.mese.anno = :anno AND p.utente.id = :idUtente ORDER BY p.data DESC")
    List<Presenza> findByMeseChiusoAnnoUtente(@Param("numeroMese") Integer numeroMese, @Param("anno") Integer anno,
            @Param("idUtente") Integer idUtente);

    @Query("SELECT COUNT(p) FROM Presenza p WHERE p.utente.id = :utenteId AND p.stato = false AND p.giustificativo IS NULL AND p.mese.id = :idMese")
    int countAssenzeNonGiustificateByUtenteIdAndMese(@Param("utenteId") Integer utenteId, @Param("idMese") Integer idMese);
}
