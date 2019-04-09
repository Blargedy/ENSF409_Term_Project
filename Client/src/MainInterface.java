import javax.print.DocFlavor;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @author Mohammed Moshirpour
 * @author Bernard Kleiner
 */

public class MainInterface extends JFrame {
    private JButton listAllTools, searchForTool, todaysOrder, disconnect, adjustQuantity, searchOk;
    private IO io;
    private Border panelEdge = BorderFactory.createEtchedBorder();
    private DefaultListModel<String> listModel;
    private JList<String> listArea;
    private JScrollPane listScrollPane;
    private JTextField selectedTextField, userInputTextField;

    private static final long serialVersionUID = 9050904299246341634L;

    public MainInterface(String s, IO receivedIo) {
        super(s);
        this.io = receivedIo;
        this.setSize(800, 400);
        this.setAlwaysOnTop(true);
        this.setLocation(700,700);
        this.setTitle("Inventory Client");

        this.add(createLowerPanel(), BorderLayout.SOUTH);
        this.add(createCenterPanel(), BorderLayout.CENTER);
        this.add(createUpperPanel(), BorderLayout.NORTH);
        this.add(createRightPanel(), BorderLayout.EAST);

//        listAllTools = new JButton("List All Tools");
//        listAllTools.addActionListener(new ListAllToolsListener(io));
//        searchByName = new JButton("Search for Tool");
//        searchByName.addActionListener(new SearchForToolListener(io));
//        todaysOrder = new JButton("Print Today's Order");
//        todaysOrder.addActionListener(new PrintTodaysOrderListener(io));
//        disconnect = new JButton("Disconnect");
//        disconnect.addActionListener(new DisconnectListener(io));
//        add("South", listAllTools);
//        add("South", searchByName);
//        add("South", todaysOrder);
//        add("South", disconnect);

        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        activate();
    }
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(150, 150, 150));
        centerPanel.setBorder(panelEdge);
        listModel = new DefaultListModel<String>();
        listArea = new JList<String>(listModel);
        String width = "1234567890123456789012345678901234567890";
        listArea.setPrototypeCellValue(width);
        listArea.setFont(new Font("Courier New", Font.BOLD, 12));
        listArea.setVisibleRowCount(15);
        listArea.addListSelectionListener(new ListListener());
        listScrollPane = new JScrollPane(listArea);
//        listModel.addElement("Initial Value");
        centerPanel.add(listScrollPane);
        return centerPanel;
    }

    private JPanel createLowerPanel() {
        JPanel lowerPanel = new JPanel();

//        userInputTextField = new JTextField(10);
//        lowerPanel.add(userInputTextField);

        adjustQuantity = new JButton("Decrease Quantity");
        adjustQuantity.addActionListener(new AdjustQuantityListener(io));
        lowerPanel.add(adjustQuantity);

        todaysOrder = new JButton("Print Order");
        todaysOrder.addActionListener(new PrintTodaysOrderListener(io));
        lowerPanel.add(todaysOrder);

//        searchForTool = new JButton("Search");
//        searchForTool.addActionListener(new SearchForToolListener(io));
//        lowerPanel.add(searchForTool);

        listAllTools = new JButton("List All");
        listAllTools.addActionListener(new ListAllToolsListener(io));
        lowerPanel.add(listAllTools);


        disconnect = new JButton("Disconnect");
        disconnect.addActionListener(new DisconnectListener(io));
        lowerPanel.add(disconnect);
        return lowerPanel;
    }

    private JPanel createUpperPanel() {
        JPanel upperPanel = new JPanel();
        upperPanel.setBackground(new Color(100, 100, 100));
        JLabel label = new JLabel("Selected Value:");
        selectedTextField = new JTextField(20);

        upperPanel.add(label);
        upperPanel.add(selectedTextField);
        return upperPanel;
    }

    private JPanel createRightPanel(){
        JPanel rightPanel = new JPanel();
        JLabel label = new JLabel("Search for Item");
        userInputTextField = new JTextField(10);

        searchOk = new JButton("OK");
        searchOk.addActionListener(new SearchForToolListener(io, userInputTextField));

        rightPanel.add(label);
        rightPanel.add(userInputTextField);
        rightPanel.add(searchOk);
        return rightPanel;
    }

    public class ListListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            int index = listArea.getSelectedIndex();
            if (index >= 0) {
                String line = (String) listModel.get(index);
                selectedTextField.setText(line);
            }
        }
    }

    public class ListAllToolsListener implements ActionListener {
        private IO io;

        public ListAllToolsListener(IO receivedIo){
            this.io = receivedIo;
        }

        @Override
        public void actionPerformed(ActionEvent event){

        }
    }

    public class SearchForToolListener implements ActionListener {
        private IO io;
        private JTextField textField;

        public SearchForToolListener(IO receivedIo, JTextField textField){
            this.io = receivedIo;
            this.textField = textField;
        }

        @Override
        public void actionPerformed(ActionEvent event){
            io.getSocketOut().println("2");
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

    public class AdjustQuantityListener implements ActionListener{
        IO io;

        AdjustQuantityListener(IO receivedIo){
            this.io = receivedIo;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            return;
        }
    }

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

    public class PrintTodaysOrderListener implements ActionListener {
        private IO io;

        public PrintTodaysOrderListener(IO receivedIo){
            this.io = receivedIo;
        }

        @Override
        public void actionPerformed(ActionEvent event){
            io.getSocketOut().println("6");
            try {
                System.out.println(io.getObjectInputStream().readObject());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public class DisconnectListener implements ActionListener {
        private IO io;

        public DisconnectListener(IO receivedIo){
            this.io = receivedIo;
        }

        @Override
        public void actionPerformed(ActionEvent event){
            io.getSocketOut().println("7");
        }
    }

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

//            byName = new JButton("Search By Name");
//            byName.addActionListener(new SearchForToolByNameListener(io, textfield1));
//            byId = new JButton("Search By ID");
//            byId.addActionListener(new SearchForToolByIdListener(io, textfield2));

            this.add("North", byName);
            this.add("South", byId);
            this.add(textfield1);
            this.add(textfield2);
            this.setVisible(true);
        }
    }



    public void activate(){
        this.setVisible(true);
    }
}

