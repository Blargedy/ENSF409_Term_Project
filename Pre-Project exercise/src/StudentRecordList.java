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
    private JButton toastOkButton;
    private JButton insertInsertButton;
    private JButton insertReturnButton;
    private JButton findOkButton;
    private JButton findCancelButton;
    private JTextField filenameField;
    private JTextField studentIdField;
    private JTextField facultyField;
    private JTextField studentMajorField;
    private JTextField studentYearField;
    private JTextField searchStudentIdField;
    private JDialog fileDialog;
    private JDialog toastDialog;
    private JDialog insertDialog;
    private JDialog searchDialog;
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

        upperPanel.add(label);
        return upperPanel;
    }

    /**
     * Method to create the center panel that will hold the JList. This method
     * creates the panel and returns it to be added to the container.
     */
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(panelEdge);
        listModel = new DefaultListModel<String>();
        listArea = new JList<String>(listModel);
        String width = "1234567890123456789012345678901234567890";
        listArea.setPrototypeCellValue(width);
        listArea.setFont(new Font("Courier New", Font.BOLD, 12));
        listArea.setVisibleRowCount(15);
        listArea.addListSelectionListener(new ListListener());
        listScrollPane = new JScrollPane(listArea);
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
                displayToast("IO exception");
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            displayToast("File not found exception");
            e.printStackTrace();
        }
        return BST;
    }

    // ******************Listeners****************

    public class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == insert) {
                insertDialog = new JDialog();

                JPanel controls = new JPanel();
                insertInsertButton = new JButton("Insert");
                insertInsertButton.addActionListener(new ButtonListener());
                controls.add(insertInsertButton);
                insertReturnButton = new JButton("Return to Main Window");
                insertReturnButton.addActionListener(new ButtonListener());
                controls.add(insertReturnButton);

                JPanel textArea = new JPanel();
                studentIdField = new JTextField(20);
                textArea.add(studentIdField);
                facultyField = new JTextField(20);
                textArea.add(facultyField);
                studentMajorField = new JTextField(20);
                textArea.add(studentMajorField);
                studentYearField = new JTextField(20);
                textArea.add(studentYearField);

                insertDialog.setBounds(220, 150, 400, 200);

                insertDialog.add(textArea, BorderLayout.NORTH);
                insertDialog.add(controls, BorderLayout.SOUTH);
                insertDialog.setTitle("Insert a New Node");
                insertDialog.setVisible(true);

                String studentId = studentIdField.getText();
                String faculty = facultyField.getText();
                String studentMajor = studentMajorField.getText();
                String studentYear = studentYearField.getText();
                studentRecordTree.insert(studentId, faculty, studentMajor, studentYear);
            }
            else if(e.getSource() == find){
                if(studentRecordTree == null) {
                    displayToast("Records are empty. Import data first");
                }
                else{
                    displayToast("this worked  5 minutes ago. java pls.");
                    searchDialog = new JDialog();
                    JPanel controls = new JPanel();
                    findCancelButton = new JButton("Cancel");
                    findCancelButton.addActionListener(new ButtonListener());
                    controls.add(findCancelButton);
                    findOkButton = new JButton("OK");
                    findOkButton.addActionListener(new ButtonListener());
                    controls.add(findOkButton);

                    JPanel searchTextArea = new JPanel();
                    searchStudentIdField = new JTextField(20);
                    searchTextArea.add(searchStudentIdField);
                    searchDialog.setBounds(220, 150, 400, 200);
                    searchDialog.setVisible(true);
                }
            }
            else if(e.getSource() == browse){

            }
            else if(e.getSource() == createTree){
                fileDialog = new JDialog();
                JTextArea createTextPrompt = new JTextArea("Enter the file name:");

                JPanel controls = new JPanel();
                filenameOkButton= new JButton("OK");
                filenameOkButton.addActionListener(new ButtonListener());
                controls.add(createTextPrompt);
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
                fileDialog.setTitle("Input");
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
            else if (e.getSource() == toastOkButton) {
                toastDialog.setVisible(false);
            }
            else if (e.getSource() == insertInsertButton) {
                insertDialog.setVisible(false);
            }
            else if (e.getSource() == insertReturnButton) {
                insertDialog.setVisible(false);
            }
            else if (e.getSource() == findCancelButton) {
                searchDialog.setVisible(false);
            }
            else if (e.getSource() == findOkButton) {
                String studentId = searchStudentIdField.getText();
                displayToast(studentRecordTree.find(studentRecordTree.root, studentId).toString());
                searchDialog.setVisible(false);
            }
        }
    }

    public void displayToast(String message){
        JTextArea displayToastTextArea = new JTextArea(message);
        toastDialog = new JDialog();
        JPanel controls = new JPanel();
        toastOkButton= new JButton("OK");
        toastOkButton.addActionListener(new ButtonListener());
        controls.add(toastOkButton);
        toastDialog.setBounds(220, 150, 400, 200);
        toastDialog.add(displayToastTextArea);
        toastDialog.add(controls, BorderLayout.SOUTH);
        toastDialog.setVisible(true);
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