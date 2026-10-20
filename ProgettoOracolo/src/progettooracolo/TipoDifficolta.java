package progettooracolo;

/**
 * i quattro set di risposte di zoltar
 * ogni set ha un nome da mostrare e una lista di frasi
 */
public enum TipoDifficolta {

    // risposte normali e gentili
    BASE("Base", new String[] {
        "Si, assolutamente!",
        "Le stelle dicono di si.",
        "Senza dubbio.",
        "Molto probabile, coraggio!",
        "Forse... ma ci credo, tu puoi farcela.",
        "Non e' chiaro, riprova piu' tardi.",
        "Concentrati e chiedimelo di nuovo.",
        "Non contarci troppo.",
        "Le mie fonti dicono di no."
    }),

    // risposte antipatiche e cattive
    CATTIVO("Cattivo", new String[] {
        "No. Mai. Neanche tra mille vite.",
        "Che domanda stupida: hai bisogno di un oracolo per questo?",
        "Il mio cristallo ha riso cosi' tanto che si e' incrinato.",
        "Si, ma solo perche' il destino ha pieta' di te.",
        "Ho visto il tuo futuro e ho chiesto un rimborso.",
        "Vai pure avanti, sara' divertente guardarti fallire.",
        "Persino un criceto con la sfera di cristallo farebbe meglio di te.",
        "Dopo questa domanda ho perso fiducia nell'umanita'.",
        "Sai che la risposta e' no, e lo sai da prima di chiedere."
    }),

    // risposte in dialetto napoletano
    NAPOLETANO("Napoletano", new String[] {
        "E certo che sì, guagliò!",
        "Ma che staje dicenno? Nun se pò proprio fà.",
        "Aggio guardato 'e stelle: ce stanno bone assaje!",
        "Chello che vuò tu nun se avvera manco si prieghe 'a Maronna.",
        "Statte tranquillo, tutto s'aggiusta.",
        "Mah, nun saccio... dimme 'a verità, tu ce credi overo?",
        "Eh, vulesse 'o ciel! Ma 'o destino è 'nu pezzo 'e fetenzia.",
        "Sì, ma primma magnate 'na pizza e nun pensà 'e fesserie.",
        "Jamme ja', nun me fà perdere tiempo cu sti domande!"
    }),

    // risposte con un linguaggio elegante e formale
    ALTOLOCATO("Linguaggio altolocato", new String[] {
        "Le stelle, mio caro, concordano con ammirevole unanimita'.",
        "Con sommo rammarico, devo comunicarle che l'esito appare sfavorevole.",
        "Una prospettiva quanto mai promettente, se mi consente l'osservazione.",
        "Ritengo, con la dovuta cautela, che l'affermativa sia assai plausibile.",
        "Mi duole constatarlo, ma il fato sembra di diverso avviso.",
        "La questione, signore, richiede ulteriore meditazione.",
        "Sarebbe invero una sorpresa di rara eleganza.",
        "Le sconsiglio vivamente di nutrire simili aspettative.",
        "Non vi e' dubbio alcuno: la risposta e' affermativa, con i miei omaggi."
    });

    /** nome del set da mostrare */
    private final String nome;

    /** lista delle frasi del set */
    private final String[] risposte;

    /**
     * crea un set con il suo nome e le sue frasi
     *
     * @param nome il nome da mostrare
     * @param risposte le frasi del set
     */
    TipoDifficolta(String nome, String[] risposte) {
        this.nome = nome;
        this.risposte = risposte;
    }

    /**
     * @return il nome del set
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return tutte le frasi del set
     */
    public String[] getRisposte() {
        return risposte;
    }
}
