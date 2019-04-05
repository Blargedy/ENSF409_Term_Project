import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SearchForToolListener implements ActionListener {
    private IO io;

    public SearchForToolListener(IO receivedIo){
        this.io = receivedIo;
    }

    @Override
    public void actionPerformed(ActionEvent event){
        try {
            //"Enter name of tool"
            System.out.println(io.getSocketIn().readLine());
            io.getSocketOut().println(io.getStdIn().readLine());
            try {
                System.out.println(io.getObjectInputStream().readObject());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}