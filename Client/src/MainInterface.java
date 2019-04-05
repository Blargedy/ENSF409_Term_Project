import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

public class MainInterface extends JFrame {
    private JButton listAllTools, searchForTool, todaysOrder, disconnect;
    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private ObjectInputStream objectInputStream;

    private static final long serialVersionUID = 9050904299246341634L;

    public MainInterface(String s, BufferedReader receivedSocketIn, PrintWriter recievedPrintWriter, ObjectInputStream receivedObjectInputStream) {
        super(s);
        this.setSize(600, 400);
        this.setLayout(new GridLayout(2, 2));
        this.socketIn = receivedSocketIn;
        this.socketOut = recievedPrintWriter;
        this.objectInputStream = receivedObjectInputStream;
        listAllTools = new JButton("List All Tools");
        listAllTools.addActionListener(new ListAllToolsListener(socketIn, socketOut));
        searchForTool = new JButton("Search for Tool");
        searchForTool.addActionListener(new SearchForToolListener());
        todaysOrder = new JButton("Print Today's Order");
        todaysOrder.addActionListener(new PrintTodaysOrderListener(objectInputStream, socketOut));
        disconnect = new JButton("Disconnect");
        disconnect.addActionListener(new DisconnectListener(socketOut));
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

