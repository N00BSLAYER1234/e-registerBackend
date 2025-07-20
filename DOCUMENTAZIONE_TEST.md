# Documentazione Test - Registro Elettronico

## Panoramica
La suite di test del progetto Registro Elettronico utilizza un approccio stratificato per garantire la qualità e l'affidabilità del codice. I test coprono tutti i livelli dell'architettura: controller, service, repository, entity e DTO.

## Framework e Strumenti Utilizzati

### Framework di Test
- **JUnit 5**: Framework principale per i test unitari
- **Mockito**: Per la creazione di mock e l'isolamento delle dipendenze
- **Spring Boot Test**: Per i test di integrazione
- **AssertJ**: Per asserzioni più leggibili e potenti

### Annotazioni Principali
- `@Test`: Definisce un metodo di test
- `@BeforeEach`: Configurazione prima di ogni test
- `@DisplayName`: Descrizioni leggibili per i test
- `@Nested`: Organizzazione gerarchica dei test
- `@ExtendWith(MockitoExtension.class)`: Integrazione con Mockito
- `@DataJpaTest`: Test di integrazione per repository JPA

## Struttura dei Test

### 1. Test del Layer Controller

#### UtenteRESTTest.java
**Scopo**: Verifica il funzionamento degli endpoint REST per la gestione utenti

**Endpoint Testati**:
- `GET /api/utenti` - Recupera tutti gli utenti
- `GET /api/tutor` - Recupera solo i tutor
- `GET /api/tutorati` - Recupera solo gli studenti
- `GET /api/tutor/{tutorId}/tutorati` - Recupera studenti per un tutor specifico

**Scenari di Test**:
- Risposte HTTP corrette (200 OK, 404 NOT FOUND, 400 BAD REQUEST)
- Validazione della struttura JSON
- Gestione parametri non validi
- Verifica header Content-Type e Accept
- Test di integrazione con MockMvc

**Classi Annidate**:
- `TestGetAllUsers`: Test per recupero di tutti gli utenti
- `TestGetTutor`: Test per recupero tutor
- `TestGetTutorati`: Test per recupero studenti
- `TestGetTutoratiByTutor`: Test per recupero studenti per tutor
- `TestValidazioneParametri`: Test di validazione parametri
- `TestScenariIntegrazione`: Test scenari complessi

### 2. Test del Layer Entity

#### UtenteTest.java
**Scopo**: Verifica il comportamento dell'entità `Utente`

**Funzionalità Testate**:
- Getter e setter per tutti i campi
- Validazione dei dati (nome obbligatorio)
- Costruttori e valori di default
- Gestione di campi obbligatori e opzionali
- Relazioni con altre entità
- Gestione valori null

**Scenari Principali**:
- Creazione utente con tutti i campi
- Validazione campi obbligatori
- Test di getter/setter individuali
- Gestione informazioni di contatto
- Credenziali di accesso

### 3. Test del Layer DTO

#### UtenteDTOTest.java
**Scopo**: Verifica il comportamento del Data Transfer Object per gli utenti

**Funzionalità Testate**:
- Costruttore con valori di default
- Getter e setter per tutti i campi
- Gestione valori null
- Scenari completi per tutor e studenti
- Casi limite (stringhe vuote, ID negativi)

**Classi Annidate**:
- `TestCostruttore`: Test del costruttore
- `TestGetterSetter`: Test di getter e setter
- `TestValoriNull`: Test gestione valori null
- `TestScenariCompleti`: Test scenari reali
- `TestEdgeCases`: Test casi limite

### 4. Test del Layer Repository

#### UtenteRepositoryTest.java
**Scopo**: Test di integrazione per il repository degli utenti

**Operazioni Testate**:
- Operazioni CRUD base (save, findById, findAll, delete)
- Validazione persistenza entità
- Configurazione con `@DataJpaTest`

#### PresenzaRepositoryTest.java
**Scopo**: Test repository per la gestione delle presenze

**Funzionalità Testate**:
- Operazioni CRUD per le presenze
- Relazioni con entità Utente e Mese
- Query personalizzate (`findByMese_Id`)

#### MeseRepositoryTest.java
**Scopo**: Test completo del repository per la gestione dei mesi

**Funzionalità Testate**:
- Operazioni CRUD complesse
- Query personalizzate (`findByUtente_IdAndNumeroMese`)
- Validazione relazioni tra entità
- Gestione campi booleani (meseChiuso)
- Casi limite (mesi di confine, utenti multipli)
- Operazioni batch

#### GiustificativoRepositoryTest.java
**Scopo**: Test repository per la gestione delle giustificazioni

**Funzionalità Testate**:
- Operazioni CRUD base
- Gestione stato entità (campo accettata)
- Relazioni tra entità

### 5. Test del Layer Service

#### UtenteServiceImplTest.java & UtenteServiceTest.java
**Scopo**: Test della logica di business per la gestione utenti

**Funzionalità Testate**:
- Conversione da Entity a DTO
- Ricerca utenti per ID, email, ruolo
- Recupero di tutti gli utenti e studenti per tutor
- Filtri basati su ruolo
- Gestione casi limite

#### PresenzaServiceImplTest.java
**Scopo**: Test completo del servizio presenze (più complesso)

**Funzionalità Testate**:
- Logica di business complessa
- Creazione presenze con validazione
- Integrazione con gestione mesi
- Trigger per notifiche email
- Gestione stati (approvato, stato)
- Gestione eccezioni per vari scenari

#### EmailServiceTest.java
**Scopo**: Test del servizio di notifica email

**Funzionalità Testate**:
- Funzionalità di notifica email
- Integrazione con servizio utenti
- Gestione errori senza eccezioni

#### MeseServiceImplTest.java
**Scopo**: Test del servizio per la gestione dei mesi

**Funzionalità Testate**:
- Operazioni di gestione mesi
- Funzionalità di chiusura mesi
- Operazioni CRUD base

#### GiustificativoServiceImplTest.java
**Scopo**: Test del servizio per le giustificazioni

**Funzionalità Testate**:
- Gestione delle giustificazioni
- Workflow di approvazione
- Operazioni CRUD

## Pattern di Testing e Best Practices

### Organizzazione dei Test
- **Classi Annidate**: Uso estensivo di `@Nested` per raggruppare test correlati
- **Nomi Descrittivi**: Uso di `@DisplayName` per descrizioni leggibili
- **Setup Appropriato**: Metodi `@BeforeEach` per l'inizializzazione dei dati di test

### Strategia di Mocking
- **Isolamento Dipendenze**: Uso completo di Mockito per isolare le dipendenze
- **Verifica Interazioni**: Verifica corretta delle interazioni con i mock
- **Injection delle Dipendenze**: Uso di `@InjectMocks` per l'injection automatica

### Copertura Test
- **Casi Felici**: Test dei scenari principali
- **Casi Limite**: Test di condizioni di confine, valori null, risultati vuoti
- **Gestione Errori**: Test delle eccezioni e gestione errori

## Regole di Business Coperte

### Validazione Utenti
- Validazione ruoli (TUTOR vs TUTORATO)
- Relazioni tutor-studente
- Campi obbligatori e opzionali

### Gestione Presenze
- Prevenzione duplicati
- Controllo chiusura mesi
- Validazione stati

### Notifiche Email
- Trigger per assenze eccessive
- Integrazione con sistema utenti

### Workflow Approvazione
- Processo di approvazione giustificazioni
- Gestione stati workflow

## Configurazione Test

### Profili di Test
- Uso di `@ActiveProfiles("test")` per configurazioni specifiche
- Database H2 in memoria per i test
- Configurazioni separate per test e produzione

### Dati di Test
- Inizializzazione dati nei metodi `@BeforeEach`
- Uso di builder pattern per creazione oggetti test
- Dati realistici ma non sensibili

## Esecuzione dei Test

### Comandi Maven
```bash
# Esecuzione di tutti i test
mvn test

# Esecuzione test specifici
mvn test -Dtest=UtenteRESTTest
mvn test -Dtest=*Service*

# Esecuzione con report copertura
mvn test jacoco:report
```

### Configurazione IDE
- Supporto per esecuzione test singoli
- Debug dei test
- Visualizzazione copertura

## Metriche di Qualità

### Punti di Forza
- **Copertura Completa**: Test per tutti i layer dell'architettura
- **Isolamento**: Uso appropriato di mock per test unitari
- **Organizzazione**: Struttura gerarchica con classi annidate
- **Test di Integrazione**: Uso di `@DataJpaTest` per repository
- **Casi Limite**: Buona copertura di edge cases

### Aree di Miglioramento
- **Copertura Controller**: Solo UtenteREST è testato completamente
- **Test Parametrizzati**: Potrebbero essere utilizzati per scenari ripetitivi
- **Test End-to-End**: Mancano test di integrazione completi
- **Performance Testing**: Non sono presenti test di performance

## Convenzioni di Naming

### Metodi di Test
- Nomi descrittivi in italiano
- Convenzione: `verbo` + `scenario` + `risultatoAtteso`
- Esempi: `ritorna200ConTutor()`, `gestisceValoriNull()`

### Classi di Test
- Suffisso `Test` per test unitari
- Organizzazione per layer (Controller, Service, Repository)
- Classi annidate per raggruppare scenari correlati

## Manutenzione dei Test

### Aggiornamento Test
- Aggiornare test quando cambiano le regole di business
- Mantenere sincronizzati test e codice di produzione
- Rivedere regolarmente la copertura

### Debugging
- Uso di logging appropriato nei test
- Asserzioni dettagliate per facilitare debugging
- Isolamento dei test per prevenire interferenze

## Conclusione

La suite di test del Registro Elettronico rappresenta un approccio maturo e completo al testing, con buona copertura di tutti i layer dell'architettura e dei principali scenari di business. I test seguono le best practices di Spring Boot e utilizzano appropriatamente i framework di testing più diffusi nell'ecosistema Java.