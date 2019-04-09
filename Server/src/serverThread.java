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
    private IO io;

    public serverThread(Socket aSocket){
        io = new IO(aSocket);
        System.out.println("New connection started");
    }

    /**
     * Action-method started with each thread. Begins by making a new instance of FrontEnd
     */
    @Override
    public void run() {
        FrontEnd app = new FrontEnd();
        app.menu();
        try {
            io.getSocketIn().close();
            io.getSocketOut().close();
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
         * present the user with the interactive options. Reads from console before sending
         * to server.
         */
        public void menu() {
            while (true) {
                Item tempItem;
                Supplier tempSupplier;
                Order tempOrder;

                try {
                    readFromSocket = io.getSocketIn().readLine();
                    menuChoice = Integer.parseInt(readFromSocket);
                } catch (IOException e) {
                    System.out.println("couldn't read from socket");
                    e.printStackTrace();
                }

                switch (menuChoice) {
                    case 1: //list all tools
                        io.getSocketOut().println(theShop.getTheInventory().getItemList().size());
                        io.getSocketOut().print(theShop.listAllItems());
                        break;

                    //////////////////////////////////////////////////////////////

                    case 2: //search for tool (by name and by id (2,3) collapsed into one)
                        try {
                            readFromSocket = io.getSocketIn().readLine();
                        } catch (IOException e) {
                            System.out.println("error reading from socket");
                            e.printStackTrace();
                        }
                        //try first assuming input is name
                        tempItem = theShop.getItem(readFromSocket);
                        if(tempItem != null){
                            sendObjectOverSocket(tempItem);
                        }

                        else if((tempItem = theShop.getItem(Integer.parseInt(readFromSocket))) != null){
                            sendObjectOverSocket(tempItem);
                        }

                        else
                            sendObjectOverSocket(null);
                        break;

                    //////////////////////////////////////////////////////////////

                    case 4: // check item quantity
                        try {
                            readFromSocket = io.getSocketIn().readLine();
                        } catch (IOException e) {
                            System.out.println("error reading from socket");
                            e.printStackTrace();
                        }

                        tempItem = theShop.getItem(readFromSocket); // try to find with name search
                        if(tempItem != null){
                            int q = tempItem.getItemQuantity();
                            io.getSocketOut().println("item " + tempItem.getItemName() + " has quantity " + q);
                        }
                        else if((tempItem = theShop.getItem(Integer.parseInt(readFromSocket))) != null){
                            int q = tempItem.getItemQuantity();
                            io.getSocketOut().println("item " + tempItem.getItemName() + " has quantity " + q);
                        }
                        else
                            io.getSocketOut().println("item not found");
                        break;

                    //////////////////////////////////////////////////////////////

                    case 5: //decrease item quantity
                        io.getSocketOut().println("enter name or ID of item");
                        try {
                            readFromSocket = io.getSocketIn().readLine();
                        } catch (IOException e) {
                            System.out.println("error reading from socket");
                            e.printStackTrace();
                        }

                        tempItem = theShop.getItem(readFromSocket); // try to find with name search
                        if(tempItem != null){
                            int q = tempItem.getItemQuantity();
                            if(q > 0){
                                tempItem.setItemQuantity(q-1);
                                io.getSocketOut().println("new item quantity of " + tempItem.getItemName()+ " is " + (q-1));
                            }
                            else
                                io.getSocketOut().println("item quantity already 0");
                        }
                        else if((tempItem = theShop.getItem(Integer.parseInt(readFromSocket))) != null){
                            int q = tempItem.getItemQuantity();
                            if(q > 0){
                                tempItem.setItemQuantity(q - 1);
                                io.getSocketOut().println("new item quantity of " + tempItem.getItemName()+ " is " +(q - 1));
                            }
                            else
                                io.getSocketOut().println("item quantity already 0");
                            }
                        else
                            io.getSocketOut().println("item not found");
                        break;

                    //////////////////////////////////////////////////////////////

                    case 6: //grab today's order
                        sendObjectOverSocket(theShop.getTheInventory().getMyOrder());
                        break;

                    //////////////////////////////////////////////////////////////

                    case 7: //exit
                        io.getSocketOut().println("\nGood Bye!");
                        return;

                    //////////////////////////////////////////////////////////////

                    default: //invalid input
                        io.getSocketOut().println("\nSelection out of range. Try again.");
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
                io.getObjectOutputStream().writeObject(toBeSent);
            } catch (IOException e) {
                System.out.println("cannot write item to output stream");
                e.printStackTrace();
            }
        }
    }
}
