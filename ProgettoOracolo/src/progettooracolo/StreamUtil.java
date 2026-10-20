package progettooracolo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * funzioni di aiuto per leggere e scrivere righe di testo su uno stream
 * si scrivono una volta sola e si usano in tutto il programma
 */
public class StreamUtil {

    /** costruttore privato perche la classe ha solo metodi statici */
    private StreamUtil() {
    }

    /**
     * legge un byte alla volta fino al ritorno a capo
     *
     * @param in lo stream da cui leggere
     * @return la riga letta oppure null se la connessione è chiusa
     * @throws IOException se la lettura va male
     */
    public static String readLine(InputStream in) throws IOException {
        // contenitore dove si mettono i byte della riga
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int b;

        // legge finche non arriva meno uno che vuol dire connessione chiusa
        while ((b = in.read()) != -1) {
            // il ritorno a capo vuol dire che la riga è finita
            if (b == '\n') {
                return buffer.toString(StandardCharsets.UTF_8.name());
            }
            // il carattere di windows si ignora
            if (b != '\r') {
                buffer.write(b);
            }
        }

        // se la connessione si chiude ma qualcosa è stato letto lo restituisce
        if (buffer.size() > 0) {
            return buffer.toString(StandardCharsets.UTF_8.name());
        }

        // niente da leggere e connessione chiusa
        return null;
    }

    /**
     * scrive una riga e manda subito i dati
     *
     * @param out lo stream su cui scrivere
     * @param testo la riga da scrivere senza il ritorno a capo
     * @throws IOException se la scrittura va male
     */
    public static void writeLine(OutputStream out, String testo) throws IOException {
        // aggiunge il ritorno a capo e trasforma il testo in byte
        out.write((testo + "\n").getBytes(StandardCharsets.UTF_8));
        // flush spinge i dati verso laltra parte senza aspettare
        out.flush();
    }
}
