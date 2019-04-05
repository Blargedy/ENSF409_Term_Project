import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

public class PrintTodaysOrderListener implements ActionListener {
    private PrintWriter socketOut;
    private ObjectInputStream objectInputStream;

    PrintTodaysOrderListener(ObjectInputStream receivedObjectInputStream, PrintWriter receivedPrintWriter){
        this.objectInputStream = receivedObjectInputStream;
        this.socketOut = receivedPrintWriter;
    }

    @Override
    public void actionPerformed(ActionEvent event){
        socketOut.println("6");

    }
}
