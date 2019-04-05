import java.io.*;
import java.net.Socket;

public class IO {
    private PrintWriter socketOut;
    private Socket dataSocket;
    private BufferedReader stdIn;
    private BufferedReader socketIn;
    private ObjectInputStream objectInputStream;

    public IO(String serverName, int portNumber){
        while(true){
            try {
                dataSocket = new Socket(serverName, portNumber);
                stdIn = new BufferedReader(new InputStreamReader(System.in));
                socketIn = new BufferedReader(new InputStreamReader(
                        dataSocket.getInputStream()));
                socketOut = new PrintWriter((dataSocket.getOutputStream()), true);
                objectInputStream = new ObjectInputStream(dataSocket.getInputStream());
                break;
            } catch (IOException e) {
                System.out.println("ERROR connecting to server. Trying again in 1s");
                continue;
            }
        }
    }

    public synchronized BufferedReader getStdIn() { return stdIn;}

    public synchronized PrintWriter getSocketOut() {
        return socketOut;
    }

    public synchronized BufferedReader getSocketIn() {
        return socketIn;
    }

    public synchronized ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }
}
