import java.io.*;
import java.net.Socket;

/**
 * Original name of file: FrontEnd.java
 * @author Mohammed Moshirpour
 * @author Bernard Kleiner
 */

/**
 * Main thread running on the server side, started with each connection. Creates a FrontEnd instance
 * and continues handling client requests
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

    /**
     * Action-method started with each thread. Begins by making a new instance of FrontEnd
     */
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

    /**
     * High level class in charge of creating the shop object. Receives data from the client
     * and sends requested data in return
     */
    public class FrontEnd {
        private Shop theShop;
        private String readFromSocket;
        private int menuChoice;

        FrontEnd() {
            theShop = new Shop();
            readFromSocket = null;
            menuChoice = -1;
        }

        /**
         * sends the text of the menu for the client to display
         */
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

        /**
         * present the user with the interactive options. Reads from console before sending
         * to server.
         */
        public void menu() {
            while (true) {
                Item tempItem;
                Supplier tempSupplier;
                Order tempOrder;

                printMenuChoices();

                try {
                    readFromSocket = socketInput.readLine();
                    menuChoice = Integer.parseInt(readFromSocket);
                } catch (IOException e) {
                    System.out.println("couldn't read from socket");
                    e.printStackTrace();
                }

                switch (menuChoice) {
                    case 1: //list all tools
                        socketOutput.println(theShop.getTheInventory().getItemList().size());
                        socketOutput.print(theShop.listAllItems());
                        break;

                    //////////////////////////////////////////////////////////////

                    case 2: //search for tool by tool name
                        socketOutput.println("Enter the name of the item to search for");
                        tempItem = null;
                        try {
                            tempItem = theShop.getItem(socketInput.readLine());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        sendObjectOverSocket(tempItem);
                        break;

                    //////////////////////////////////////////////////////////////

                    case 3: // search for tool by tool id
                        socketOutput.println("Enter the ID of the item to search for");
                        tempItem = null;
                        try {
                            tempItem = theShop.getItem(socketInput.readLine());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        sendObjectOverSocket(tempItem);
                        break;

                    //////////////////////////////////////////////////////////////

                    case 4: // check item quantity
                        socketOutput.println("enter name or ID of item");
                        try {
                            readFromSocket = socketInput.readLine();
                        } catch (IOException e) {
                            System.out.println("error reading from socket");
                            e.printStackTrace();
                        }

                        tempItem = theShop.getItem(readFromSocket); // try to find with name search
                        if(tempItem != null){
                            int q = tempItem.getItemQuantity();
                            socketOutput.println("item " + tempItem.getItemName() + " has quantity " + q);
                        }
                        else if((tempItem = theShop.getItem(Integer.parseInt(readFromSocket))) != null){
                            int q = tempItem.getItemQuantity();
                            socketOutput.println("item " + tempItem.getItemName() + " has quantity " + q);
                        }
                        else
                            socketOutput.println("item not found");
                        break;

                    //////////////////////////////////////////////////////////////

                    case 5: //decrease item quantity
                        socketOutput.println("enter name or ID of item");
                        try {
                            readFromSocket = socketInput.readLine();
                        } catch (IOException e) {
                            System.out.println("error reading from socket");
                            e.printStackTrace();
                        }

                        tempItem = theShop.getItem(readFromSocket); // try to find with name search
                        if(tempItem != null){
                            int q = tempItem.getItemQuantity();
                            if(q > 0){
                                tempItem.setItemQuantity(q-1);
                                socketOutput.println("new item quantity of " + tempItem.getItemName()+ " is " + (q-1));
                            }
                            else
                                socketOutput.println("item quantity already 0");
                        }
                        else if((tempItem = theShop.getItem(Integer.parseInt(readFromSocket))) != null){
                            int q = tempItem.getItemQuantity();
                            if(q > 0){
                                tempItem.setItemQuantity(q - 1);
                                socketOutput.println("new item quantity of " + tempItem.getItemName()+ " is " +(q - 1));
                            }
                            else
                                socketOutput.println("item quantity already 0");
                            }
                        else
                            socketOutput.println("item not found");
                        break;

                    //////////////////////////////////////////////////////////////

                    case 6: //grab today's order
                        sendObjectOverSocket(theShop.getTheInventory().getMyOrder());
                        break;

                    //////////////////////////////////////////////////////////////

                    case 7: //exit
                        socketOutput.println("\nGood Bye!");
                        return;

                    //////////////////////////////////////////////////////////////

                    default: //invalid input
                        socketOutput.println("\nSelection out of range. Try again.");
                        break;
                }
            }
        }

        /**
         * attempts to send an object to the client.
         * @param toBeSent object in question
         * @param <E> type of object
         */
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
