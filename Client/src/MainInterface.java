import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainInterface extends JFrame {
    private JButton listAllTools, searchForTool, todaysOrder, disconnect;
    private IO io;
    //private Border panelEdge = BorderFactory.createEtchedBorder();
    //private DefaultListModel<String> listModel;
    //private JList<String> listArea;

    private static final long serialVersionUID = 9050904299246341634L;

    public MainInterface(String s, IO receivedIo) {
        super(s);
        this.io = receivedIo;
        this.setSize(600, 400);
        this.setAlwaysOnTop(true);
        this.setLocation(700,700);
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
//    private JPanel createCenterPanel() {
//        JPanel centerPanel = new JPanel();
//        centerPanel.setBackground(new Color(150, 150, 150));
//        centerPanel.setBorder(panelEdge);
//        listModel = new DefaultListModel<String>();
//        listArea = new JList<String>(listModel);
//        String width = "1234567890123456789012345678901234567890";
//        listArea.setPrototypeCellValue(width);
//        listArea.setFont(new Font("Courier New", Font.BOLD, 12));
//        listArea.setVisibleRowCount(15);
//        listArea.addListSelectionListener(new ListListener());
//        listScrollPane = new JScrollPane(listArea);
//        listModel.addElement("Initial Value");
//        centerPanel.add(listScrollPane);
//        return centerPanel;
//    }
//
//    private JPanel createUpperPanel() {
//        JPanel upperPanel = new JPanel();
//        userInputTextField = new JTextField(10);
//        upperPanel.add(userInputTextField);
//        getUserInput = new JButton("Add Value");
//        getUserInput.addActionListener(new ButtonListener());
//        upperPanel.add(getUserInput);
//        clear = new JButton("ClearList");
//        clear.addActionListener(new ButtonListener());
//        upperPanel.add(clear);
//        load = new JButton("Load From File");
//        load.addActionListener(new ButtonListener());
//        upperPanel.add(load);
//        return upperPanel;
//    }
//
//    public class ListListener implements ListSelectionListener {
//        public void valueChanged(ListSelectionEvent e) {
//            int index = listArea.getSelectedIndex();
//            if (index >= 0) {
//                String line = (String) listModel.get(index);
//                selectedTextField.setText(line);
//            }
//        }
//    }

    public class ListAllToolsListener implements ActionListener {
        private IO io;

        public ListAllToolsListener(IO receivedIo){
            this.io = receivedIo;
        }

        @Override
        public void actionPerformed(ActionEvent event){

        }
    }

    public class SearchForToolByIdListener implements ActionListener {
        private IO io;
        private JTextField textField;

        public SearchForToolByIdListener(IO receivedIo, JTextField textField){
            this.textField = textField;
            this.io = receivedIo;
        }
        @Override
        public void actionPerformed(ActionEvent event){
            io.getSocketOut().println("3");
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

    public class SearchForToolListener implements ActionListener {
        private IO io;
        public JFrame frame;

        public SearchForToolListener(IO receivedIo){
            this.io = receivedIo;
        }

        @Override
        public void actionPerformed(ActionEvent event){
            new SearchForToolInterface("Search for Tool", io);
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


    public void activate(){
        this.setVisible(true);
    }
}

