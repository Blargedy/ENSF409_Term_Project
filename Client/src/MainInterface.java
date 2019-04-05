import javax.swing.*;
import java.awt.*;

public class MainInterface extends JFrame {
    private JButton listAllTools, searchForTool, todaysOrder, disconnect;
    private IO io;

    private static final long serialVersionUID = 9050904299246341634L;

    public MainInterface(String s, IO receivedIo) {
        super(s);
        this.io = receivedIo;
        this.setSize(600, 400);
        this.setLayout(new GridLayout(2, 2));
        listAllTools = new JButton("List All Tools");
        listAllTools.addActionListener(new ListAllToolsListener(io));
        searchForTool = new JButton("Search for Tool");
        searchForTool.addActionListener(new SearchForToolListener(io));
        todaysOrder = new JButton("Print Today's Order");
        todaysOrder.addActionListener(new PrintTodaysOrderListener(io));
        disconnect = new JButton("Disconnect");
        disconnect.addActionListener(new DisconnectListener(io));
        add("South", listAllTools);
        add("South", searchForTool);
        add("South", todaysOrder);
        add("South", disconnect);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        activate();
    }

    public void activate(){
        this.setVisible(true);
    }
}

