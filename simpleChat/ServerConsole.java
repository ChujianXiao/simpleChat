import java.io.IOException;
import java.util.Scanner;

import client.ChatClient;
import common.ChatIF;

public class ServerConsole implements ChatIF{
	//Class variables *************************************************

	/**
	 * The default port to connect on.
	 */
	final public static int DEFAULT_PORT = 5555;

	//Instance variables **********************************************

	/**
	 * The instance of the server that created this ServerConsole.
	 */
	EchoServer server;


	/**
	 * Scanner to read from the console
	 */
	Scanner fromConsole; 

	//Constructors ****************************************************

	/**
	 * Constructs an instance of the ServerConsole UI.
	 *
	 * @param port The server port.
	 */
	public ServerConsole(int port) 
	{
		server = new EchoServer(port);

		// Create scanner object to read from console
		fromConsole = new Scanner(System.in); 
	}

	//Instance methods ************************************************
	/**
	 * This method waits for input from the console.  Once it is 
	 * received, it sends it to the client's message handler.
	 */
	public void accept() 
	{
		try
		{

			String message;

			while (true) 
			{
				message = fromConsole.nextLine();
				server.handleMessageFromServerUI(message);
			}
		} 
		catch (Exception ex) 
		{
			System.out.println
			("Unexpected error while reading from console!");
		}
	}

	public void display(String message) {
		System.out.println("> " + message);
	}

	//Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the Server UI.
	 *
	 * @param args[0] The  port of the server.
	 */
	public static void main(String[] args) 
	{
		int port;
		try
		{
			port = Integer.parseInt(args[0]);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			port = DEFAULT_PORT;
		}

		ServerConsole server = new ServerConsole(port);
		server.accept();  //Wait for console data
	}
}