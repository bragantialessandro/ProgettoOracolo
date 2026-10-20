package progettooracolo;

/**
 * impostazioni fisse del programma
 * se il server sta su un altro computer si cambia solo il valore dell host qui
 */
public final class Configurazione {

    /** indirizzo del computer dove gira il server */
    public static final String HOST = "127.0.0.1";

    /** numero della porta su cui il server ascolta */
    public static final int PORTA = 5000;

    /** costruttore privato perche la classe contiene solo costanti */
    private Configurazione() {
    }
}
