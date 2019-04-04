import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

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

        private ArrayList<Supplier> suppliers;
        private Inventory theInventory;
        private Shop theShop;
        private String readFromSocket;
        private int menuChoice;

        FrontEnd() {
            suppliers = new ArrayList<Supplier>();
            readSuppliers();
            theInventory = new Inventory(readItems());
            theShop = new Shop(theInventory, suppliers);
            readFromSocket = null;
            menuChoice = -1;
        }

        /**
         * populate the list of items in stock from the text file "items.txt"
         * @return
         */
        private ArrayList<Item> readItems() {

            ArrayList<Item> items = new ArrayList<Item>();

            try {
                FileReader fr = new FileReader("items.txt");
                BufferedReader br = new BufferedReader(fr);

                String line = "";
                while ((line = br.readLine()) != null) {
                    String[] temp = line.split(";");
                    int supplierId = Integer.parseInt(temp[4]);
                    Supplier theSupplier = null;

                        for (Supplier i: suppliers) {
                            if (i.getSupId() == supplierId)
                                theSupplier = i;
                        }
                    if (theSupplier != null) {
                        Item myItem = new Item(Integer.parseInt(temp[0]), temp[1], Integer.parseInt(temp[2]),
                                Double.parseDouble(temp[3]), theSupplier);
                        items.add(myItem);
                        theSupplier.getItemList().add(myItem);
                    }
                }
            } catch (Exception e) {
                System.out.println("error parsing items.txt");
                System.out.println(e.getMessage());
            }
            return items;
        }

        private void readSuppliers() {
            try {
                FileReader fr = new FileReader("suppliers.txt");
                BufferedReader br = new BufferedReader(fr);

                String line = "";
                while ((line = br.readLine()) != null) {
                    String[] temp = line.split(";");
                    suppliers.add(new Supplier(Integer.parseInt(temp[0]), temp[1], temp[2], temp[3]));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
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
                            toBeSent = theInventory.searchForItem(socketInput.readLine());
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
