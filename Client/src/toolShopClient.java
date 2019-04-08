import java.awt.desktop.SystemEventListener;
import java.io.*;
import java.net.Socket;

/**
 * @author Mohammed Moshirpour
 * @author Bernard Kleiner
 */

public class toolShopClient {
	private MainInterface mainInterface;
	public IO io;

    /**
     * Creates a socket object, attempting to connect to a server running on the IP in serverName and
     * on port given in portNumber. Retries failed connections indefinitely
     * @param serverName IP of server to which to connect
     * @param portNumber port of the server to connect to
     */
	public toolShopClient(String serverName, int portNumber) {
		//open connection to server
		io = new IO("localhost", 9090);
		mainInterface = new MainInterface("Inventory", io);
		}

    /**
     * Orchestrator class. Creates a toolShopClient object on port 9090, which attempts to connect to a server
     * running on the same machine on port 9090. Begins the communication protocol.
     * @param args not used
     * @throws IOException
     */
	public static void main(String[] args) throws IOException  {
		toolShopClient aClient = new toolShopClient("localhost", 9090);
	}

	/**
	 * begins sending and receiving data from the date server. The server awaits a response, so the user
	 * is prompted for input. DATE causes the server to return the date, TIME the current time, and
	 * QUIT terminates the connection and stops the program.
	 */

	public < E > E receiveObjectOverSocket(){
		try {
			return (E) io.getObjectInputStream().readObject();
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