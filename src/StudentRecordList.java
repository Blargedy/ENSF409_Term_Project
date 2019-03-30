import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


/**
 * File taken from D2L course page
 * @author Mohammad Moshirpour
 * @author Berni Kleiner
 * @since Mar 28 2019
 */
public class StudentRecordList extends JFrame {

    private static final long serialVersionUID = 1L;
    // Attributes
    private JTextField userInputTextField;
    private JTextField selectedTextField;
    private JButton insert;
    private JButton createTree;
    private JButton browse;
    private JButton find;
    private JButton filenameOkButton;
    private JButton filenameCancelButton;
    private JTextField filenameField;
    private JDialog fileDialog;
    private JList<String> listArea;
    private DefaultListModel<String> listModel;
    private BinSearchTree studentRecordTree;

    private JScrollPane listScrollPane;
    private Border panelEdge = BorderFactory.createEtchedBorder();

    // Constructor
    public StudentRecordList() {
        this.setTitle("Main Window");
        this.setBounds(300, 250, 500, 400);

        this.add(createUpperPanel(), BorderLayout.NORTH);
        this.add(createCenterPanel(), BorderLayout.CENTER);
        this.add(createLowerPanel(), BorderLayout.SOUTH);

        this.setVisible(true);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
    }

    /**
     * Method to create the upper panel that will hold a JTextField and a
     * JLabel. This method creates the panel and returns it to be added to the
     * container.
     */
    private JPanel createUpperPanel() {
        JPanel upperPanel = new JPanel();
        upperPanel.setBackground(new Color(230, 230, 230));
        JLabel label = new JLabel("An Application to Maintain Student Records");
        //selectedTextField = new JTextField(20);

        upperPanel.add(label);
        //upperPanel.add(selectedTextField);
        return upperPanel;
    }

    /**
     * Method to create the center panel that will hold the JList. This method
     * creates the panel and returns it to be added to the container.
     */
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        //centerPanel.setBackground(new Color(150, 150, 150));
        centerPanel.setBorder(panelEdge);
        listModel = new DefaultListModel<String>();
        listArea = new JList<String>(listModel);
        String width = "1234567890123456789012345678901234567890";
        listArea.setPrototypeCellValue(width);
        listArea.setFont(new Font("Courier New", Font.BOLD, 12));
        listArea.setVisibleRowCount(15);
        listArea.addListSelectionListener(new ListListener());
        listScrollPane = new JScrollPane(listArea);
        //listModel.addElement("Initial Value");
        centerPanel.add(listScrollPane);
        return centerPanel;
    }


    /**
     * Method to create the lower panel that will hold the text field, button
     * and label. This method creates the panel and returns it to be added to
     * the container.
     */
    private JPanel createLowerPanel() {
        JPanel lowerPanel = new JPanel();
        insert = new JButton("Insert");
        insert.addActionListener(new ButtonListener());
        lowerPanel.add(insert);
        find = new JButton("Find");
        find.addActionListener(new ButtonListener());
        lowerPanel.add(find);
        browse = new JButton("Browse");
        browse.addActionListener(new ButtonListener());
        lowerPanel.add(browse);
        createTree = new JButton("Create Tree from File");
        createTree.addActionListener(new ButtonListener());
        lowerPanel.add(createTree);

        return lowerPanel;
    }

    /**
     * takes the name of a file as input and parses the contents into a binary search tree
     * @param filename name of the file from which to read
     * @return binary search tree containing data with each line of the tree being one line in the
     * text input file
     */
    public BinSearchTree readFile(String filename){
        File file;
        BufferedReader br;
        BinSearchTree BST = new BinSearchTree();
        try{
            file = new File(filename);
            br = new BufferedReader(new FileReader(file));
            try {
                String line;
                while ((line = br.readLine()) != null){
                    listModel.addElement(line);
                    String[] splitLine = line.split("\\s+");
                    BST.insert(splitLine[1], splitLine[2], splitLine[3], splitLine[4]);
                }

            } catch (IOException e) {
                System.out.println("IO exception");
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found exception");
            e.printStackTrace();
        }
        return BST;
    }

    // ******************Listeners****************

    public class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == insert) {
                String text = userInputTextField.getText();
                if(text.length()>0)
                    listModel.addElement(text);
            }
            else if(e.getSource() == find){
                if(studentRecordTree.empty())
                    System.out.println("Generate tree first you dum dum");
                Node searchResult = studentRecordTree.find(studentRecordTree.root, "64939");
                if(searchResult == null)
                    System.out.println("record not found");
                else
                    System.out.println(searchResult.toString());
            }
            else if(e.getSource() == browse){
                listModel.removeAllElements();
            }
            else if(e.getSource() == createTree){

                fileDialog = new JDialog();


                JPanel controls = new JPanel();
                filenameOkButton= new JButton("OK");
                filenameOkButton.addActionListener(new ButtonListener());
                controls.add(filenameOkButton);
                filenameCancelButton= new JButton("Cancel");
                filenameCancelButton.addActionListener(new ButtonListener());
                controls.add(filenameCancelButton);


                JPanel textArea = new JPanel();
                filenameField = new JTextField(20);
                textArea.add(filenameField);

                fileDialog.setBounds(220, 150, 400, 200);

                fileDialog.add(textArea, BorderLayout.NORTH);
                fileDialog.add(controls, BorderLayout.SOUTH);
                fileDialog.setVisible(true);
            }
            else if (e.getSource() == filenameOkButton) {
                String filename = filenameField.getText();
                studentRecordTree = readFile(filename);
                fileDialog.setVisible(false);
            }
            else if (e.getSource() == filenameCancelButton) {
                fileDialog.setVisible(false);
            }

        }
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
}