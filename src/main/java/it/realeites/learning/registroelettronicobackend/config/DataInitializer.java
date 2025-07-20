package it.realeites.learning.registroelettronicobackend.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import it.realeites.learning.registroelettronicobackend.model.entity.Mese;
import it.realeites.learning.registroelettronicobackend.model.entity.Presenza;
import it.realeites.learning.registroelettronicobackend.model.entity.Ruolo;
import it.realeites.learning.registroelettronicobackend.model.entity.Utente;
import it.realeites.learning.registroelettronicobackend.repository.MeseRepository;
import it.realeites.learning.registroelettronicobackend.repository.PresenzaRepository;
import it.realeites.learning.registroelettronicobackend.repository.UtenteRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private MeseRepository meseRepository;

    @Autowired
    private PresenzaRepository presenzaRepository;

    @Override
    public void run(String... args) throws Exception {
        initializeData();
    }

    private void initializeData() {
        // Check if data already exists
        if (utenteRepository.count() > 0) {
            return; // Data already initialized
        }

        try {
            // Create sample tutors
            Utente tutor1 = new Utente();
            tutor1.setNome("Marco");
            tutor1.setCognome("Rossi");
            tutor1.setDataNascita(LocalDate.of(1980, 5, 15));
            tutor1.setIndirizzo("Via Roma 123");
            tutor1.setCellulare("3331234567");
            tutor1.setEmail("marco.rossi@school.com");
            tutor1.setUsername("tutor");
            tutor1.setPassword("password");
            tutor1.setRuolo(Ruolo.TUTOR);
            tutor1.setTutor(null);
            tutor1 = utenteRepository.save(tutor1);

            Utente tutor2 = new Utente();
            tutor2.setNome("Anna");
            tutor2.setCognome("Verdi");
            tutor2.setDataNascita(LocalDate.of(1975, 8, 22));
            tutor2.setIndirizzo("Via Milano 456");
            tutor2.setCellulare("3337654321");
            tutor2.setEmail("anna.verdi@school.com");
            tutor2.setUsername("tutor2");
            tutor2.setPassword("password");
            tutor2.setRuolo(Ruolo.TUTOR);
            tutor2.setTutor(null);
            tutor2 = utenteRepository.save(tutor2);

            // Create sample students
            Utente student1 = new Utente();
            student1.setNome("Alice");
            student1.setCognome("Johnson");
            student1.setDataNascita(LocalDate.of(2000, 3, 10));
            student1.setIndirizzo("Via Torino 789");
            student1.setCellulare("3339876543");
            student1.setEmail("alice.johnson@student.com");
            student1.setUsername("student");
            student1.setPassword("password");
            student1.setRuolo(Ruolo.TUTORATO);
            student1.setTutor(tutor1);
            student1 = utenteRepository.save(student1);

            Utente student2 = new Utente();
            student2.setNome("Bob");
            student2.setCognome("Smith");
            student2.setDataNascita(LocalDate.of(1999, 7, 25));
            student2.setIndirizzo("Via Napoli 321");
            student2.setCellulare("3331122334");
            student2.setEmail("bob.smith@student.com");
            student2.setUsername("student2");
            student2.setPassword("password");
            student2.setRuolo(Ruolo.TUTORATO);
            student2.setTutor(tutor1);
            student2 = utenteRepository.save(student2);

            Utente student3 = new Utente();
            student3.setNome("Charlie");
            student3.setCognome("Brown");
            student3.setDataNascita(LocalDate.of(2001, 12, 5));
            student3.setIndirizzo("Via Firenze 654");
            student3.setCellulare("3334455667");
            student3.setEmail("charlie.brown@student.com");
            student3.setUsername("student3");
            student3.setPassword("password");
            student3.setRuolo(Ruolo.TUTORATO);
            student3.setTutor(tutor2);
            student3 = utenteRepository.save(student3);

            // Create sample months
            Mese gennaio = new Mese();
            gennaio.setNumeroMese(1);
            gennaio.setAnno(2024);
            gennaio.setMeseChiuso(false);
            gennaio.setUtente(student1);
            gennaio = meseRepository.save(gennaio);

            Mese febbraio = new Mese();
            febbraio.setNumeroMese(2);
            febbraio.setAnno(2024);
            febbraio.setMeseChiuso(true);
            febbraio.setUtente(student1);
            febbraio = meseRepository.save(febbraio);

            // Create sample attendance records
            createAttendanceRecord(student1, LocalDate.of(2024, 1, 15), true, true, gennaio);
            createAttendanceRecord(student1, LocalDate.of(2024, 1, 16), false, false, gennaio);
            createAttendanceRecord(student1, LocalDate.of(2024, 1, 17), true, true, gennaio);
            createAttendanceRecord(student1, LocalDate.of(2024, 1, 18), true, true, gennaio);
            createAttendanceRecord(student1, LocalDate.of(2024, 1, 19), true, true, gennaio);

            createAttendanceRecord(student2, LocalDate.of(2024, 1, 15), true, true, gennaio);
            createAttendanceRecord(student2, LocalDate.of(2024, 1, 16), true, true, gennaio);
            createAttendanceRecord(student2, LocalDate.of(2024, 1, 17), false, false, gennaio);
            createAttendanceRecord(student2, LocalDate.of(2024, 1, 18), true, true, gennaio);
            createAttendanceRecord(student2, LocalDate.of(2024, 1, 19), true, true, gennaio);

            createAttendanceRecord(student3, LocalDate.of(2024, 1, 15), false, false, gennaio);
            createAttendanceRecord(student3, LocalDate.of(2024, 1, 16), false, false, gennaio);
            createAttendanceRecord(student3, LocalDate.of(2024, 1, 17), true, true, gennaio);
            createAttendanceRecord(student3, LocalDate.of(2024, 1, 18), true, true, gennaio);
            createAttendanceRecord(student3, LocalDate.of(2024, 1, 19), true, true, gennaio);

            System.out.println("Sample data initialized successfully!");

        } catch (Exception e) {
            System.err.println("Error initializing data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createAttendanceRecord(Utente utente, LocalDate data, boolean stato, boolean approvato, Mese mese) {
        try {
            Presenza presenza = new Presenza();
            presenza.setData(data);
            presenza.setStato(stato);
            presenza.setApprovato(approvato);
            presenza.setUtente(utente);
            presenza.setMese(mese);
            presenzaRepository.save(presenza);
        } catch (Exception e) {
            System.err.println("Error creating attendance record: " + e.getMessage());
        }
    }
}