package Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Funzioni di supporto per leggere/scrivere righe su uno stream.
 * (Il suggerimento dell'esercizio: scriverle una sola volta e riutilizzarle.)
 */
public class StreamUtil {

    private StreamUtil() {
        // classe di sola utilita': non si istanzia
    }

    /**
     * Legge byte per byte fino a '\n'.
     * Restituisce null quando read() restituisce -1 (l'altra parte ha chiuso).
     */
    public static String readLine(InputStream in) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int b;
        while ((b = in.read()) != -1) {
            if (b == '\n') {
                return buffer.toString(StandardCharsets.UTF_8.name());
            }
            if (b != '\r') {          // ignoro il ritorno a capo di Windows
                buffer.write(b);
            }
        }
        if (buffer.size() > 0) {
            return buffer.toString(StandardCharsets.UTF_8.name());
        }
        return null;                  // -1: connessione chiusa
    }

    /** Scrive una riga terminata da '\n' e svuota il buffer. */
    public static void writeLine(OutputStream out, String testo) throws IOException {
        out.write((testo + "\n").getBytes(StandardCharsets.UTF_8));
        out.flush();
    }
}
