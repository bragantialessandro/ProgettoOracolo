package progettooracolo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * gestisce un solo client
 * ogni client ha il suo thread e il suo oracolo
 * cosi il server parla con tutti insieme e ognuno ha il proprio set di risposte
 */
public final class ClientHandler extends Thread {

    /** collegamento con il client */
    private final Socket socket;

    /** numero che identifica il client */
    private final int numero;

    /** oracolo personale di questo client */
    private final Oracolo oracolo = new Oracolo();

    /**
     * @param socket il collegamento con il client
     * @param numero il numero assegnato al client
     */
    public ClientHandler(Socket socket, int numero) {
        this.socket = socket;
        this.numero = numero;
    }

    /**
     * parte quando si fa start e serve il client finche non si disconnette
     */
    @Override
    public void run() {
        System.out.println("client n" + numero + " connesso " + socket.getInetAddress());

        // il try con le risorse chiude il socket da solo alla fine
        try (Socket s = socket) {
            InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream();

            // messaggio di benvenuto
            StreamUtil.writeLine(out, Protocollo.crea(Protocollo.OK,
                    "Sono Zoltar, sei il client n" + numero
                    + ". Set attuale: " + oracolo.getDifficolta().getNome()));

            // legge le righe finche il client non chiude
            String riga;
            while ((riga = StreamUtil.readLine(in)) != null) {
                System.out.println("client n" + numero + " ha scritto " + riga);
                // elabora la riga e manda indietro la risposta
                StreamUtil.writeLine(out, elabora(riga));
            }
        } catch (IOException e) {
            System.out.println("client n" + numero + " errore " + e.getMessage());
        }
        System.out.println("client n" + numero + " disconnesso");
    }

    /**
     * capisce cosa vuole il client e prepara la risposta
     *
     * @param riga la riga ricevuta dal client
     * @return la riga di risposta da inviare
     */
    private String elabora(String riga) {
        // il limite due taglia solo al primo underscore
        String[] parti = riga.split(Protocollo.SEP, 2);
        String comando = parti[0].trim().toUpperCase();

        // se manca il contenuto il messaggio non è valido
        if (parti.length < 2 || parti[1].trim().isEmpty()) {
            return Protocollo.crea(Protocollo.ERRORE, "formato non valido, usa TIPO_testo");
        }
        String parametro = parti[1].trim();

        // lo switch decide cosa fare in base al tipo di messaggio
        switch (comando) {
            case Protocollo.DIFFICOLTA:
                // il client vuole cambiare set
                if (oracolo.impostaSet(parametro)) {
                    return Protocollo.crea(Protocollo.OK,
                            "set impostato: " + oracolo.getDifficolta().getNome());
                }
                return Protocollo.crea(Protocollo.ERRORE,
                        "set sconosciuto (base, cattivo, napoletano, altolocato)");

            case Protocollo.DOMANDA:
                // il client fa una domanda e zoltar risponde
                return Protocollo.crea(Protocollo.RISPOSTA, oracolo.rispondi(parametro));

            default:
                return Protocollo.crea(Protocollo.ERRORE, "comando sconosciuto: " + parti[0]);
        }
    }
}
