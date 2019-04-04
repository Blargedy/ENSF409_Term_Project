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
			try {
				// print menu options
				for(int i = 0; i < 10; i++){
					response = socketIn.readLine();
					System.out.println(response);
				}

				//send user response to server
				line = stdIn.readLine();
				socketOut.println(line);
				menuChoice = Integer.parseInt(line);

				switch (menuChoice){
					case 1: //list all tools
						break;
					case 2: //search for tool by tool name
						System.out.println(socketIn.readLine()); //request to enter item name
						socketOut.println(stdIn.readLine());	//send user input
						System.out.println(receiveObjectOverSocket().toString()); //print received object
						break;
					case 3: //search for tool by tool id
						break;
					case 4: //check item quantity
						break;
					case 5: //decrease item quantity
						break;
					case 6: //grab today's order
						break;
					case 7: //exit
						break;
					default: //invalid input
						break;
				}

				response = socketIn.readLine();
				System.out.println(response);

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

//	public Item receiveItemOverSocket(){
//		try {
//			return (Item) objectInputStream.readObject();
//		} catch (IOException e) {
//			System.out.println("error with IO during item read");
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			System.out.println("error finding class during Item read");
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public Order receiveOrderOverSocket(){
//		try {
//			return (Order) objectInputStream.readObject();
//		} catch (IOException e) {
//			System.out.println("error with IO during Order read");
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			System.out.println("error finding class during Order read");
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public Supplier receiveSupplierOverSocket(){
//		try {
//			return (Supplier) objectInputStream.readObject();
//		} catch (IOException e) {
//			System.out.println("error with IO during Supplier read");
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			System.out.println("error finding class during Supplier read");
//			e.printStackTrace();
//		}
//		return null;
//	}
}