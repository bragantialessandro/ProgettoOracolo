package progettooracolo;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * punto di partenza del programma
 * la prima copia avviata apre anche il server
 * le copie successive trovano la porta occupata e aprono solo il client
 * cosi esiste sempre un solo server e tanti client
 */
public class ProgettoOracolo {

    /**
     * @param args non usati
     */
    public static void main(String[] args) {

        // prova ad aprire il server
        Server server = new Server(Configurazione.PORTA);
        if (server.apriPorta()) {
            // la porta era libera quindi questo è il server unico
            server.start();
            System.out.println("server avviato sulla porta " + Configurazione.PORTA);
            System.out.println("per fermare il server premi stop in netbeans");
        } else {
            // la porta era occupata quindi il server esiste già
            System.out.println("il server è già attivo e si apre solo il client");
        }

        // in ogni caso si apre la finestra del client
        SwingUtilities.invokeLater(() -> new FinestraZoltar().setVisible(true));
    }
}
