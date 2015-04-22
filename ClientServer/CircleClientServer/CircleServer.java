/*
	File: CircleServer.java
	Version: 1.1
	Date: October 2009
	Author: Lynsay A. Shepherd
	
	Description:  Java program demonstrating client/server architecture.  The server calculates the area and sphere volume from a circle radius supplied by the client.  Written as part of an MSc class.
*/

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class CircleServer {
    public static void main(String[] args){
    
    	//Program header
        programInformation();
        
		try
		{
		  //create a server socket
		  ServerSocket serverSocket = new ServerSocket(0000);

		  //listen for connections to server
		  Socket connectToClient = serverSocket.accept();

		  //create an input stream to get data from client
		  DataInputStream isFromClient = new DataInputStream(connectToClient.getInputStream());

		  //create an output stream to send data to client
		  DataOutputStream osToClient = new DataOutputStream(connectToClient.getOutputStream());

		  while (true)
		  {
			//get radius, do calculations
			double radius = isFromClient.readDouble();
			double area = radius * radius * Math.PI;
			double spherevol=( 4.0 / 3.0 ) * Math.PI * Math.pow( radius, 3 );

			//print out values
			System.out.println("\nRadius received from client " + radius);
			System.out.println("Area found " + area);
			System.out.println("Sphere volume " + spherevol+"\n");

			//send data to client
			osToClient.writeDouble(area);
			osToClient.writeDouble(spherevol);
			osToClient.flush();
		  }  // end while

		}  // end try
		catch(IOException ex)
		{
		  System.err.println(ex);
		}  // end catch

  }  // end  main
  
  //display program information
	private static void programInformation ()
	{
		System.out.println ("=== Java program demonstrating client/server architecture.  The server calculates the area and sphere volume from a circle radius supplied by the client. ===");
		System.out.println ("(c) Lynsay A. Shepherd, October 2009.\n");
		System.out.println ("This is the SERVER\n\n");
	} // end program information

}
