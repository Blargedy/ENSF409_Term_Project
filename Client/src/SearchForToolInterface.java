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
        this.add(panel);
        this.setSize(600,300);
        this.setAlwaysOnTop(true);
        this.setLayout(new GridLayout(2,1));
        this.setLocation(1200,1200);
        this.setVisible(true);
        panel = new JPanel();
        panel.setSize(200,200);
        panel.setVisible(true);

        OK = new JButton("OK");
        OK.addActionListener(new SearchToolByNameListener(io));
        Cancel = new JButton("Cancel");
        Cancel.addActionListener(new CloseWIndowListener(this));
        this.add("South", OK);
        this.add("South", Cancel);
        this.setVisible(true);
    }
}
