package progettooracolo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * finestra del client a tema zoltar
 * si sceglie il set si scrive la domanda e si legge la risposta
 * la risposta di zoltar è evidenziata mentre la domanda dell utente è più semplice
 * il collegamento al server parte da solo appena la finestra si crea
 */
public class FinestraZoltar extends JFrame {

    /** colore oro usato per titoli bordi e bottoni */
    private static final Color ORO = new Color(235, 195, 70);

    /** colore chiaro per i testi normali */
    private static final Color TESTO_CHIARO = new Color(225, 215, 245);

    /** colore scuro per gli sfondi dei campi */
    private static final Color VIOLA_SCURO = new Color(30, 12, 55);

    /** parte di rete del client */
    private final Client client = new Client();

    /** menu per scegliere il set di risposte */
    private final JComboBox<String> sceltaDifficolta = new JComboBox<>(
            new String[] {"base", "cattivo", "napoletano", "altolocato"});

    /** campo dove si scrive la domanda */
    private final JTextField campoDomanda = new JTextField();

    /** bottone per mandare la domanda */
    private final JButton bottoneChiedi = new JButton("Chiedi a Zoltar");

    /** zona dove compaiono domande e risposte */
    private final JTextPane areaRisposte = new JTextPane();

    /** il testo contenuto nella zona delle risposte */
    private final StyledDocument documento = areaRisposte.getStyledDocument();

    /** stile delle domande dell utente */
    private final SimpleAttributeSet stileClient = new SimpleAttributeSet();

    /** stile evidenziato delle risposte di zoltar */
    private final SimpleAttributeSet stileServer = new SimpleAttributeSet();

    /** stile dei messaggi di servizio */
    private final SimpleAttributeSet stileInfo = new SimpleAttributeSet();

    /**
     * crea la finestra la disegna e si collega al server
     */
    public FinestraZoltar() {
        super("Zoltar l'oracolo");
        creaStili();
        costruisciInterfaccia();
        collegaEventi();

        // chiude solo la finestra e non tutto il programma
        // cosi il server resta acceso per gli altri client
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(620, 580);
        setLocationRelativeTo(null);

        // collegamento automatico al server
        connetti();
    }

    /**
     * prepara i tre stili del testo
     */
    private void creaStili() {
        // domanda del client testo azzurro e corsivo allineato a sinistra
        StyleConstants.setFontFamily(stileClient, "Serif");
        StyleConstants.setFontSize(stileClient, 15);
        StyleConstants.setItalic(stileClient, true);
        StyleConstants.setForeground(stileClient, new Color(150, 195, 255));
        StyleConstants.setAlignment(stileClient, StyleConstants.ALIGN_LEFT);
        StyleConstants.setSpaceBelow(stileClient, 4);

        // risposta del server testo oro in grassetto con sfondo viola e allineato a destra
        StyleConstants.setFontFamily(stileServer, "Serif");
        StyleConstants.setFontSize(stileServer, 17);
        StyleConstants.setBold(stileServer, true);
        StyleConstants.setForeground(stileServer, ORO);
        StyleConstants.setBackground(stileServer, new Color(95, 35, 130));
        StyleConstants.setAlignment(stileServer, StyleConstants.ALIGN_RIGHT);
        StyleConstants.setSpaceBelow(stileServer, 12);

        // messaggi di servizio piccoli grigi e al centro
        StyleConstants.setFontFamily(stileInfo, "SansSerif");
        StyleConstants.setFontSize(stileInfo, 12);
        StyleConstants.setForeground(stileInfo, new Color(160, 150, 185));
        StyleConstants.setAlignment(stileInfo, StyleConstants.ALIGN_CENTER);
        StyleConstants.setSpaceBelow(stileInfo, 8);
    }

    /**
     * mette al loro posto tutti i componenti della finestra
     */
    private void costruisciInterfaccia() {
        // sfondo con cielo e stelle che contiene tutto
        PannelloSfondo sfondo = new PannelloSfondo();
        sfondo.setLayout(new BorderLayout(8, 8));
        sfondo.setBorder(BorderFactory.createEmptyBorder(10, 15, 12, 15));
        setContentPane(sfondo);

        // parte alta con sfera titolo e sottotitolo
        JPanel titoli = new JPanel();
        titoli.setLayout(new BoxLayout(titoli, BoxLayout.Y_AXIS));
        titoli.setOpaque(false);
        JLabel titolo = new JLabel("Z O L T A R");
        titolo.setFont(new Font("Serif", Font.BOLD, 38));
        titolo.setForeground(ORO);
        JLabel sottotitolo = new JLabel("l'oracolo che sa tutto e ti dice la verità");
        sottotitolo.setFont(new Font("Serif", Font.ITALIC, 14));
        sottotitolo.setForeground(TESTO_CHIARO);
        titoli.add(titolo);
        titoli.add(sottotitolo);

        JPanel intestazione = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        intestazione.setOpaque(false);
        intestazione.add(new SferaDiCristallo());
        intestazione.add(titoli);

        // riga con la scelta del set
        JPanel rigaSet = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        rigaSet.setOpaque(false);
        JLabel etichettaSet = new JLabel("scegli il set di risposte");
        etichettaSet.setForeground(TESTO_CHIARO);
        stilizzaMenu(sceltaDifficolta);
        rigaSet.add(etichettaSet);
        rigaSet.add(sceltaDifficolta);

        JPanel nord = new JPanel(new BorderLayout(0, 8));
        nord.setOpaque(false);
        nord.add(intestazione, BorderLayout.NORTH);
        nord.add(rigaSet, BorderLayout.SOUTH);

        // zona centrale con le risposte
        areaRisposte.setEditable(false);
        areaRisposte.setBackground(new Color(22, 8, 42));
        areaRisposte.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        JScrollPane scorrimento = new JScrollPane(areaRisposte);
        //scorrimento.setBorder(BorderFactory.createLineBorder(ORO, 2));
        scorrimento.getViewport().setBackground(new Color(22, 8, 42));

        // parte bassa con domanda e bottone
        JLabel etichettaDomanda = new JLabel("la tua domanda");
        etichettaDomanda.setForeground(TESTO_CHIARO);
        stilizzaCampo(campoDomanda);
        stilizzaBottone(bottoneChiedi);
        JPanel sud = new JPanel(new BorderLayout(8, 0));
        sud.setOpaque(false);
        sud.add(etichettaDomanda, BorderLayout.WEST);
        sud.add(campoDomanda, BorderLayout.CENTER);
        sud.add(bottoneChiedi, BorderLayout.EAST);

        sfondo.add(nord, BorderLayout.NORTH);
        sfondo.add(scorrimento, BorderLayout.CENTER);
        sfondo.add(sud, BorderLayout.SOUTH);
    }

    /**
     * da ai campi e ai bottoni i colori di zoltar
     *
     * @param campo il campo di testo da colorare
     */
    private void stilizzaCampo(JTextField campo) {
        campo.setBackground(VIOLA_SCURO);
        campo.setForeground(TESTO_CHIARO);
        campo.setCaretColor(ORO);
        campo.setFont(new Font("Serif", Font.PLAIN, 15));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ORO, 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        
    }
      
    /**
     * colora il menu a tendina
     *
     * @param menu il menu da colorare
     */
    private void stilizzaMenu(JComboBox<String> menu) {
        menu.setBackground(VIOLA_SCURO);
        menu.setForeground(ORO);
        menu.setFont(new Font("Serif", Font.BOLD, 14));
    }

    /**
     * colora un bottone in oro
     *
     * @param bottone il bottone da colorare
     */
    private void stilizzaBottone(JButton bottone) {
        bottone.setBackground(ORO);
        bottone.setForeground(new Color(40, 10, 60));
        bottone.setFont(new Font("Serif", Font.BOLD, 14));
        bottone.setFocusPainted(false);
        bottone.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 110, 30), 1),
                BorderFactory.createEmptyBorder(6, 14, 6, 14)));
    }

    /**
     * collega bottone invio e chiusura finestra alle azioni
     */
    private void collegaEventi() {
        bottoneChiedi.addActionListener(e -> chiedi());
        // premere invio nel campo fa la stessa cosa del bottone
        campoDomanda.addActionListener(e -> chiedi());

        // quando la finestra si chiude si chiude anche il collegamento
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                client.chiudi();
            }
        });
    }

    /**
     * si collega al server in automatico
     */
    private void connetti() {
        try {
            // le righe ricevute arrivano da un altro thread
            // invokeLater le porta nel thread della grafica
            client.connetti(Configurazione.HOST, Configurazione.PORTA,
                    riga -> SwingUtilities.invokeLater(() -> mostra(riga)),
                    () -> SwingUtilities.invokeLater(this::connessioneChiusa));
            abilitaDomande(true);
        } catch (IOException ex) {
            aggiungi("impossibile collegarsi al server", stileInfo);
            abilitaDomande(false);
        }
    }

    /**
     * manda al server il set scelto e la domanda scritta
     */
    private void chiedi() {
        String domanda = campoDomanda.getText().trim();
        // senza domanda o senza collegamento non si fa niente
        if (domanda.isEmpty() || !client.isConnesso()) {
            return;
        }
        try {
            // prima il set cosi il server usa quello scelto poi la domanda
            client.invia(Protocollo.DIFFICOLTA, (String) sceltaDifficolta.getSelectedItem());
            client.invia(Protocollo.DOMANDA, domanda);
            aggiungi("Tu: " + domanda, stileClient);
            campoDomanda.setText("");
        } catch (IOException ex) {
            aggiungi("errore nell invio", stileInfo);
        }
    }

    /**
     * capisce la riga ricevuta e la mostra con lo stile giusto
     *
     * @param riga la riga arrivata dal server
     */
    private void mostra(String riga) {
        // il limite due taglia solo al primo underscore
        String[] parti = riga.split(Protocollo.SEP, 2);
        if (parti.length < 2) {
            aggiungi(riga, stileInfo);
            return;
        }
        switch (parti[0]) {
            case Protocollo.RISPOSTA:
                // la risposta di zoltar è evidenziata
                aggiungi(" Zoltar dice: " + parti[1] + " ", stileServer);
                break;
            case Protocollo.ERRORE:
                aggiungi("errore " + parti[1], stileInfo);
                break;
            default:
                // la conferma del set non serve all utente e si salta
                if (!parti[1].startsWith("difficoltà impostata")) {
                    aggiungi(parti[1], stileInfo);
                }
        }
    }

    /**
     * aggiunge una riga di testo in fondo con lo stile indicato
     *
     * @param testo il testo da scrivere
     * @param stile lo stile da usare
     */
    private void aggiungi(String testo, SimpleAttributeSet stile) {
        try {
            int inizio = documento.getLength();
            documento.insertString(inizio, testo + "\n", stile);
            // lo stile del paragrafo serve per allineamento e spazi
            documento.setParagraphAttributes(inizio, testo.length() + 1, stile, false);
            // porta la vista in fondo per vedere sempre l ultima riga
            areaRisposte.setCaretPosition(documento.getLength());
        } catch (BadLocationException e) {
            // posizione non valida e non succede mai perche si scrive in fondo
        }
    }

    /**
     * avvisa che il server ha chiuso e blocca i comandi
     */
    private void connessioneChiusa() {
        aggiungi("connessione chiusa", stileInfo);
        abilitaDomande(false);
    }

    /**
     * attiva o disattiva i comandi
     *
     * @param abilitato true per attivarli e false per disattivarli
     */
    private void abilitaDomande(boolean abilitato) {
        campoDomanda.setEnabled(abilitato);
        bottoneChiedi.setEnabled(abilitato);
        sceltaDifficolta.setEnabled(abilitato);
    }
}
