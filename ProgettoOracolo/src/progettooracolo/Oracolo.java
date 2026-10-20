package progettooracolo;

import java.util.Random;

/**
 * zoltar l oracolo
 * ogni client ha il suo oracolo con il suo set di risposte
 */
public class Oracolo {

    /** serve per scegliere una frase a caso */
    private final Random random = new Random();

    /** set di risposte in uso e all inizio è quello base */
    private TipoDifficolta difficolta = TipoDifficolta.BASE;

    /**
     * @return il set in uso
     */
    public TipoDifficolta getDifficolta() {
        return difficolta;
    }

    /**
     * sceglie il set in base al nome ricevuto usando uno switch
     *
     * @param nomeDifficolta il nome del set scelto dal client
     * @return true se il set esiste e false se il nome non è valido
     */
    public boolean impostaSet(String nomeDifficolta) {
        // toglie gli spazi e mette tutto minuscolo per confrontare facile
        switch (nomeDifficolta.trim().toLowerCase()) {
            case "base":
                difficolta = TipoDifficolta.BASE;
                break;
            case "cattivo":
                difficolta = TipoDifficolta.CATTIVO;
                break;
            case "napoletano":
                difficolta = TipoDifficolta.NAPOLETANO;
                break;
            case "altolocato":
                difficolta = TipoDifficolta.ALTOLOCATO;
                break;
            default:
                // nome sconosciuto quindi il set non cambia
                return false;
        }
        return true;
    }

    /**
     * sceglie una frase a caso dal set in uso
     *
     * @param domanda la domanda ricevuta e non serve per scegliere la risposta
     * @return la frase scelta
     */
    public String rispondi(String domanda) {
        String[] risposte = difficolta.getRisposte();
        // numero a caso da zero fino all ultima frase
        return risposte[random.nextInt(risposte.length)];
    }
}
