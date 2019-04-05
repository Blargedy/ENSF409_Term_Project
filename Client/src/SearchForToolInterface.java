import javax.swing.*;
import java.awt.*;

public class SearchForToolInterface extends JFrame {
    private JButton OK, Cancel;
    private IO io;

    private static final long serialVersionUID = 034L;

    public SearchForToolInterface(String s, IO receivedIo){
        super(s);
        this.io = receivedIo;
        this.setSize(600,300);
        this.setAlwaysOnTop(true);
        this.setLayout(new GridLayout(2,1));
        this.setLocation(1200,1200);

        OK = new JButton("OK");
        OK.addActionListener(new SearchToolByNameListener(io));
        Cancel = new JButton("Cancel");
        Cancel.addActionListener(new CancelListener(this));
        this.add("South", OK);
        this.add("South", Cancel);
        this.setVisible(true);
    }
}
