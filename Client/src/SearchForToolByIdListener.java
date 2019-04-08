import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SearchForToolByIdListener implements ActionListener {
    private IO io;
    private JTextField textField;

    public SearchForToolByIdListener(IO receivedIo, JTextField textField){
        this.textField = textField;
        this.io = receivedIo;
    }
    @Override
    public void actionPerformed(ActionEvent event){
        io.getSocketOut().println("3");
        io.getSocketOut().println(textField.getText());
        try {
            System.out.print(io.getObjectInputStream().readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
