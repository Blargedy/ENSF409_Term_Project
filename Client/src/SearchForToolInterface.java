import javax.swing.*;
import java.awt.*;

public class SearchForToolInterface extends JFrame {
    private JFrame frame;
    private JPanel panel;
    private JButton OK, Cancel;
    private IO io;

    private static final long serialVersionUID = 034L;

    public SearchForToolInterface(String s, IO receivedIo){
        super(s);
        this.io = receivedIo;
        panel = new JPanel();
        panel.setSize(200,200);
        this.add(panel);
        this.setSize(600,300);
        this.setAlwaysOnTop(true);
        this.setLayout(new GridLayout(2,1));
        this.setLocation(600,0);
        this.setVisible(true);

        OK = new JButton("OK");
        OK.addActionListener(new SearchToolByNameListener(io));
        Cancel = new JButton("Cancel");
        Cancel.addActionListener(new CloseWIndowListener(this));
        panel.add("North", OK);
        panel.add("South", Cancel);
        panel.setVisible(true);
    }
}
