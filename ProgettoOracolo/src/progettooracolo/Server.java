package progettooracolo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * server unico del programma
 * per ogni client che si collega crea un thread dedicato
 * se un server esiste già la porta è occupata e non se ne crea un altro
 */
public class Server extends Thread {

    /** numero della porta su cui ascoltare */
    private final int porta;

    /** il socket che aspetta i client */
    private ServerSocket serverSocket;

    /**
     * @param porta la porta su cui ascoltare
     */
    public Server(int porta) {
        this.porta = porta;
    }

    /**
     * prova ad aprire la porta
     * se è già occupata vuol dire che un server è già attivo
     *
     * @return true se la porta è stata aperta e false se il server esiste già
     */
    public boolean apriPorta() {
        try {
            serverSocket = new ServerSocket(porta);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * parte con start e accetta i client uno dopo l altro per sempre
     */
    @Override
    public void run() {
        int numero = 0;
        try {
            while (true) {
                // si ferma qui finche non arriva un nuovo client
                Socket socket = serverSocket.accept();
                numero++;
                // un thread nuovo per il client e il ciclo riparte subito
                new ClientHandler(socket, numero).start();
            }
        } catch (IOException e) {
            System.out.println("errore del server: " + e.getMessage());
        }
    }
}
