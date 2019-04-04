import java.io.*;
import java.net.Socket;

/**
 * Orignal name of file: FrontEnd.java
 * @author Mohammed Moshirpour
 * @author Bernard Kleiner
 */

public class serverThread implements Runnable{
    private Socket aSocket;
    private BufferedReader socketInput;
    private PrintWriter socketOutput;
    private ObjectOutputStream objectOutputStream;

    public serverThread(Socket aSocket){
        this.aSocket = aSocket;
        System.out.println("New connection started");
        try {
            socketInput = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
            socketOutput = new PrintWriter(aSocket.getOutputStream(), true);
            objectOutputStream = new ObjectOutputStream(aSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Unable to create input or output streams");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        FrontEnd app = new FrontEnd();
        app.menu();
        try {
            socketInput.close();
            socketOutput.close();
            System.out.println("Connection closed");
        } catch (IOException e) {
            System.out.println("Unable to close sockets");
            e.printStackTrace();
        }
    }

    public class FrontEnd {
//        private Inventory theInventory;
        private Shop theShop;
        private String readFromSocket;
        private int menuChoice;

        FrontEnd() {
            theShop = new Shop();
            readFromSocket = null;
            menuChoice = -1;
        }

        private void printMenuChoices() {
            socketOutput.println("Please choose from one of the following options: ");
            socketOutput.println("1. List all tools in the inventory.");
            socketOutput.println("2. Search for tool by tool name.");
            socketOutput.println("3. Search for tool by tool id.");
            socketOutput.println("4. Check item quantity.");
            socketOutput.println("5. Decrease item quantity.");
            socketOutput.println("6. Grab today's order");
            socketOutput.println("7. Quit.");
            socketOutput.println("");
            socketOutput.println("Please enter your selection: ");
        }

        public void menu() {

            while (true) {

                printMenuChoices();

                try {
                    readFromSocket = socketInput.readLine();
                    System.out.println(readFromSocket);
                    menuChoice = Integer.parseInt(readFromSocket);
                    System.out.println(menuChoice);
                } catch (IOException e) {
                    System.out.println("couldn't read from socket");
                    e.printStackTrace();
                }

                switch (menuChoice) {

                    case 1: //list all tools
                        theShop.listAllItems();
                        break;
                    case 2: //search for tool by tool name
                        socketOutput.println("Enter the name of the item to search for");
                        Item toBeSent = null;
                        try {
                            toBeSent = theShop.getItem(socketInput.readLine());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        sendObjectOverSocket(toBeSent);
                        break;
                    case 3: // search for tool by tool id
                        socketOutput.println("search by id placeholder");
                        break;
                    case 4: // check item quantity
                        socketOutput.println("check item quantity placeholder");
                        break;
                    case 5: //decrease item quantity
                        socketOutput.println("decrease item quantity placeholder");
                        break;
                    case 6: //grab today's order
                        socketOutput.println("print order placeholder");
                        break;
                    case 7: //exit
                        socketOutput.println("\nGood Bye!");
                        return;
                    default: //invalid input
                        socketOutput.println("\nInvalid selection Please try again!");
                        break;
                }
            }
        }

        private < E > void sendObjectOverSocket(E  toBeSent){
            try {
                objectOutputStream.writeObject(toBeSent);
            } catch (IOException e) {
                System.out.println("cannot write item to output stream");
                e.printStackTrace();
            }
        }
    }
}
