import javax.swing.*;
import java.awt.*;

public class SearchForToolInterface extends JFrame {
    private final JTextField textfield2;
    private JTextField textfield1;
    private JButton byName, byId;
    private IO io;

    private static final long serialVersionUID = 034L;

    public SearchForToolInterface(String s, IO receivedIo){
        super(s);
        this.io = receivedIo;

        this.setSize(600,300);
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(2, 2));

        textfield1 = new JTextField("Enter Name Here ",10);
        textfield2 = new JTextField("Enter ID Here ",10);

        byName = new JButton("Search By Name");
        byName.addActionListener(new SearchForToolByNameListener(io, textfield1));
        byId = new JButton("Search By ID");
        byId.addActionListener(new SearchForToolByIdListener(io, textfield2));

        this.add("North", byName);
        this.add("South", byId);
        this.add(textfield1);
        this.add(textfield2);
        this.setVisible(true);
    }
}
