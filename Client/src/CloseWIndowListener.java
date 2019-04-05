import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseWIndowListener implements ActionListener {
    private JFrame windowToClose;

    public CloseWIndowListener(JFrame receivedFrame){
        this.windowToClose = receivedFrame;
    }

    @Override
    public void actionPerformed(ActionEvent event){
        windowToClose.setVisible(false);
    }
}
