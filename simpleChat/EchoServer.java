// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 


import java.io.IOException;

import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  ServerConsole serverConsole;
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
    this.serverConsole = serverConsole;
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
    	System.out.println("Message received: " + msg + " from " + client);
		this.sendToAllClients(msg);
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when a client has connected.
   */
  protected void clientConnected(ConnectionToClient client) 
  {
	  System.out.println("Client " + client.toString() + " has connected.");
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when a client has disconnected.
   */
  synchronized protected void clientDisconnected(ConnectionToClient client) {
	  System.out.println("Client " + client.toString() + " has disconnected.");
  }
  
  public void handleMessageFromServerUI(String message)
  {
	  if(message.trim().charAt(0) == '#') {
		  String[] splitMsg = message.split(" ");
		  switch(splitMsg[0]) {
		  case "#quit":
			  try {
				  close();
			  }catch(Exception e) {
				  e.printStackTrace();
			  }
			  System.exit(0);
			  break;
		  case "#stop":
			  stopListening();
			  break;
		  case "#close":
			  try {
				  close();
			  }catch(Exception e) {
				  e.printStackTrace();
			  }
			  break;
		  case "#setport":
			  if(isListening() == false && getNumberOfClients() == 0) {
				  setPort(Integer.parseInt(splitMsg[2]));
			  }else {
				  serverConsole.display("Unable to set port, not closed yet.");
			  }
			  break;
		  case "#start":
			  if(isListening() == false && getNumberOfClients() == 0) {
				  run();
			  }else {
				  serverConsole.display("Unable to start, not closed.");
			  }
		  case "#getport":
			  serverConsole.display(String.valueOf(this.getPort()));
			  break;
		  }
	  }else{

	  }
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
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
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
      ex.printStackTrace();
    }
  }
}
//End of EchoServer class
