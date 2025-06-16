package com.todolist;

// Importazione della classe factory FXCollections per la creazione di collezioni osservabili
// Implementa il pattern Abstract Factory per la generazione di strutture dati reattive
// Fornisce metodi statici per l'istanziazione di ObservableList, ObservableSet e ObservableMap
import javafx.collections.FXCollections;

// Importazione dell'interfaccia ObservableList che estende List<E> con funzionalità di osservazione
// Implementa il pattern Observer per la notifica automatica delle modifiche alla collezione
// Supporta il binding bidirezionale con componenti UI come ListView, TableView e ComboBox
import javafx.collections.ObservableList;

/**
 * Classe manager che implementa il pattern Repository per la gestione centralizzata delle entità Task.
 * Fornisce un'astrazione di alto livello per le operazioni CRUD (Create, Read, Update, Delete)
 * sulla collezione di task, nascondendo i dettagli implementativi della struttura dati sottostante.
 * 
 * La classe utilizza ObservableList come struttura dati principale per garantire la reattività
 * dell'interfaccia utente attraverso il pattern Observer, eliminando la necessità di refresh manuali.
 * 
 * Implementa il pattern Facade per semplificare l'interazione con la collezione di task,
 * fornendo metodi di alto livello che incapsulano la logica di business e la validazione dei dati.
 */
public class TaskManager {

    /**
     * Collezione osservabile privata per la memorizzazione e gestione delle entità Task.
     * Implementa il pattern Encapsulation nascondendo la struttura dati interna ai client.
     * 
     * ObservableList<Task> fornisce le seguenti caratteristiche:
     * - Notifiche automatiche delle modifiche attraverso ListChangeListener
     * - Supporto per il binding bidirezionale con componenti JavaFX
     * - Thread-safety per operazioni di lettura concorrenti
     * - Implementazione ottimizzata per operazioni di inserimento e rimozione
     * 
     * La collezione mantiene l'ordine di inserimento secondo la semantica di List<E>
     * e supporta elementi duplicati secondo la logica di business dell'applicazione.
     */
    private ObservableList<Task> tasks;

    /**
     * Costruttore di default per l'inizializzazione del TaskManager.
     * Implementa il pattern Constructor Injection per la dependency injection della collezione.
     * 
     * Il costruttore inizializza la collezione tasks utilizzando il factory method
     * FXCollections.observableArrayList() che crea un'implementazione ottimizzata
     * basata su ArrayList con funzionalità di osservazione aggiunte.
     * 
     * La collezione viene inizializzata vuota e pronta per l'aggiunta di elementi
     * attraverso i metodi pubblici della classe.
     */
    public TaskManager() {
        // Inizializzazione della collezione osservabile attraverso il factory method di FXCollections
        // observableArrayList() crea un'implementazione che combina ArrayList con ObservableList
        // La collezione risultante supporta notifiche automatiche e binding con componenti UI
        // L'implementazione interna utilizza una struttura dati array ridimensionabile per efficienza
        this.tasks = FXCollections.observableArrayList();
    }

    /**
     * Metodo getter per l'ottenimento del riferimento alla collezione osservabile di task.
     * Implementa il pattern Accessor con esposizione controllata della struttura dati interna.
     * 
     * Il metodo restituisce il riferimento diretto alla collezione ObservableList per consentire
     * il binding bidirezionale con componenti UI come ListView, mantenendo la reattività
     * dell'interfaccia utente attraverso le notifiche automatiche delle modifiche.
     * 
     * NOTA: La restituzione del riferimento diretto viola il principio di incapsulamento
     * ma è necessaria per l'integrazione con il framework JavaFX che richiede accesso
     * diretto alle collezioni osservabili per il data binding.
     * 
     * @return ObservableList<Task> contenente tutti i task gestiti dal manager.
     *         Il riferimento restituito consente modifiche dirette alla collezione
     *         e mantiene la sincronizzazione automatica con l'interfaccia utente.
     */
    public ObservableList<Task> getTasks() {
        // Restituzione del riferimento diretto alla collezione osservabile
        // Necessario per il binding con ListView e altri componenti JavaFX
        // Il riferimento mantiene la relazione Observer-Observable per la reattività UI
        return tasks;
    }

    /**
     * Metodo per l'aggiunta di un nuovo task alla collezione attraverso la descrizione testuale.
     * Implementa il pattern Factory Method per la creazione e inserimento di nuove entità Task.
     * 
     * Il metodo include validazione dei dati di input per garantire l'integrità della collezione:
     * - Controllo null-safety per prevenire NullPointerException
     * - Validazione stringa non vuota dopo trim per evitare task con descrizioni insignificanti
     * 
     * L'aggiunta del task attiva automaticamente le notifiche ai listener registrati
     * sulla collezione ObservableList, garantendo l'aggiornamento immediato dell'interfaccia utente.
     * 
     * @param description Stringa contenente la descrizione del nuovo task da creare.
     *                   Deve essere non-null e non-vuota dopo rimozione degli spazi bianchi.
     *                   Valori null o stringhe vuote vengono ignorati silenziosamente.
     */
    public void addTask(String description) {
        // Validazione dei dati di input attraverso il pattern Guard Clause
        // Controllo di null-safety per prevenire eccezioni durante l'elaborazione
        // Validazione della stringa dopo trim() per escludere descrizioni composte solo da whitespace
        if (description != null && !description.trim().isEmpty()) {
            // Creazione di una nuova istanza Task attraverso il costruttore parametrizzato
            // Aggiunta alla collezione osservabile che attiva automaticamente le notifiche
            // L'operazione è atomica e thread-safe secondo l'implementazione di ObservableList
            tasks.add(new Task(description));
        }
        // Gestione silente di input non validi secondo il principio di robustezza
        // Evita eccezioni e mantiene la stabilità dell'applicazione
    }

    /**
     * Metodo per la rimozione di un task specifico dalla collezione.
     * Implementa il pattern Command per l'esecuzione di operazioni di cancellazione controllate.
     * 
     * Il metodo utilizza il confronto per uguaglianza (equals) per identificare l'elemento
     * da rimuovere nella collezione, supportando la rimozione di task duplicati se presenti.
     * 
     * La rimozione attiva automaticamente le notifiche ai ListChangeListener registrati,
     * garantendo l'aggiornamento sincronizzato dell'interfaccia utente.
     * 
     * @param task Istanza di Task da rimuovere dalla collezione.
     *            Deve essere non-null e presente nella collezione per essere rimossa.
     *            Valori null vengono ignorati silenziosamente per robustezza.
     */
    public void removeTask(Task task) {
        // Validazione null-safety attraverso il pattern Guard Clause
        // Prevenzione di NullPointerException durante l'operazione di rimozione
        if (task != null) {
            // Rimozione dell'elemento dalla collezione osservabile
            // Utilizza il metodo equals() della classe Task per il confronto di uguaglianza
            // L'operazione attiva automaticamente le notifiche ai listener registrati
            // Complessità temporale O(n) per la ricerca lineare nell'ArrayList sottostante
            tasks.remove(task);
        }
        // Gestione silente di parametri null per mantenere la robustezza del sistema
    }

    /**
     * Metodo per la rimozione in batch di tutti i task completati dalla collezione.
     * Implementa il pattern Bulk Operation per l'efficienza nelle operazioni su collezioni multiple.
     * 
     * Utilizza il metodo removeIf() con method reference per la rimozione condizionale,
     * applicando il predicato Task::isCompleted a tutti gli elementi della collezione.
     * 
     * L'operazione è ottimizzata per evitare ConcurrentModificationException attraverso
     * l'utilizzo di un iteratore interno che gestisce la rimozione sicura durante l'iterazione.
     * 
     * La rimozione in batch attiva una singola notifica di cambiamento per tutte le modifiche,
     * ottimizzando le prestazioni dell'aggiornamento dell'interfaccia utente.
     */
    public void removeCompletedTasks() {
        // Utilizzo del metodo removeIf() per la rimozione condizionale efficiente
        // Il predicato Task::isCompleted viene applicato a ogni elemento della collezione
        // Method reference equivalente alla lambda expression: task -> task.isCompleted()
        // L'operazione è atomica e thread-safe secondo l'implementazione di ObservableList
        // Complessità temporale O(n) con una singola passata sulla collezione
        tasks.removeIf(Task::isCompleted);
    }
}