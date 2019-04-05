import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class DisconnectListener implements ActionListener {
    PrintWriter socketOut;

    DisconnectListener(PrintWriter receivedPrintWriter){
        this.socketOut = receivedPrintWriter;
    }

    @Override
    public void actionPerformed(ActionEvent event){
        socketOut.println("7");
    }
}
