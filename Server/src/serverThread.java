import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class serverThread implements Runnable{
    private Socket aSocket;
    private BufferedReader socketInput;
    private PrintWriter socketOutput;

    public serverThread(Socket aSocket){
        this.aSocket = aSocket;
        System.out.println("New connection started");
        try {
            socketInput = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
            socketOutput = new PrintWriter(aSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Unable to create input or output streams");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        StringBuffer line = null;
        while (true) {
            try {
                line = new StringBuffer(socketInput.readLine());
            } catch (IOException e) {
                System.out.println("Unable to read line from socket");
                e.printStackTrace();
            }
            if (line != null) {
                if (line.toString().equals("QUIT")) {
                    break;
                }
                if (line.toString().equals("DATE")) {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
                    socketOutput.println( sdf.format(cal.getTime()));
                } else if (line.toString().equals("TIME")) {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    socketOutput.println( sdf.format(cal.getTime()));
                }else {
                    socketOutput.println("Wrong input, please try again");
                }
            }
        }
        try {
            socketInput.close();
            socketOutput.close();
            System.out.println("Connection closed");
        } catch (IOException e) {
            System.out.println("Unable to close sockets");
            e.printStackTrace();
        }
    }
}
