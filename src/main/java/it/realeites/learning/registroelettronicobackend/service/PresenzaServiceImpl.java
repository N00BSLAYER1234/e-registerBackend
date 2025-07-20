package it.realeites.learning.registroelettronicobackend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.realeites.learning.registroelettronicobackend.model.dto.PresenzaDTO;
import it.realeites.learning.registroelettronicobackend.model.dto.PresenzaRequest;
import it.realeites.learning.registroelettronicobackend.model.dto.UtenteDTO;
import it.realeites.learning.registroelettronicobackend.model.entity.Giustificativo;
import it.realeites.learning.registroelettronicobackend.model.entity.Mese;
import it.realeites.learning.registroelettronicobackend.model.entity.Presenza;
import it.realeites.learning.registroelettronicobackend.model.entity.Ruolo;
import it.realeites.learning.registroelettronicobackend.model.entity.Utente;
import it.realeites.learning.registroelettronicobackend.repository.MeseRepository;
import it.realeites.learning.registroelettronicobackend.repository.PresenzaRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PresenzaServiceImpl implements PresenzaService {

    @Autowired
    private PresenzaRepository presenzaRepo;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private GiustificativoService giustificativoService;

    @Autowired
    private MeseRepository meseRepository;

    @Autowired
    private EmailService emailService;

    // Trova una presenza per ID
    @Override
    public PresenzaDTO findById(Integer id) {
        Presenza presenza = presenzaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Presenza non trovata con id: " + id));
        return presenzaToDTO(presenza);
    }

    // Restituisce tutte le presenze
    @Override
    public List<PresenzaDTO> findAll() {
        List<Presenza> presenze = presenzaRepo.findAll();
        return presenze.stream().map(this::presenzaToDTO).collect(Collectors.toList());
    }

    // Salva una nuova presenza
    @Override
    public PresenzaDTO save(PresenzaRequest presenzaRequest) {

        Utente utenteCompleto = utenteService.findEntityById(presenzaRequest.getUtente().getId())
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));

        Optional<Presenza> p = presenzaRepo.findByUtenteIdAndData(presenzaRequest.getUtente().getId(),
                presenzaRequest.getData());
        if (p.isPresent()) {
            throw new IllegalArgumentException("Presenza già inserita per questa data");
        }

        if (Ruolo.TUTOR.equals(presenzaRequest.getUtente().getRuolo())) {
            throw new IllegalArgumentException("Solo i tutorati possono aggiungere una presenza!");
        }

        Integer mese = presenzaRequest.getData().getMonthValue();
        Integer anno = presenzaRequest.getData().getYear();

        Mese m = meseRepository.findByUtente_IdAndNumeroMese(presenzaRequest.getUtente().getId(), mese)
                .orElseGet(() -> {
                    Mese nuovoMese = new Mese();
                    nuovoMese.setUtente(utenteCompleto);
                    nuovoMese.setNumeroMese(mese);
                    nuovoMese.setAnno(anno);
                    nuovoMese.setMeseChiuso(false);
                    return meseRepository.save(nuovoMese);
                });

        if (m.getMeseChiuso()) {
            throw new RuntimeException("Non è possibile inserire una presenza: il mese è chiuso");
        }

        // Crea la presenza
        Presenza presenza = new Presenza();
        presenza.setData(presenzaRequest.getData());
        presenza.setUtente(utenteCompleto);
        presenza.setGiustificativo(presenzaRequest.getGiustificativo());
        presenza.setStato(presenzaRequest.getStato());
        presenza.setMese(m);
        presenza = presenzaRepo.save(presenza);
        return presenzaToDTO(presenza);

    }

    @Override
    public PresenzaDTO saveByTutor(PresenzaRequest presenzaRequest, Integer tutorId) {

        // Recupera il tutor e il tutorato
        Utente tutor = utenteService.findEntityById(tutorId)
                .orElseThrow(() -> new IllegalArgumentException("Tutor non trovato"));

        Utente tutorato = utenteService.findEntityById(presenzaRequest.getUtente().getId())
                .orElseThrow(() -> new IllegalArgumentException("Tutorato non trovato"));

        // Verifica che il tutorato sia effettivamente assegnato al tutor
        if (!tutorato.getTutor().getId().equals(tutor.getId())) {
            throw new IllegalArgumentException("Il tutorato non è assegnato a questo tutor");
        }

        // Verifica che non esista già una presenza per quella data
        Optional<Presenza> p = presenzaRepo.findByUtenteIdAndData(tutorato.getId(), presenzaRequest.getData());
        if (p.isPresent()) {
            throw new IllegalArgumentException("Presenza già inserita per questa data");
        }

        // Solo i tutorati possono avere presenze
        if (Ruolo.TUTOR.equals(tutorato.getRuolo())) {
            throw new IllegalArgumentException("Solo i tutorati possono avere una presenza");
        }

        // // Gestione giustificativo in caso di assenza
        // if (Boolean.FALSE.equals(presenzaRequest.getStato())) {
        //     Giustificativo giustificativo = presenzaRequest.getGiustificativo();
        //     if (giustificativo == null || giustificativo.getDescrizione() == null) {
        //         throw new IllegalArgumentException("Descrizione del giustificativo obbligatoria per le assenze");
        //     }
        //     giustificativo.setAccettata(false);
        //     giustificativoService.save(giustificativo);
        //     presenzaRequest.setGiustificativo(giustificativo);
        // }

        // Recupera o crea il mese
        Integer mese = presenzaRequest.getData().getMonthValue();
        Integer anno = presenzaRequest.getData().getYear();

        Mese m = meseRepository.findByUtente_IdAndNumeroMese(tutorato.getId(), mese)
                .orElseGet(() -> {
                    Mese nuovoMese = new Mese();
                    nuovoMese.setUtente(tutorato);
                    nuovoMese.setNumeroMese(mese);
                    nuovoMese.setAnno(anno);
                    nuovoMese.setMeseChiuso(false);
                    return meseRepository.save(nuovoMese);
                });

        if (m.getMeseChiuso()) {
            throw new IllegalStateException("Non è possibile inserire una presenza: il mese è chiuso");
        }

        // Crea e salva la presenza
        Presenza presenza = new Presenza();
        presenza.setData(presenzaRequest.getData());
        presenza.setUtente(tutorato);
        presenza.setGiustificativo(presenzaRequest.getGiustificativo());
        presenza.setStato(presenzaRequest.getStato());
        presenza.setMese(m);
        presenza = presenzaRepo.save(presenza);

        return presenzaToDTO(presenza);
    }

    // Aggiorna una presenza esistente
    @Override
    public PresenzaDTO updatePresenza(Integer id) {
        Presenza presenza = presenzaRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Presenza non trovata"));
        if (presenza.getStato() == false) {
            if (!presenza.isApprovato()) {
                presenza.setStato(true);
            } else {
                throw new IllegalStateException("Presenza già approvata");
            }
        } else {
            throw new IllegalStateException("Presenza già registrata");
        }

        presenza.setApprovato(false);

        presenzaRepo.save(presenza);
        return presenzaToDTO(presenza);
    }

    // Aggiunge un giustificativo ad una presenza
    @Override
    public void addGiustificativo(Integer id, String descrizione) {

        Presenza presenza = presenzaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Presenza non trovata con id: " + id));

        if (presenza.isApprovato()) {
            throw new IllegalStateException("Presenza già approvata");
        }

        if (presenza.getStato()) {
            throw new IllegalStateException("Puoi aggiungere un giustificativo solo per le assenze");
        }

        if (presenza.getStato() == false) {
            Giustificativo giustificativo = new Giustificativo();
            giustificativo.setDescrizione(descrizione);
            giustificativo.setAccettata(false);
            giustificativoService.save(giustificativo);
            presenza.setGiustificativo(giustificativo);
            presenzaRepo.save(presenza);
        }
    }

    // Elimina una presenza per ID
    @Override
    public void deleteById(Integer id) {

        Presenza presenza = presenzaRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Presenza non trovata"));

        if (presenza.getMese().isMeseChiuso()) {
            throw new IllegalStateException("Non è possibile eliminare una presenza: il mese è chiuso");
        }
        presenzaRepo.deleteById(id);
    }

    // Approva una presenza da parte del tutor
    @Override
    public void setApprovato(Integer idPresenza, Integer idTutor) {

        Presenza presenza = presenzaRepo.findById(idPresenza)
                .orElseThrow(() -> new EntityNotFoundException("Presenza non trovata con id: " + idPresenza));

        Utente tutorato = presenza.getUtente();

        Utente tutor = utenteService.findEntityById(idTutor)
                .orElseThrow(() -> new IllegalArgumentException("Tutor non trovato"));

        if (tutorato.getTutor().getId() == tutor.getId()) {
            presenza.setApprovato(true);
        } else {
            throw new RuntimeException("Tutorato non associato a questo Tutor");
        }

        presenzaRepo.save(presenza);
    }

    // Rimuove l'approvazione da una presenza
    @Override
    public void deleteApprovato(Integer idPresenza) {
        Presenza presenza = presenzaRepo.findById(idPresenza)
                .orElseThrow(() -> new EntityNotFoundException("Presenza non trovata con id: " + idPresenza));

        if (presenza.getMese().isMeseChiuso()) {
            throw new IllegalStateException("Non è possibile modificare una presenza: il mese è chiuso");
        }

        presenza.setApprovato(false);
        presenzaRepo.save(presenza);
    }

    // Trova tutte le presenze per un mese specifico
    @Override
    public List<PresenzaDTO> findAllByMese_Id(Integer idMese) {
        List<Presenza> presenze = presenzaRepo.findByMese_Id(idMese);

        return presenze.stream().map(this::presenzaToDTO).collect(Collectors.toList());
    }

    // Trova tutte le presenze per un utente specifico
    @Override
    public List<PresenzaDTO> findAllByUtente_Id(Integer idUtente) {
        List<Presenza> presenze = presenzaRepo.findByUtente_Id(idUtente);

        return presenze.stream().map(this::presenzaToDTO).collect(Collectors.toList());
    }

    private final int sogliaAssenze = 2;

    // Controlla le presenze e invia email se superata la soglia di assenze
    @Override
    public void controllaPresenzeEUtente(Integer utenteId, Integer idMese) {
        UtenteDTO utenteDTO = utenteService.findById(utenteId)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato con id: " + utenteId));

        int assenze = presenzaRepo.countAssenzeNonGiustificateByUtenteIdAndMese(utenteId, idMese);

        if (assenze > sogliaAssenze) {
            emailService.inviaMailAvvisoPresenze(utenteDTO, assenze);
        }
    }

    // Converte un'entità Presenza in PresenzaDTO
    private PresenzaDTO presenzaToDTO(Presenza presenza) {
        PresenzaDTO dto = new PresenzaDTO();
        UtenteDTO udto = utenteService.toUtenteDTO(presenza.getUtente());
        dto.setId(presenza.getId());
        dto.setData(presenza.getData());
        dto.setStato(presenza.getStato());
        dto.setApprovato(presenza.isApprovato());
        dto.setUtente(udto);

        return dto;
    }

}
