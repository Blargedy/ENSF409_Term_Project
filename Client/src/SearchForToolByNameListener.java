import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SearchForToolByNameListener implements ActionListener {
    private IO io;
    private JTextField textField;

    public SearchForToolByNameListener(IO receivedIo, JTextField textField) {
        this.io = receivedIo;
        this.textField = textField;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        io.getSocketOut().println("2");
        io.getSocketOut().println(textField.getText());
        try {
            System.out.println(io.getObjectInputStream().readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
