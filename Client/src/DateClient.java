import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DateClient {
	private PrintWriter socketOut;
	private Socket dataSocket;
	private BufferedReader stdIn;
	private BufferedReader socketIn;

    /**
     * Creates a socket object, attempting to connect to a server running on the IP in serverName and
     * on port given in portNumber. Retries failed connections indefinitely
     * @param serverName IP of server to which to connect
     * @param portNumber port of the server to connect to
     */
	public DateClient(String serverName, int portNumber) {
		while(true){
			try {
				dataSocket = new Socket(serverName, portNumber);
				stdIn = new BufferedReader(new InputStreamReader(System.in));
				socketIn = new BufferedReader(new InputStreamReader(
						dataSocket.getInputStream()));
				socketOut = new PrintWriter((dataSocket.getOutputStream()), true);
				break;
			} catch (IOException e) {
				System.out.println("ERROR connecting to server. Trying again in 1s");
				continue;
			}
		}
	}

    /**
     * begins sending and receiving data from the date server. The server awaits a response, so the user
     * is prompted for input. DATE causes the server to return the date, TIME the current time, and
     * QUIT terminates the connection and stops the program.
     */
	public void communicate()  {

		String line;
		String response;
		boolean running = true;
		while (running) {
			try {
				System.out.println("enter one of: DATE | TIME | QUIT");
				line = stdIn.readLine();
				if(line.compareTo("QUIT") == 0){
					System.out.println(line);
					socketOut.println(line);
					running = false;
					break;
				}
				System.out.println(line);
				socketOut.println(line);
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

    /**
     * Orchestrator class. Creates a DateClient object on port 9090, which attempts to connect to a server
     * running on the same machine on port 9090. Begins the communication protocol.
     * @param args not used
     * @throws IOException
     */
	public static void main(String[] args) throws IOException  {
		DateClient aClient = new DateClient("localhost", 9090);
		aClient.communicate();
	}
}