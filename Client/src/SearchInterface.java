import javax.swing.*;

/**
 * @author Bernard Kleiner
 */

public class SearchInterface extends JFrame {
    private JButton byId, byName, Cancel;
    private IO io;
    private JTextField userInputTextField;

    public SearchInterface(String s, IO receivedIo){
        super(s);
        this.io = receivedIo;
    }

    private JPanel createResultPanel(){
        JPanel resultPanel = new JPanel();
        return resultPanel;
    }

}
