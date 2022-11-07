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
		server = new EchoServer(port,this);
		try 
	    {
	      server.listen(); //Start listening for connections
	    } 
	    catch (Exception ex) 
	    {
	      System.out.println("ERROR - Could not listen for clients!");
	      ex.printStackTrace();
	    }

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
	   * This method is responsible for the creation of 
	   * the server UI
	   *
	   * @param args[0] The port number to listen on.  Defaults to 5555 
	   *          if no argument is entered.
	   */
	  public static void main(String[] args) 
	  {
	    int port = 0; //Port to listen on

	    try
	    {
	      port = Integer.parseInt(args[0]); //Get port from command line
	    }
	    catch(Throwable t)
	    {
	      port = DEFAULT_PORT; //Set port to 5555
	    }
	    ServerConsole serverCons = new ServerConsole(port);
	    serverCons.accept();  //Wait for console data
	  }
}