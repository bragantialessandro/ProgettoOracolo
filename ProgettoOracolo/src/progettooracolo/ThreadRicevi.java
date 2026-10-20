package progettooracolo;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * thread del client che ascolta il server
 * legge le righe dal socket e le passa alla finestra
 * finisce quando la connessione si chiude
 */
public final class ThreadRicevi extends Thread {

    /** collegamento con il server */
    private final Socket socket;

    /** cosa fare con ogni riga ricevuta */
    private final Consumer<String> allaRiga;

    /** cosa fare quando la connessione finisce */
    private final Runnable allaChiusura;

    /**
     * @param socket il collegamento con il server
     * @param allaRiga azione da fare per ogni riga ricevuta
     * @param allaChiusura azione da fare quando la connessione finisce
     */
    public ThreadRicevi(Socket socket, Consumer<String> allaRiga, Runnable allaChiusura) {
        this.socket = socket;
        this.allaRiga = allaRiga;
        this.allaChiusura = allaChiusura;
        // thread daemon cosi non tiene acceso il programma quando si chiude la finestra
        setDaemon(true);
    }

    /**
     * parte con start e legge finche il server non chiude
     */
    @Override
    public void run() {
        try {
            InputStream in = socket.getInputStream();
            String riga;
            // continua finche la lettura non restituisce null
            while ((riga = StreamUtil.readLine(in)) != null) {
                allaRiga.accept(riga);
            }
        } catch (IOException e) {
            // il socket è stato chiuso dal client stesso e va bene cosi
        }
        allaChiusura.run();
    }
}
