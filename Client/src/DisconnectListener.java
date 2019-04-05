import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisconnectListener implements ActionListener {
   private IO io;

   public DisconnectListener(IO receivedIo){
       this.io = receivedIo;
   }

    @Override
    public void actionPerformed(ActionEvent event){
        io.getSocketOut().println("7");
    }
}
