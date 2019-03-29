import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


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
    private JButton getUserInput;
    private JButton createTree;
    private JButton browse;
    private JButton find;
    private JList<String> listArea;
    private DefaultListModel<String> listModel;
    private JScrollPane listScrollPane;
    private Border panelEdge = BorderFactory.createEtchedBorder();

    // Constructor
    public StudentRecordList() {
        this.setTitle("Main Window");
        this.setBounds(50, 50, 500, 400);

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
        //userInputTextField = new JTextField(10);
        //lowerPanel.add(userInputTextField);
        getUserInput = new JButton("Insert");
        getUserInput.addActionListener(new ButtonListener());
        lowerPanel.add(getUserInput);
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

    // ******************Listeners****************

    public class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == getUserInput) {
                String text = userInputTextField.getText();
                if(text.length()>0)
                    listModel.addElement(text);
            }
            else if(e.getSource() == createTree){
                File file = new File("my_input.txt");
                try {
                    Scanner inputFile = new Scanner(file);
                    while(inputFile.hasNextLine()){
                        listModel.addElement(inputFile.nextLine());
                    }
                } catch (FileNotFoundException e1) {
                    System.out.println(e1.getMessage());
                }
            }
            else if(e.getSource() == find){
                listModel.removeAllElements();

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