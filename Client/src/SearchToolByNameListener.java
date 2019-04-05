import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SearchToolByNameListener implements ActionListener {
    private IO io;

    public SearchToolByNameListener(IO receivedIo){
        this.io = receivedIo;
    }
    @Override
    public void actionPerformed(ActionEvent event){
        try {
            io.getSocketOut().println("2");
            io.getSocketOut().println(io.getStdIn().readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
