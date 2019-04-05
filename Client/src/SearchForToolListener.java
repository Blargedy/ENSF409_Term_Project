import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SearchForToolListener implements ActionListener {
    private IO io;
    public JFrame frame;


    public SearchForToolListener(IO receivedIo){
        this.io = receivedIo;
    }

    @Override
    public void actionPerformed(ActionEvent event){
        try {
            new SearchForToolInterface("Search for Tool", io);

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