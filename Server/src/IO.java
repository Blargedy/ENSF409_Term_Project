import java.io.*;
import java.net.Socket;

public class IO {
    private PrintWriter socketOut;
    private BufferedReader socketIn;
    private ObjectOutputStream objectOutputStream;

    public IO(Socket receivedSocket){
        while(true){
            try {
                socketIn = new BufferedReader(new InputStreamReader(receivedSocket.getInputStream()))
                socketOut = new PrintWriter(receivedSocket.getOutputStream(), true);
                objectOutputStream = new ObjectOutputStream(receivedSocket.getOutputStream());
                break;
            } catch (IOException e) {
                System.out.println("ERROR connecting to server. Trying again in 1s");
                continue;
            }
        }
    }

    public synchronized PrintWriter getSocketOut() {
        return socketOut;
    }

    public synchronized BufferedReader getSocketIn() {
        return socketIn;
    }

    public synchronized ObjectInputStream getObjectOutputStream() {
        return objectOutputStream;
    }
}
