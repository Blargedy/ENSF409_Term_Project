import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;

public class ListAllToolsListener implements ActionListener {
    private IO io;

    public ListAllToolsListener(IO receivedIo){
        this.io = receivedIo;
    }

    @Override
    public void actionPerformed(ActionEvent event){
        io.getSocketOut().println("1");
        int numItems = 0;
        try {
            numItems = Integer.parseInt(io.getSocketIn().readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader tempSocketIn = io.getSocketIn();
        for(int i = 0; i < numItems; i++) {
            try {
                System.out.println(tempSocketIn.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
