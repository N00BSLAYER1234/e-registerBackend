## Struttura del progetto

Le classi Java sono organizzate in package in base alla loro responsabilità. La struttura del progetto è la seguente:
- **controller**: indirizzano le richieste HTTP al servizio appropriato e restituiscono le risposte (https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller.html);
- **service**: contengono la logica di manipolazione dei dati (presi dalla richiesta HTTP, dal/dai database e/o da altre fonti) necessaria per soddisfare la richiesta HTTP. Per accedere ai database, interagiscono con i repository;
- **repository**: sono il punto di contatto con i database. Espongono dei metodi per recuperare, scrivere, aggiornare ed eliminare i dati (operazioni tipicamente riassunte nell'acronimo CRUD: Create, Read, Update, Delete). I repository del progetto seguono i pattern di Spring Data JPA (https://docs.spring.io/spring-data/jpa/reference/jpa.html);
- **model**: i modelli, come suggerisce il nome stesso, sono classi che modellano i dati. In quanto tali, tendono ad avere più attributi che metodi veri e propri (i metodi classici di un model sono getter e setter, che contengono tipicamente poca logica). In questo progetto, distinguiamo due tipi di model in altrettanti sottopackage:
    - **dto (data transfer object)**: queste classi costituiscono un contratto per la comunicazione fra client e server: il corpo della richiesta e della risposta HTTP, se presenti, costituiscono una serializzazione (tipicamente JSON) di queste classi;
    - **entity**: queste classi rappresentano le tabelle (si suppone che il progetto usi un database relazionale - in generale si può parlare di "entità") del database . A questo scopo, sono tipicamente annotate con annotazioni JPA (es. @Entity, @Column, @OneToMany): https://docs.spring.io/spring-data/jpa/reference/jpa.html .
- **utils**: le cosiddette classi di utility (o, appunto, utils) forniscono delle funzionalità richieste in più parti dell'applicativo. Si distinguono dai servizi perché svolgono tipicamente funzioni più tecniche e generiche e meno legate a uno specifico oggetto di dominio, come ad esempio la manipolazione di stringhe, la formattazione di date o la conversione dei dati da un formato all'altro (es. XML -> JSON), che possono servire in più servizi o altre parti dell'applicazione.

## Note sui package vuoti

Per garantire che le cartelle vuote possano essere incluse nel repository Git, è possibile aggiungere un file `.gitkeep` in ciascuna di esse. Questo file non ha alcun contenuto e serve solo come segnaposto: deve essere eliminato non appena la cartella viene riempita con i file necessari o non appena la cartella stessa viene eliminata.

## Linee guida per la scrittura del codice

Per mantenere la qualità e la coerenza del codice, si prega di attenersi agli standard di Spring, come desumibili dalla [documentazione ufficiale](https://docs.spring.io/spring-framework/docs/current/reference/html5/). Nel caso in cui la versione di Spring utilizzata non fosse l'ultima, si prega di fare riferimento alla documentazione pertinente per la versione in uso.
Ci aspettiamo inoltre che rispettiate i seguenti principi:

### Single Responsibility Principle (SRP)

Ogni classe dovrebbe avere una sola ragione per cambiare. Questo significa che ogni classe dovrebbe avere solo un compito o una responsabilità.
Per maggiori informazioni sul Single Responsibility Principle, potete fare riferimento alle seguenti risorse:
- [Single Responsibility Principle su Wikipedia](https://en.wikipedia.org/wiki/Single-responsibility_principle)
- [Single Responsibility Principle di Robert C. Martin](https://www.oreilly.com/library/view/clean-architecture-a/9780134494272/) (in caso lo aveste a portata di mano)


### Programmare per interfacce

Per i controller e i service, ci atteniamo al principio di programmazione per interfacce. Questo significa che eventuali modifiche a questi layer devono essere prima dichiarate nell'interfaccia pertinente e poi implementate nella/e classe/e corrispondente/i. Inoltre, i servizi devono essere iniettati per tipo di interfaccia e non per tipo di classe.
Il motivo di questo approccio è che progettare prima l'interfaccia incoraggia gli sviluppatori a dare nomi chiari a funzioni e variabili e a definire un insieme ben strutturato di funzionalità. Questa pratica aiuta a disaccoppiare la progettazione dall'implementazione e porta, a nostro avviso, a del codice più manutenibile e comprensibile. Inoltre, promuove un incapsulamento reale, garantendo che i chiamanti non facciano affidamento sui dettagli di implementazione del metodo. In sostanza, gli sviluppatori dovrebbero comportarsi come se conoscessero solo l'interfaccia della dipendenza iniettata quando invocano i suoi metodi da un'altra classe.

Per ulteriori informazioni su questo principio, potete fare riferimento alle seguenti discussioni:
- [Programming to an Interface su Stack Overflow](https://stackoverflow.com/questions/383947/what-does-it-mean-to-program-to-an-interface)
- [Programming to an Interface su Stack Exchange](https://softwareengineering.stackexchange.com/questions/232359/understanding-programming-to-an-interface)

### Unit testing

Ci aspettiamo che, laddove applicabile, i metodi siano coperti da unit test. Ecco alcune indicazioni su come scrivere unit test:
- **non rompere l'incapsulamento**: testare l'API pubblica di una classe, non il suo stato interno o i metodi privati;
- **testare il comportamento, non l'implementazione**: concentrarsi su cosa fa il metodo, non su come lo fa;
- **isolamento**: i test unitari dovrebbero testare i metodi in isolamento. Se un metodo non può essere testato in isolamento, è accettabile escluderlo dai test unitari.

Ricordatevi che effettuare unit test non significa testare ogni singolo metodo, specie quelli che non possono essere testati in isolamento.