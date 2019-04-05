import java.awt.desktop.SystemEventListener;
import java.io.*;
import java.net.Socket;

/**
 * @author Mohammed Moshirpour
 * @author Bernard Kleiner
 */

public class toolShopClient {
	private PrintWriter socketOut;
	private Socket dataSocket;
	private BufferedReader stdIn;
	private BufferedReader socketIn;
	private ObjectInputStream objectInputStream;
	private MainInterface mainInterface;

    /**
     * Creates a socket object, attempting to connect to a server running on the IP in serverName and
     * on port given in portNumber. Retries failed connections indefinitely
     * @param serverName IP of server to which to connect
     * @param portNumber port of the server to connect to
     */
	public toolShopClient(String serverName, int portNumber) {
		while(true){
			try {
				dataSocket = new Socket(serverName, portNumber);
				stdIn = new BufferedReader(new InputStreamReader(System.in));
				socketIn = new BufferedReader(new InputStreamReader(
						dataSocket.getInputStream()));
				socketOut = new PrintWriter((dataSocket.getOutputStream()), true);
				objectInputStream = new ObjectInputStream(dataSocket.getInputStream());
				mainInterface = new MainInterface("Inventory", socketIn, socketOut, objectInputStream);
				break;
			} catch (IOException e) {
				System.out.println("ERROR connecting to server. Trying again in 1s");
				continue;
			}
		}
	}

    /**
     * Orchestrator class. Creates a toolShopClient object on port 9090, which attempts to connect to a server
     * running on the same machine on port 9090. Begins the communication protocol.
     * @param args not used
     * @throws IOException
     */
	public static void main(String[] args) throws IOException  {
		toolShopClient aClient = new toolShopClient("localhost", 9090);
		aClient.communicate();
	}

	/**
	 * begins sending and receiving data from the date server. The server awaits a response, so the user
	 * is prompted for input. DATE causes the server to return the date, TIME the current time, and
	 * QUIT terminates the connection and stops the program.
	 */
	public void communicate()  {
		String line;
		String response;
		int menuChoice = -1;
		boolean running = true;

		while (running) {
			Item receivedItem;
			Supplier receivedSupplier;
			Order receivedOrder;
			try {
				// print menu options
				for(int i = 0; i < 10; i++){
					response = socketIn.readLine();
					System.out.println(response);
				}

				//send user response to server
				while(true){
					line = stdIn.readLine();
					try{
						menuChoice = Integer.parseInt(line);
						break;
					}catch (NumberFormatException e){
						System.out.println("Bad number format. Try again:");
						continue;
					}
				}

				socketOut.println(line);

				switch (menuChoice){
					case 1: //list all tools
						int numItems = Integer.parseInt(socketIn.readLine());
						for(int i = 0; i < numItems; i++)
							System.out.println(socketIn.readLine());
						break;

					//////////////////////////////////////////////////////////////

					case 2: //search for tool by tool name
						System.out.println(socketIn.readLine()); //request to enter item name
						socketOut.println(stdIn.readLine());	//send user input
						receivedItem = receiveObjectOverSocket();
						if(receivedItem == null){
							System.out.println("item not found");
						}
						else{
							System.out.println(receivedItem); //print received object
						}
						break;

					//////////////////////////////////////////////////////////////

					case 3: //search for tool by tool id
						System.out.println(socketIn.readLine()); //request to enter item name
						socketOut.println(stdIn.readLine());	//send user input
						receivedItem = receiveObjectOverSocket();
						if(receivedItem == null){
							System.out.println("item not found");
						}
						else{
							System.out.println(receivedItem); //print received object
						}
						break;

					//////////////////////////////////////////////////////////////

					case 4: //check item quantity
						System.out.println(socketIn.readLine()); //"enter name or ID of item"
						socketOut.println(stdIn.readLine()); //send user response
						System.out.println(socketIn.readLine()); //print server response
						break;

					//////////////////////////////////////////////////////////////

					case 5: //decrease item quantity
						System.out.println(socketIn.readLine()); //"enter name or ID of item"
						socketOut.println(stdIn.readLine()); //send user response
						System.out.println(socketIn.readLine()); //print server response
						break;

					//////////////////////////////////////////////////////////////

					case 6: //print today's order
						receivedOrder = receiveObjectOverSocket();
						if(receivedOrder == null)
							System.out.println("order not found");
						else
							System.out.println(receivedOrder);
						break;

					//////////////////////////////////////////////////////////////

					case 7: //exit
						System.out.println("goodbye");
						running = false;
						break;

					//////////////////////////////////////////////////////////////

					default: //invalid input
						break;
				}

			} catch (IOException e) {
				System.out.println("Sending error: " + e.getMessage());
			}
		}
		try {
			stdIn.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			System.out.println("Closing error: " + e.getMessage());
		}

	}

	public < E > E receiveObjectOverSocket(){
		try {
			return (E) objectInputStream.readObject();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("error with IO during object read");
		} catch (ClassNotFoundException e) {
			System.out.println("error finding class during object read");
			e.printStackTrace();
		}
		return null;
	}
}