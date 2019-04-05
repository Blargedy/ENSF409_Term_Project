import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CancelListener implements ActionListener {

    JFrame windowToClose;

    public CancelListener(JFrame receivedFrame){
        this.windowToClose = receivedFrame;
    }

    @Override
    public void actionPerformed(ActionEvent event){
        windowToClose.setVisible(false);
    }
}
