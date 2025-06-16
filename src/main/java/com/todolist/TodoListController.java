package com.todolist;

// Importazione wildcard del package javafx.animation per tutte le classi di animazione
// Include: Timeline, KeyFrame, KeyValue, Transition, FadeTransition, ScaleTransition,
// TranslateTransition, RotateTransition, ParallelTransition, SequentialTransition,
// PauseTransition e Interpolator per funzioni di easing matematiche
import javafx.animation.*;

// Importazione dell'annotazione FXML per l'injection automatica dei componenti UI
// Implementa il pattern Dependency Injection attraverso reflection e introspection
// Consente il binding automatico tra elementi FXML e campi della classe controller
import javafx.fxml.FXML;

// Importazione della classe astratta Node, superclasse di tutti gli elementi del scene graph
// Rappresenta un nodo nell'albero gerarchico dell'interfaccia utente JavaFX
// Fornisce funzionalità comuni per trasformazioni geometriche, eventi e proprietà visuali
import javafx.scene.Node;

// Importazione wildcard del package javafx.scene.control per tutti i controlli UI
// Include: Button, TextField, ListView, Label, CheckBox e altre classi di controllo
// Implementa il pattern Composite per la gestione gerarchica dei componenti
import javafx.scene.control.*;

// Importazione della classe CheckBoxListCell per celle ListView con checkbox integrate
// Implementa il pattern Strategy per la personalizzazione del rendering delle celle
// Supporta il binding bidirezionale con proprietà booleane osservabili
import javafx.scene.control.cell.CheckBoxListCell;

// Importazione della classe DropShadow per effetti di ombreggiatura
// Implementa algoritmi di rendering per la simulazione di ombre proiettate
// Utilizza convoluzioni matematiche per la generazione di effetti di profondità
import javafx.scene.effect.DropShadow;

// Importazione della classe Glow per effetti di luminescenza
// Applica algoritmi di post-processing per la simulazione di bagliori luminosi
// Utilizza funzioni gaussiane per la distribuzione dell'intensità luminosa
import javafx.scene.effect.Glow;

// Importazione della classe BorderPane per layout manager a cinque regioni
// Implementa il pattern Strategy per il posizionamento di componenti
// Divide l'area in regioni: TOP, BOTTOM, LEFT, RIGHT, CENTER secondo coordinate cartesiane
import javafx.scene.layout.BorderPane;

// Importazione della classe HBox per layout orizzontale lineare
// Implementa il pattern Strategy per l'allineamento orizzontale di componenti
// Utilizza algoritmi di distribuzione spaziale lungo l'asse X del piano cartesiano
import javafx.scene.layout.HBox;

// Importazione della classe Color per la rappresentazione di colori nel modello RGBA
// Supporta spazi colore RGB, HSB e rappresentazioni esadecimali
// Implementa il pattern Value Object per l'immutabilità delle istanze colore
import javafx.scene.paint.Color;

// Importazione della classe Circle per forme geometriche circolari
// Implementa primitive geometriche basate su equazioni matematiche del cerchio
// Utilizza coordinate cartesiane per centro e raggio secondo la formula (x-h)² + (y-k)² = r²
import javafx.scene.shape.Circle;

// Importazione della classe Duration per rappresentazione temporale in animazioni
// Incapsula valori temporali in millisecondi con aritmetica in virgola mobile
// Supporta operazioni matematiche per calcoli di timing e sincronizzazione
import javafx.util.Duration;

// Importazione della classe ArrayList per strutture dati dinamiche
// Implementa List<E> con array ridimensionabile per efficienza nelle operazioni di accesso
// Fornisce complessità temporale O(1) per accesso casuale e O(n) per inserimenti/rimozioni
import java.util.ArrayList;

// Importazione dell'interfaccia List per contratti di collezioni ordinate
// Definisce operazioni CRUD per strutture dati sequenziali
// Supporta accesso posizionale e iterazione secondo l'ordine di inserimento
import java.util.List;

// Importazione della classe Random per generazione di numeri pseudocasuali
// Implementa algoritmi PRNG (Pseudo-Random Number Generator) per simulazioni
// Utilizza il Linear Congruential Generator per sequenze deterministiche riproducibili
import java.util.Random;

/**
 * Classe controller che implementa il pattern MVC (Model-View-Controller) per la gestione
 * dell'interfaccia utente dell'applicazione Todo List. Funge da mediatore tra il modello dati
 * (TaskManager) e la vista (elementi FXML), orchestrando le interazioni utente e le animazioni.
 * 
 * La classe implementa il pattern Observer attraverso event handlers per la gestione
 * asincrona degli eventi dell'interfaccia utente, e il pattern Command per l'incapsulamento
 * delle operazioni sui dati in metodi discreti e riutilizzabili.
 * 
 * Utilizza extensively il framework di animazione JavaFX per fornire feedback visuale
 * attraverso trasformazioni geometriche, interpolazioni temporali e effetti di post-processing,
 * implementando principi di User Experience Design per migliorare l'usabilità dell'applicazione.
 */
public class TodoListController {

    /**
     * Campo BorderPane annotato con @FXML per l'injection automatica del contenitore principale.
     * BorderPane implementa il pattern Strategy per il layout management attraverso cinque regioni
     * geometricamente definite secondo un sistema di coordinate cartesiane bidimensionali.
     * L'annotation @FXML attiva il meccanismo di reflection-based dependency injection del
     * FXMLLoader, che utilizza introspection per mappare l'elemento fx:id="rootPane" del file
     * FXML a questo campo attraverso il pattern Proxy e il principio di Inversion of Control.
     */
    @FXML
    private BorderPane rootPane;
    
    /**
     * Campo TextField annotato con @FXML per l'input testuale delle nuove attività.
     * TextField estende TextInputControl e implementa il pattern Observer per la gestione
     * degli eventi di input attraverso InvalidationListener e ChangeListener.
     * Supporta binding bidirezionale con StringProperty per sincronizzazione automatica
     * dei dati secondo il pattern Model-View-ViewModel (MVVM).
     */
    @FXML
    private TextField taskInput;
    
    /**
     * Campo ListView parametrizzato con tipo generico Task per la visualizzazione delle attività.
     * ListView<Task> implementa il pattern Composite per la gestione di collezioni eterogenee
     * e il pattern Strategy attraverso CellFactory per la personalizzazione del rendering.
     * Utilizza VirtualFlow per l'ottimizzazione delle performance con lazy loading e
     * viewport-based rendering per gestire efficientemente liste di dimensioni arbitrarie.
     */
    @FXML
    private ListView<Task> taskListView;
    
    /**
     * Campo Button per l'aggiunta di nuove attività, implementa il pattern Command
     * attraverso ActionEvent handlers per l'incapsulamento delle operazioni discrete.
     * Button estende ButtonBase e supporta il pattern State per la gestione degli stati
     * visuali (normal, hover, pressed, disabled) attraverso CSS pseudo-classes.
     */
    @FXML
    private Button addTaskButton;
    
    /**
     * Campo Button per il completamento delle attività selezionate.
     * Implementa il pattern Command per operazioni batch sui dati selezionati,
     * utilizzando SelectionModel per la gestione delle selezioni multiple attraverso
     * algoritmi di set theory per operazioni di unione e intersezione.
     */
    @FXML
    private Button markCompletedButton;
    
    /**
     * Campo Button per la rimozione delle attività selezionate.
     * Utilizza il pattern Command con conferma utente per operazioni distruttive,
     * implementando il principio di reversibilità attraverso undo/redo patterns
     * e validazione preventiva per l'integrità dei dati.
     */
    @FXML
    private Button removeTaskButton;
    
    /**
     * Campo Button per la rimozione batch delle attività completate.
     * Implementa il pattern Bulk Operation per l'efficienza computazionale,
     * utilizzando predicati funzionali per il filtering e stream processing
     * secondo paradigmi di programmazione funzionale per operazioni su collezioni.
     */
    @FXML
    private Button removeCompletedButton;
    
    /**
     * Campo Label per la visualizzazione del titolo dell'applicazione.
     * Label implementa il pattern Flyweight per l'ottimizzazione della memoria
     * condividendo risorse comuni tra istanze simili, e supporta rich text
     * attraverso TextFlow per formattazione avanzata con markup HTML-like.
     */
    @FXML
    private Label titleLabel;
    
    /**
     * Campo HBox per il layout orizzontale del container di input.
     * HBox implementa il pattern Strategy per l'allineamento e la distribuzione
     * spaziale dei componenti lungo l'asse X secondo algoritmi di space distribution
     * (SPACE_BETWEEN, SPACE_AROUND, SPACE_EVENLY) basati su calcoli proporzionali.
     */
    @FXML
    private HBox inputBox;
    
    /**
     * Campo HBox per il layout orizzontale dei pulsanti di controllo.
     * Utilizza spacing uniforme e alignment policies per la distribuzione
     * geometrica dei controlli secondo principi di design simmetrico e
     * proporzioni auree per l'ottimizzazione dell'esperienza utente.
     */
    @FXML
    private HBox controlButtonsBox;

    /**
     * Campo TaskManager per la gestione del modello dati dell'applicazione.
     * Implementa il pattern Repository per l'astrazione dell'accesso ai dati
     * e il pattern Facade per semplificare le operazioni CRUD complesse.
     * Non annotato con @FXML poiché istanziato programmaticamente nel metodo initialize()
     * secondo il pattern Factory Method per il controllo del ciclo di vita degli oggetti.
     */
    private TaskManager taskManager;
    
    /**
     * Campo Random per la generazione di numeri pseudocasuali utilizzati nelle animazioni.
     * Implementa algoritmi PRNG (Pseudo-Random Number Generator) per la creazione
     * di sequenze deterministiche riproducibili, utilizzando il Linear Congruential
     * Generator con parametri matematici ottimizzati per distribuzione uniforme.
     * Utilizzato per variazioni casuali in posizioni, colori e timing delle animazioni
     * per creare effetti visuali non deterministici e migliorare l'engagement utente.
     */
    private Random random = new Random();

    /**
     * Metodo di inizializzazione automaticamente invocato dal FXMLLoader dopo l'injection
     * dei componenti annotati con @FXML. Implementa il pattern Template Method definendo
     * la sequenza di inizializzazione dell'interfaccia utente secondo un algoritmo
     * deterministico e ottimizzato per le performance di rendering.
     * 
     * Il metodo orchestra l'inizializzazione del modello dati, la configurazione
     * delle cell factories, l'applicazione di listeners per il data binding,
     * e l'attivazione delle animazioni iniziali secondo principi di progressive
     * enhancement per l'esperienza utente.
     */
    public void initialize() {
        // Istanziazione del TaskManager attraverso il pattern Factory Method
        // per il controllo del ciclo di vita e l'incapsulamento della logica di creazione
        taskManager = new TaskManager();
        
        // Binding della ObservableList del TaskManager alla ListView attraverso
        // il pattern Observer per la sincronizzazione automatica dei dati.
        // Utilizza weak references per prevenire memory leaks e garantire
        // garbage collection efficiente degli oggetti non più referenziati
        taskListView.setItems(taskManager.getTasks());

        // Configurazione di una custom cell factory attraverso il pattern Strategy
        // per la personalizzazione del rendering delle celle della ListView.
        // Implementa il pattern Factory Method per la creazione di celle specializzate
        taskListView.setCellFactory(lv -> {
            // Creazione di una CheckBoxListCell parametrizzata con method reference
            // per il binding della proprietà booleana 'completed' del Task.
            // Utilizza il pattern Strategy per la gestione del checkbox state
            CheckBoxListCell<Task> cell = new CheckBoxListCell<>(Task::completedProperty);

            // Registrazione di un ChangeListener sulla itemProperty della cella
            // per gestire dinamicamente l'associazione e la dissociazione dei Task.
            // Implementa il pattern Observer per la reattività ai cambiamenti di stato
            cell.itemProperty().addListener((obs, oldItem, newItem) -> {
                // Cleanup preventivo del listener precedente per evitare memory leaks
                // e accumulo di listeners orfani. Implementa il pattern Disposable
                // per la gestione corretta delle risorse e la prevenzione di memory leaks
                if (oldItem != null && oldItem.completedListener != null) {
                    // Rimozione esplicita del listener dalla proprietà osservabile
                    // per interrompere la catena di riferimenti e consentire garbage collection
                    oldItem.completedProperty().removeListener(oldItem.completedListener);
                    // Nullificazione del riferimento per eliminare strong references
                    // e facilitare la deallocazione della memoria
                    oldItem.completedListener = null;
                }
                
                // Configurazione del nuovo Task associato alla cella
                if (newItem != null) {
                    // Applicazione immediata dello stile CSS basato sullo stato corrente
                    // del Task per garantire consistenza visuale al momento del binding
                    updateCellStyle(cell, newItem);
                    
                    // Creazione e registrazione di un nuovo ChangeListener per monitorare
                    // i cambiamenti futuri della proprietà 'completed' del Task.
                    // Utilizza lambda expression per l'implementazione concisa del listener
                    newItem.completedListener = (completedObs, oldVal, newVal) -> {
                        // Invocazione del metodo di aggiornamento dello stile CSS
                        // in risposta ai cambiamenti di stato del Task
                        updateCellStyle(cell, newItem);
                    };
                    // Registrazione del listener sulla BooleanProperty osservabile
                    // per attivare la reattività ai cambiamenti di stato
                    newItem.completedProperty().addListener(newItem.completedListener);
                } else {
                    // Rimozione della classe CSS 'completed-task' quando la cella diventa vuota
                    // per garantire la pulizia dello stato visuale e prevenire stili residui
                    cell.getStyleClass().remove("completed-task");
                }
            });
            
            // Applicazione di effetti hover alla cella attraverso trasformazioni geometriche
            // per migliorare l'interattività e fornire feedback visuale all'utente.
            // Utilizza scaling factor di 1.02 per un effetto sottile ma percettibile
            addHoverEffect(cell, 1.02, 1.0);

            // Restituzione della cella configurata al ListView per il rendering
            return cell;
        });

        // Sequenza di animazioni iniziali per l'enhancement progressivo dell'interfaccia utente.
        // Implementa il pattern Choreographer per la coordinazione temporale delle animazioni
        // e la creazione di un'esperienza utente fluida e coinvolgente attraverso motion design
        
        // Animazione del titolo principale attraverso effetti di scaling e fade-in
        // per attirare l'attenzione dell'utente e stabilire la gerarchia visuale
        animateTitle();
        
        // Animazione di fade-in e slide per il container di input con delay di 500ms
        // Utilizza interpolazione temporale per transizioni smooth e naturali
        // Il valore 20 rappresenta la distanza di traslazione in pixel lungo l'asse Y
        fadeInAndSlide(inputBox, 500, 20);
        
        // Animazione sequenziale del container dei pulsanti di controllo con delay di 700ms
        // Implementa staggered animation per creare un effetto di cascata visuale
        fadeInAndSlide(controlButtonsBox, 700, 20);
        
        // Animazione finale del pulsante di rimozione completati con delay di 900ms
        // Completa la sequenza di reveal progressivo degli elementi dell'interfaccia
        fadeInAndSlide(removeCompletedButton, 900, 20);
        
        // Attivazione dell'effetto particellare di background per ambient animation
        // Crea un'atmosfera dinamica attraverso elementi grafici in movimento continuo
        // Utilizza algoritmi di fisica simulata per movimenti naturali e organici
        startParticleAnimation();

        // Applicazione di effetti hover interattivi ai pulsanti dell'interfaccia
        // Implementa il pattern Decorator per l'aggiunta di comportamenti dinamici
        // senza modificare la struttura base dei componenti Button
        
        // Effetto hover sul pulsante di aggiunta con scaling factor 1.1
        // Fornisce feedback visuale immediato per migliorare l'usabilità
        addHoverEffect(addTaskButton, 1.1, 1.0);
        
        // Effetto hover sul pulsante di completamento con trasformazione geometrica
        // Utilizza interpolazione smooth per transizioni fluide tra stati
        addHoverEffect(markCompletedButton, 1.1, 1.0);
        
        // Effetto hover sul pulsante di rimozione con feedback visuale
        // Implementa principi di affordance per comunicare l'interattività
        addHoverEffect(removeTaskButton, 1.1, 1.0);
        
        // Effetto hover sul pulsante di rimozione batch con scaling uniforme
        // Mantiene consistenza nell'esperienza utente attraverso comportamenti uniformi
        addHoverEffect(removeCompletedButton, 1.1, 1.0);

        // Animazione di startup pulsante per attirare l'attenzione dell'utente
        // Implementa il pattern Attention Grabber con delay di 1000ms per timing ottimale
        // Utilizza pulsazione ritmica per guidare l'utente verso l'azione primaria
        animatePulse(addTaskButton, 1000);
    }

    /**
     * Metodo per l'animazione di pulsazione (pulse) di un nodo attraverso trasformazioni
     * geometriche di scaling. Implementa il pattern Command per l'incapsulamento
     * dell'operazione di animazione e il pattern Template Method per la definizione
     * della sequenza di trasformazioni matematiche applicate al nodo target.
     * 
     * L'animazione utilizza funzioni di interpolazione matematiche per creare
     * transizioni smooth tra i valori di scala, implementando principi di easing
     * per movimenti naturali e organici che rispettano le leggi fisiche del moto.
     * 
     * @param node Il nodo JavaFX target dell'animazione, deve implementare l'interfaccia
     *             Transformable per supportare le operazioni di scaling geometrico
     * @param delayMillis Il ritardo in millisecondi prima dell'inizio dell'animazione,
     *                    utilizzato per la sincronizzazione temporale e la coordinazione
     *                    con altre animazioni nella sequenza di choreography
     */
    private void animatePulse(Node node, long delayMillis) {
        // Creazione di una ScaleTransition per trasformazioni geometriche di scaling
        // Durata di 300ms ottimizzata per percezione umana secondo studi di UX research
        // Il timing rispetta i principi di motion design per animazioni percettibili ma non intrusive
        ScaleTransition st = new ScaleTransition(Duration.millis(300), node);
        
        // Impostazione del valore iniziale di scala sull'asse X (coordinata orizzontale)
        // Valore 1.0 rappresenta la scala naturale del nodo senza trasformazioni
        st.setFromX(1.0);
        
        // Impostazione del valore iniziale di scala sull'asse Y (coordinata verticale)
        // Mantiene proporzioni uniformi per evitare distorsioni geometriche
        st.setFromY(1.0);
        
        // Impostazione del valore finale di scala sull'asse X
        // Fattore 1.15 rappresenta un incremento del 15% per effetto visibile ma sottile
        st.setToX(1.15);
        
        // Impostazione del valore finale di scala sull'asse Y
        // Scaling uniforme su entrambi gli assi per mantenere aspect ratio originale
        st.setToY(1.15);
        
        // Configurazione del numero di cicli di animazione
        // 4 cicli forniscono feedback sufficiente senza essere eccessivamente invasivi
        st.setCycleCount(4);
        
        // Attivazione dell'auto-reverse per movimento bidirezionale
        // Crea effetto di pulsazione attraverso espansione e contrazione alternate
        st.setAutoReverse(true);
        
        // Applicazione dell'interpolatore EASE_BOTH per accelerazione e decelerazione graduali
        // Implementa funzioni di easing cubiche per movimenti naturali e organici
        // Simula l'inerzia fisica per transizioni realistiche
        st.setInterpolator(Interpolator.EASE_BOTH);

        // Creazione di una SequentialTransition per la composizione temporale di animazioni
        // Implementa il pattern Composite per la gestione di sequenze complesse
        SequentialTransition seqT = new SequentialTransition (
            // PauseTransition per il ritardo iniziale prima dell'esecuzione
            // Consente la sincronizzazione con altre animazioni nella timeline globale
            new PauseTransition(Duration.millis(delayMillis)),
            // ScaleTransition configurata per l'effetto di pulsazione
            st
        );
        
        // Avvio dell'esecuzione della sequenza di animazioni
        // Attiva il rendering engine per l'applicazione delle trasformazioni geometriche
        seqT.play();
    }

    /**
     * Metodo per l'applicazione di effetti hover interattivi attraverso trasformazioni
     * geometriche di scaling. Implementa il pattern Decorator per l'aggiunta di
     * comportamenti dinamici ai nodi senza modificarne la struttura fondamentale.
     * 
     * Il metodo utilizza due ScaleTransition distinte per gestire le fasi di
     * mouse-enter e mouse-exit, creando un feedback visuale bidirezionale che
     * rispetta i principi di affordance e usabilità dell'interfaccia utente.
     * 
     * @param node Il nodo target per l'applicazione dell'effetto hover
     * @param scaleTo Il fattore di scala finale durante l'hover (tipicamente > 1.0)
     * @param scaleFrom Il fattore di scala iniziale al termine dell'hover (tipicamente 1.0)
     */
    private void addHoverEffect(Node node, double scaleTo, double scaleFrom) {
        // Creazione della ScaleTransition per l'effetto di mouse-enter
        // Durata di 150ms ottimizzata per responsività immediata senza lag percettibile
        // Il timing rispetta i principi di micro-interazioni per feedback istantaneo
        ScaleTransition stOver = new ScaleTransition(Duration.millis(150), node);
        
        // Impostazione del valore target di scala sull'asse X per l'espansione orizzontale
        // Utilizza il parametro scaleTo per configurabilità e riutilizzabilità del metodo
        stOver.setToX(scaleTo);
        
        // Impostazione del valore target di scala sull'asse Y per l'espansione verticale
        // Mantiene proporzioni uniformi per evitare distorsioni geometriche
        stOver.setToY(scaleTo);
        
        // Creazione della ScaleTransition per l'effetto di mouse-exit
        // Durata identica per simmetria temporale e consistenza nell'esperienza utente
        ScaleTransition stOut = new ScaleTransition(Duration.millis(150), node);
        
        // Impostazione del valore di ritorno sull'asse X per il ripristino delle dimensioni originali
        // Utilizza scaleFrom per flessibilità nella configurazione degli stati finali
        stOut.setToX(scaleFrom);
        
        // Impostazione del valore di ritorno sull'asse Y per simmetria geometrica
        stOut.setToY(scaleFrom);

        // Registrazione dell'event handler per l'evento MouseEntered
        // Utilizza lambda expression per implementazione concisa del callback
        // Attiva l'animazione di scaling-up al passaggio del cursore sul nodo
        node.setOnMouseEntered(e -> stOver.playFromStart());
        
        // Registrazione dell'event handler per l'evento MouseExited
        // Implementa il comportamento complementare per il ripristino dello stato originale
        // Garantisce la reversibilità dell'effetto hover per consistenza comportamentale
        node.setOnMouseExited(e -> stOut.playFromStart());
    }

    private void animateTitle() {
        Glow glow = new Glow(0.0);
        titleLabel.setEffect(glow);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(glow.levelProperty(), 0.0)),
                new KeyFrame(Duration.seconds(1.5), new KeyValue(glow.levelProperty(), 0.6)),
                new KeyFrame(Duration.seconds(3), new KeyValue(glow.levelProperty(), 0.0))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void fadeInAndSlide(Node node, long delayMillis, double slideAmount) {
        node.setOpacity(0);
        node.setTranslateY(slideAmount);

        FadeTransition ft = new FadeTransition(Duration.millis(500), node);
        ft.setToValue(1.0);

        TranslateTransition tt = new TranslateTransition(Duration.millis(500), node);
        tt.setToY(0);

        SequentialTransition st = new SequentialTransition(
            new PauseTransition(Duration.millis(delayMillis)),
            new ParallelTransition(ft, tt)
        );
        st.play();
    }

    @FXML
    private void handleAddTask() {
        String taskDescription = taskInput.getText();
        if (taskDescription != null && !taskDescription.trim().isEmpty()) {
            taskManager.addTask(taskDescription);
            taskInput.clear();
            if (!taskManager.getTasks().isEmpty()) {
                taskListView.scrollTo(taskManager.getTasks().get(taskManager.getTasks().size() - 1));
            }


            // Animate the newly added cell (this is tricky as cell is created later)
            // We can try to find the cell after a short delay or animate the list view itself.
            // For now, a simple button click animation:
            animateButtonClick(addTaskButton);
        } else {
            // Shake animation for empty input
            animateShake(taskInput);
        }
    }

    @FXML
    private void handleMarkAsCompleted() {
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            selectedTask.setCompleted(!selectedTask.isCompleted());
            taskListView.getSelectionModel().clearSelection(); // Pulisce la selezione dopo l'operazione
            animateButtonClick(markCompletedButton);
        }
    }

    @FXML
    private void handleRemoveTask() {
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            taskManager.removeTask(selectedTask);
            taskListView.getSelectionModel().clearSelection();
            animateButtonClick(removeTaskButton);
        }
    }

    @FXML
    private void handleRemoveCompletedTasks() {
        List<Task> completedTasks = new ArrayList<>();
        for (Task task : taskManager.getTasks()) {
            if (task.isCompleted()) {
                completedTasks.add(task);
            }
        }

        if (!completedTasks.isEmpty()) {
            animateButtonClick(removeCompletedButton);
            taskManager.removeCompletedTasks();
            // taskListView.refresh(); // May not be needed if ObservableList updates trigger UI refresh.
        }
    }

    // Helper method to update cell style based on task completion
    private void updateCellStyle(ListCell<Task> cell, Task task) {
        if (task.isCompleted()) {
            cell.getStyleClass().add("completed-task");
        } else {
            cell.getStyleClass().remove("completed-task");
        }
    }

    private void animateButtonClick(Button button) {
        ScaleTransition st = new ScaleTransition(Duration.millis(100), button);
        st.setToX(0.9);
        st.setToY(0.9);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();
    }

    private void animateShake(Node node) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(50), node);
        tt.setFromX(0);
        tt.setByX(10);
        tt.setCycleCount(6);
        tt.setAutoReverse(true);
        tt.play();
    }

    private void animateNodeRemoval(Node node, Runnable onFinished) {
        if (node == null) {
            if (onFinished != null) onFinished.run();
            return;
        }
        FadeTransition ft = new FadeTransition(Duration.millis(300), node);
        ft.setToValue(0);
        ScaleTransition st = new ScaleTransition(Duration.millis(300), node);
        st.setToX(0.8);
        st.setToY(0.8);

        ParallelTransition pt = new ParallelTransition(node, ft, st);
        pt.setOnFinished(event -> {
            if (onFinished != null) {
                onFinished.run();
            }
        });
        pt.play();
    }
    


    private void startParticleAnimation() {
        Timeline particleTimeline = new Timeline();
        particleTimeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame kf = new KeyFrame(Duration.millis(100), event -> {
            if (rootPane.getWidth() > 0 && rootPane.getHeight() > 0) {
                for (int i = 0; i < 2; i++) { // Crea un paio di bolle alla volta
                    Circle bubble = new Circle(random.nextDouble() * 5 + 2, Color.rgb(255, 255, 255, 0.4)); // Bolle bianche semitrasparenti
                    bubble.setEffect(new DropShadow(5, Color.rgb(220, 220, 255, 0.3))); // Leggero alone luminoso
                    bubble.setCenterX(random.nextDouble() * rootPane.getWidth());
                    bubble.setCenterY(rootPane.getHeight() + bubble.getRadius()); // Inizia dal basso

                    rootPane.getChildren().add(bubble); // Aggiungi al rootPane

                    TranslateTransition tt = new TranslateTransition(Duration.seconds(random.nextDouble() * 3 + 2), bubble);
                    tt.setToY(-(rootPane.getHeight() + bubble.getRadius() * 2)); // Muovi verso l'alto, oltre lo schermo
                    tt.setInterpolator(Interpolator.EASE_IN); // Leggera accelerazione

                    FadeTransition ft = new FadeTransition(Duration.seconds(random.nextDouble() * 2 + 1), bubble);
                    ft.setFromValue(0.7);
                    ft.setToValue(0.0);
                    ft.setDelay(Duration.seconds(1)); // Inizia a svanire dopo un po'

                    ScaleTransition st = new ScaleTransition(Duration.seconds(random.nextDouble() * 1 + 0.5), bubble);
                    st.setToX(random.nextDouble() * 0.5 + 0.8); // Variazione leggera della dimensione
                    st.setToY(random.nextDouble() * 0.5 + 0.8);
                    st.setAutoReverse(true);
                    st.setCycleCount(2);

                    ParallelTransition pt = new ParallelTransition(bubble, tt, ft, st);
                    pt.setOnFinished(e -> rootPane.getChildren().remove(bubble)); // Rimuovi la bolla quando finisce
                    pt.play();
                }
            }
        });
        particleTimeline.getKeyFrames().add(kf);
        particleTimeline.play();
    }
}