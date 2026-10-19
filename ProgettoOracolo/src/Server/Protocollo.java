package Server;

/**
 * Costanti del protocollo. Ogni messaggio e' una riga del tipo:
 *
 *   COMANDO_parametro
 *
 * Il separatore e' "_". Si usa split("_", 2): il limite 2 spezza SOLO al
 * primo "_", cosi' una domanda che contiene altri "_" non viene rovinata.
 *
 * CLIENT -> SERVER
 *   DIFFICOLTA_facile | DIFFICOLTA_media | DIFFICOLTA_difficile
 *   DOMANDA_<testo della domanda>
 *
 * SERVER -> CLIENT
 *   OK_<messaggio>        comando eseguito
 *   RISPOSTA_<testo>      risposta di Zoltar
 *   ERRORE_<messaggio>    comando non valido
 */
public final class Protocollo {

    public static final String SEP = "_";

    public static final String DIFFICOLTA = "DIFFICOLTA";
    public static final String DOMANDA = "DOMANDA";

    public static final String OK = "OK";
    public static final String RISPOSTA = "RISPOSTA";
    public static final String ERRORE = "ERRORE";

    private Protocollo() {
    }

    /** Costruisce una riga di protocollo: tipo + "_" + testo. */
    public static String crea(String tipo, String testo) {
        return tipo + SEP + testo;
    }
}
