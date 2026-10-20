package progettooracolo;

import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * parte di rete del client senza grafica
 * si collega al server e invia i messaggi
 * la ricezione è affidata al thread ricevi
 */
public class Client {

    /** collegamento con il server */
    private Socket socket;

    /**
     * si collega al server e fa partire il thread che ascolta
     *
     * @param host indirizzo del server
     * @param porta porta del server
     * @param allaRiga azione da fare per ogni riga ricevuta
     * @param allaChiusura azione da fare quando la connessione finisce
     * @throws IOException se il server non è raggiungibile
     */
    public void connetti(String host, int porta, Consumer<String> allaRiga, Runnable allaChiusura)
            throws IOException {
        socket = new Socket(host, porta);
        new ThreadRicevi(socket, allaRiga, allaChiusura).start();
    }

    /**
     * invia un messaggio al server
     *
     * @param tipo il tipo del messaggio
     * @param testo il contenuto del messaggio
     * @throws IOException se l invio va male
     */
    public void invia(String tipo, String testo) throws IOException {
        StreamUtil.writeLine(socket.getOutputStream(), Protocollo.crea(tipo, testo));
    }

    /**
     * @return true se il collegamento è aperto
     */
    public boolean isConnesso() {
        return socket != null && !socket.isClosed();
    }

    /**
     * chiude il collegamento con il server
     */
    public void chiudi() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ignored) {
            // se la chiusura fallisce non c è niente da fare
        }
    }
}
