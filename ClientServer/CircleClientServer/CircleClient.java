/*
	File: CircleClient.java
	Version: 1.1
	Date: October 2009
	Author: Lynsay A. Shepherd
	
	Description:  Java program demonstrating client/server architecture.  The server calculates the area and sphere volume from a circle radius supplied by the client.  Written as part of an MSc class.
*/

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class CircleClient {
    public static void main(String[] args){
    
    	 //Program header
        programInformation();
    
		//starting radius
		double radius = 1.0;

		try
		{
		  //create a socket to connect to server
		  Socket connectToServer = new Socket("localhost", 0000);

		  //create an input stream to get data from server
		  DataInputStream isFromServer = new DataInputStream(connectToServer.getInputStream());

		  //create an output stream to send data to server
		  DataOutputStream osToServer = new DataOutputStream(connectToServer.getOutputStream());

		  //send radius to server and receive area back
		  while (true)
		  {
			radius =  radius + 1;
			System.out.println("Radius sent to server " + radius);

			//send data to server
			osToServer.writeDouble(radius);
			osToServer.flush();

			//print out data received
			double area = isFromServer.readDouble();
			double spherevol = isFromServer.readDouble();
			System.out.println("\nArea received from server " + area);
			System.out.println("Sphere volume received from server " + spherevol+"\n");
		  }  // end while

		}  // end try
		catch(IOException ex)
		{
		  System.err.println(ex);
		}  // end catch

  }  // end main
  
	//display program information
	private static void programInformation ()
	{
		System.out.println ("=== Java program demonstrating client/server architecture.  The server calculates the area and sphere volume from a circle radius supplied by the client. ===");
		System.out.println ("(c) Lynsay A. Shepherd, October 2009.\n");
		System.out.println ("This is the CLIENT\n\n");
	} // end program information

}
