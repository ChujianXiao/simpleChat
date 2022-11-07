// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */

public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 
  
  private String loginID;

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String loginID, String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    this.loginID = loginID;
    openConnection();
    try{
    	sendToServer("#login "+ loginID);
    }catch(Exception e) {
    	System.out.println("An error has occured when sending login ID to server.");
    }
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
	  if(message.trim().charAt(0) == '#') {
		  String[] splitMsg = message.split(" ");
		  switch(splitMsg[0]) {
		  case "#quit":
			  quit();
			  System.exit(0);
			  break;
		  case "#logoff":
			  try
			  {
				 closeConnection();
			  }
			  catch(IOException e) {}
			  break;
		  case "#sethost":
			  if(isConnected() == true) {
				  setHost(splitMsg[2]);
			  }else {
				  clientUI.display("Unable to set host, not disconnected yet.");
			  }
			  break;
		  case "#setport":
			  if(isConnected() == true) {
				  setPort(Integer.parseInt(splitMsg[2]));
			  }else {
				  clientUI.display("Unable to set port, not disconnected yet.");
			  }
			  break;
		  case "#login":
			  if(isConnected() == true) {
				  try {
					  openConnection();
				  }catch(IOException exception) {
					  clientUI.display("Error when trying to connect to server.");
				  }
			  }else {
				  clientUI.display("You are already logged in.");
			  }
			  break;
		  case "#gethost":
			  clientUI.display(this.getHost());
			  break;
		  case "#getport":
			  clientUI.display(String.valueOf(this.getPort()));
			  break;
		  }
	  }else{
		  try
		    {
		      sendToServer(message);
		    }
		    catch(IOException e)
		    {
		      clientUI.display
		        ("Could not send message to server.  Terminating client.");
		      quit();
		    }
	  }
  }
  
   /**
   * This method makes the client quit and prints a message when the connection has ended.
   */
  @Override
  public void connectionClosed() {
	  clientUI.display("Closing Connection.");
  }
  
  /**
   * This method is called every time an exception is thrown when the client is waiting for messages
   * from the server.
   */
  public void connectionException(Exception exception) {
	  clientUI.display("An Error has occured when client is waiting for messages.");
	  quit();
  }
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class
