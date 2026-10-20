package progettooracolo;

/**
 * regole dei messaggi tra client e server
 * ogni messaggio è una riga fatta da un tipo e da un contenuto separati da un underscore
 * si usa lo split con limite due cosi si taglia solo al primo underscore
 * e la domanda può contenere altri underscore senza problemi
 * il client manda il tipo set con il nome del set oppure il tipo domanda con il testo
 * il server risponde con il tipo ok oppure risposta oppure errore
 */
public final class Protocollo {

    /** carattere che separa il tipo dal contenuto */
    public static final String SEP = "_";

    /** tipo del messaggio che sceglie il set di risposte */
    public static final String DIFFICOLTA = "SET";

    /** tipo del messaggio che contiene la domanda */
    public static final String DOMANDA = "DOMANDA";

    /** tipo del messaggio che dice che tutto è andato bene */
    public static final String OK = "OK";

    /** tipo del messaggio che contiene la risposta di zoltar */
    public static final String RISPOSTA = "RISPOSTA";

    /** tipo del messaggio che segnala un errore */
    public static final String ERRORE = "ERRORE";

    /** costruttore privato perche la classe contiene solo costanti */
    private Protocollo() {
    }

    /**
     * mette insieme tipo e contenuto in una sola riga
     *
     * @param tipo il tipo del messaggio
     * @param testo il contenuto del messaggio
     * @return la riga pronta da inviare
     */
    public static String crea(String tipo, String testo) {
        return tipo + SEP + testo;
    }
}
