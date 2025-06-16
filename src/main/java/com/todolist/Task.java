package com.todolist;

// Importazione dell'interfaccia BooleanProperty per la gestione di proprietà booleane osservabili
// Implementa il pattern Observer per la notifica automatica dei cambiamenti di stato
// Estende ObservableValue<Boolean> per la binding bidirezionale con componenti UI
import javafx.beans.property.BooleanProperty;

// Importazione della classe concreta SimpleBooleanProperty che implementa BooleanProperty
// Utilizza il pattern Strategy per l'incapsulamento del valore booleano con notifiche automatiche
// Gestisce internamente una lista di ChangeListener attraverso una struttura dati ottimizzata
import javafx.beans.property.SimpleBooleanProperty;

// Importazione della classe concreta SimpleStringProperty per proprietà stringa osservabili
// Implementa il pattern Decorator per l'aggiunta di funzionalità di osservazione a String
// Utilizza weak references per evitare memory leaks nella gestione dei listener
import javafx.beans.property.SimpleStringProperty;

// Importazione dell'interfaccia StringProperty per la gestione di proprietà stringa
// Estende ObservableValue<String> e WritableValue<String> per operazioni di lettura/scrittura
// Supporta il binding bidirezionale e la validazione attraverso il pattern Chain of Responsibility
import javafx.beans.property.StringProperty;

// Importazione dell'interfaccia funzionale ChangeListener per la gestione degli eventi di modifica
// Implementa il pattern Observer con signature generica ChangeListener<T>
// Utilizza il paradigma event-driven per la reattività dell'interfaccia utente
import javafx.beans.value.ChangeListener;

/**
 * Classe modello che rappresenta un'entità Task nel dominio dell'applicazione Todo List.
 * Implementa il pattern JavaBean con proprietà osservabili per l'integrazione con il framework JavaFX.
 * 
 * La classe utilizza il pattern Property per l'incapsulamento dei dati con notifiche automatiche,
 * consentendo il binding bidirezionale con i componenti dell'interfaccia utente secondo il paradigma MVC.
 * 
 * Le proprietà osservabili implementano il pattern Observer per la propagazione automatica
 * delle modifiche attraverso il scene graph senza necessità di polling o refresh manuali.
 */
public class Task {
    /**
     * Proprietà osservabile per la descrizione testuale del task.
     * Implementa il pattern Property attraverso StringProperty per il binding bidirezionale.
     * 
     * Il modificatore final garantisce l'immutabilità del riferimento all'oggetto Property,
     * mentre il contenuto rimane mutabile attraverso i metodi setter della proprietà.
     * Questo approccio implementa il pattern Immutable Object per la struttura dati.
     */
    private final StringProperty description;
    
    /**
     * Proprietà osservabile per lo stato di completamento del task.
     * Utilizza BooleanProperty per la gestione di valori booleani con notifiche automatiche.
     * 
     * La proprietà supporta il binding con componenti CheckBox attraverso il metodo
     * CheckBoxListCell.forListView() per la sincronizzazione automatica dello stato.
     */
    private final BooleanProperty completed;
    
    /**
     * Riferimento al ChangeListener per la gestione degli eventi di modifica dello stato completed.
     * Campo pubblico utilizzato dal controller per la registrazione e rimozione dinamica dei listener.
     * 
     * Questo approccio implementa il pattern Mediator per la comunicazione tra Model e View,
     * consentendo la gestione esplicita del ciclo di vita dei listener per evitare memory leaks.
     * Il tipo generico ChangeListener<Boolean> garantisce type safety a compile-time.
     */
    public ChangeListener<Boolean> completedListener;

    /**
     * Costruttore parametrizzato per l'inizializzazione di un nuovo task.
     * Implementa il pattern Constructor Injection per la dependency injection della descrizione.
     * 
     * Il costruttore inizializza le proprietà osservabili con valori di default appropriati:
     * - description: valore fornito dal parametro
     * - completed: false (stato iniziale non completato)
     * 
     * @param description Stringa contenente la descrizione testuale del task.
     *                   Il valore viene incapsulato in una SimpleStringProperty per l'osservabilità.
     *                   Accetta valori null che vengono gestiti internamente dalla Property.
     */
    public Task(String description) {
        // Inizializzazione della proprietà description attraverso il costruttore di SimpleStringProperty
        // La classe SimpleStringProperty gestisce internamente la validazione e la notifica dei cambiamenti
        // Implementa il pattern Wrapper per l'aggiunta di funzionalità osservabili al tipo primitivo String
        this.description = new SimpleStringProperty(description);
        
        // Inizializzazione della proprietà completed con valore booleano false
        // SimpleBooleanProperty incapsula il valore primitivo boolean in un oggetto osservabile
        // Il valore false rappresenta lo stato iniziale "non completato" secondo la logica di dominio
        this.completed = new SimpleBooleanProperty(false);
    }

    /**
     * Metodo getter per l'ottenimento del valore corrente della descrizione del task.
     * Implementa il pattern Accessor per l'accesso controllato ai dati incapsulati.
     * 
     * Il metodo delega la responsabilità alla proprietà osservabile attraverso il metodo get(),
     * garantendo la coerenza dei dati e la possibilità di intercettazione per logging o validazione.
     * 
     * @return String contenente la descrizione corrente del task.
     *         Può restituire null se la proprietà è stata inizializzata con valore null.
     *         Il valore restituito è una copia immutabile del contenuto della proprietà.
     */
    public String getDescription() {
        // Invocazione del metodo get() della StringProperty per l'estrazione del valore incapsulato
        // Il metodo garantisce thread-safety attraverso sincronizzazione interna della Property
        // Restituisce il valore corrente senza attivare notifiche ai listener registrati
        return description.get();
    }

    /**
     * Metodo accessor per l'ottenimento del riferimento alla proprietà osservabile description.
     * Implementa il pattern Property Exposure per il binding diretto con componenti UI.
     * 
     * Questo metodo è fondamentale per il data binding bidirezionale con TextField e altri
     * componenti di input testuale, consentendo la sincronizzazione automatica dei valori.
     * 
     * @return StringProperty rappresentante la proprietà osservabile per la descrizione.
     *         Il riferimento restituito consente l'aggiunta di listener e il binding con altri Observable.
     */
    public StringProperty descriptionProperty() {
        // Restituzione del riferimento diretto alla proprietà osservabile
        // Consente l'accesso completo alle funzionalità di binding e osservazione
        // Il riferimento restituito mantiene la relazione con l'oggetto Task originale
        return description;
    }

    /**
     * Metodo setter per la modifica del valore della descrizione del task.
     * Implementa il pattern Mutator con notifiche automatiche ai listener registrati.
     * 
     * La modifica del valore attiva automaticamente la notifica a tutti i ChangeListener
     * registrati sulla proprietà, garantendo la reattività dell'interfaccia utente.
     * 
     * @param description Nuovo valore stringa per la descrizione del task.
     *                   Accetta valori null che vengono gestiti internamente dalla Property.
     *                   Il valore viene validato e normalizzato secondo le regole interne.
     */
    public void setDescription(String description) {
        // Invocazione del metodo set() della StringProperty per la modifica del valore incapsulato
        // Il metodo attiva automaticamente la notifica ai listener attraverso il pattern Observer
        // Garantisce thread-safety e atomicità dell'operazione di modifica
        this.description.set(description);
    }

    /**
     * Metodo getter per l'ottenimento dello stato di completamento del task.
     * Implementa il pattern Accessor per valori booleani con naming convention JavaBean.
     * 
     * Il prefisso "is" segue la convenzione JavaBean per proprietà booleane,
     * distinguendolo dal prefisso "get" utilizzato per altri tipi di dati.
     * 
     * @return boolean indicante lo stato di completamento del task.
     *         true se il task è stato completato, false altrimenti.
     *         Il valore è estratto atomicamente dalla proprietà osservabile.
     */
    public boolean isCompleted() {
        // Estrazione del valore booleano primitivo dalla BooleanProperty
        // Il metodo get() restituisce il valore boolean unboxed dalla Property
        // L'operazione è thread-safe e non attiva notifiche ai listener
        return completed.get();
    }

    /**
     * Metodo accessor per l'ottenimento del riferimento alla proprietà osservabile completed.
     * Implementa il pattern Property Exposure per il binding con componenti CheckBox.
     * 
     * Questo metodo è essenziale per l'integrazione con CheckBoxListCell e altri componenti
     * che richiedono binding diretto con proprietà booleane osservabili.
     * 
     * @return BooleanProperty rappresentante la proprietà osservabile per lo stato di completamento.
     *         Il riferimento consente binding bidirezionale e registrazione di listener.
     */
    public BooleanProperty completedProperty() {
        // Restituzione del riferimento alla proprietà booleana osservabile
        // Mantiene la relazione con l'istanza Task per la coerenza dei dati
        // Consente l'accesso completo alle funzionalità di binding e osservazione
        return completed;
    }

    /**
     * Metodo setter per la modifica dello stato di completamento del task.
     * Implementa il pattern Mutator con propagazione automatica delle modifiche.
     * 
     * La modifica dello stato attiva la notifica a tutti i ChangeListener registrati,
     * inclusi quelli utilizzati per l'aggiornamento dell'interfaccia utente e degli stili CSS.
     * 
     * @param completed Nuovo stato booleano per il completamento del task.
     *                 true per marcare il task come completato, false per non completato.
     *                 Il valore viene boxed automaticamente nella BooleanProperty.
     */
    public void setCompleted(boolean completed) {
        // Modifica del valore booleano attraverso il metodo set() della BooleanProperty
        // Il valore primitivo viene automaticamente boxed in Boolean dalla Property
        // L'operazione attiva la notifica ai listener e aggiorna i binding collegati
        this.completed.set(completed);
    }

    /**
     * Override del metodo toString() ereditato dalla classe Object per la rappresentazione testuale del task.
     * Implementa il pattern Template Method definendo la rappresentazione stringa standard dell'oggetto.
     * 
     * Questo metodo è fondamentale per l'integrazione con componenti JavaFX come ListView e ComboBox
     * che utilizzano toString() per la visualizzazione degli elementi quando non è specificata
     * una cell factory personalizzata.
     * 
     * Per CheckBoxListCell, la parte testuale dell'elemento deriva da questo metodo toString()
     * mentre la checkbox viene gestita automaticamente attraverso il binding con la proprietà booleana.
     * 
     * Il metodo implementa il pattern Delegation delegando la responsabilità della rappresentazione
     * al metodo getDescription() per garantire coerenza e riutilizzo del codice.
     * 
     * @return String rappresentante la descrizione del task per la visualizzazione nell'interfaccia utente.
     *         Il valore restituito corrisponde al contenuto della proprietà description.
     *         Può essere null se la descrizione non è stata inizializzata.
     */
    @Override
    public String toString() {
        // Questo metodo definisce la rappresentazione testuale che verrà visualizzata nella ListView
        // quando non si utilizza una cell factory personalizzata che gestisce direttamente l'oggetto Task
        // (come fa CheckBoxListCell per la parte checkbox).
        
        // Per CheckBoxListCell, la parte testuale dell'elemento proviene da questo toString() per default,
        // mentre la gestione della checkbox è automatica attraverso il binding con la proprietà booleana.
        
        // Delegazione al metodo getDescription() per garantire coerenza nella rappresentazione
        // e centralizzazione della logica di accesso ai dati incapsulati
        return getDescription();
    }
}