import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ListAllToolsListener implements ActionListener {
    BufferedReader socketIn;
    PrintWriter socketOut;

    ListAllToolsListener(BufferedReader receivedSocketIn, PrintWriter receivedPrintWriter){
        this.socketIn = receivedSocketIn;
        this.socketOut = receivedPrintWriter;
    }

    @Override
    public void actionPerformed(ActionEvent event){
        socketOut.println("1");
        int numItems = 0;
        try {
            numItems = Integer.parseInt(socketIn.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < numItems; i++) {
            try {
                System.out.println(socketIn.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
