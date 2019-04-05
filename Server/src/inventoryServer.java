import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Bernard Kleiner
 */

public class inventoryServer {
	private ServerSocket serverSocket;
	private ExecutorService threadPool;

	/**
	 * Construct a Server with Port 9090
	 */
	public inventoryServer() {
		try {
			serverSocket = new ServerSocket(9090);
			threadPool = Executors.newCachedThreadPool();
			System.out.println("Server Running");
		} catch (IOException e) {
		}
	}

	/**
	 * start the execution of access to the inventory database management software in a thread
	 */
	public void startServer(){
		while(true){
			try {
				threadPool.execute(new serverThread(serverSocket.accept()));

			} catch (IOException e) {
				threadPool.shutdown();
				e.printStackTrace();
			}
		}
	}

	/**
	 * create server object, then start the inventory server
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) {
		inventoryServer is = new inventoryServer();
		is.startServer();
	}
}