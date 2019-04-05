import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PrintTodaysOrderListener implements ActionListener {
    private IO io;

    public PrintTodaysOrderListener(IO receivedIo){
        this.io = receivedIo;
    }

    @Override
    public void actionPerformed(ActionEvent event){
        io.getSocketOut().println("6");
        try {
            System.out.println(io.getObjectInputStream().readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
