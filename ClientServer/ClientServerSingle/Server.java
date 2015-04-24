/*
	File: Server.java
	Version: 1.1
	Date: October 2009
	Author: Lynsay A. Shepherd
	
	Description:  Java program demonstrating a client/server and GUI.  Only works for one client.  This is the server.
*/

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server extends JFrame {

    private JTextField enterField;
    private JTextArea displayArea;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket serverSocket;
    private Socket connectToClient;
    private int i = 1;

	//constructor
    public Server() {
    
        super("Server");
        Container container = getContentPane();

        //create enter field for messages to client; add action listener
        enterField = new JTextField();
        enterField.setEnabled(true);

        enterField.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	sendData(e.getActionCommand());
                enterField.setText("");
            }//action performed
        }//end anon inner class
        ); //endcall to actionlistener
        container.add(enterField, BorderLayout.NORTH);


        //create display message area
        displayArea = new JTextArea();
        container.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        setSize(300, 150);
        setLocation(0, 0);
        setVisible(true);
        
    }//end constructor server



    //setup and run server
    public void runServer() {
        try {
            //create a server socket
            serverSocket = new ServerSocket(0000, 100);

            while (true) {
                waitForConnection();
                getIOStreams();
                processConnections();
                closeConnections();
                ++i;
            }//end while
        }//end try
        
        //client closes connection
        catch (EOFException eofException) {
            System.out.println("Client terminated connection");
        }
        
        //process problems communicating with client
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }//end run server


    //send message to client
    private void sendData(String message) {
        try {
            output.writeObject("Server>> " + message);
            output.flush();
            displayArea.append("\nServer>> " + message);

            //if server sends bye, close the server window
            if (message.equals("BYE")) {
                System.exit(0);
            }
        }//end try
        
        //process problems sending to client
        catch (IOException ioException) {
            displayArea.append("\nError writing object");
        }
    }//end senddata method


    //wait for connection from client to arrive, then display connection info
    private void waitForConnection() throws IOException {
        displayArea.setText("Waiting for connection \n");
        connectToClient = serverSocket.accept();
        displayArea.append("Connection " + i + " received from " + connectToClient.getInetAddress().getHostName());
    }//end wait for connection



    //set streams to send and receive data
    private void getIOStreams() throws IOException {
        output = new ObjectOutputStream(connectToClient.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connectToClient.getInputStream());
        displayArea.append("\nGot IO Stream\n");
    }//end get iostream method


    //process connection with server
    private void processConnections() throws IOException {

        //send successful connection message to client
        String message = "Server>> Connection successful";
        output.writeObject(message);
        output.flush();
        enterField.setEnabled(true);

		//process messages from client
        do {
            try {
                message = (String) input.readObject();
                displayArea.append("\n" + message);
                displayArea.setCaretPosition(displayArea.getText().length());
            	}
            
            //process problems reading from client
            catch (ClassNotFoundException classNotFoundException) {
                displayArea.append("\nUnknown object type received");
            	}
            } while (!message.equals("Client>> BYE"));
			//if client does=bye do nothing- keep server running for another client

    }//end process connections method



    //close streams and connections
    private void closeConnections() throws IOException {
        displayArea.append("\nUser terminated connection");
        output.close();
        input.close();
        connectToClient.close();
    }//end close connection
    
    
    //display program information
	private static void programInformation ()
	{
		System.out.println ("=== Java program demonstrating a client/server and GUI.  Only works for one client.  This is the server. ===");
		System.out.println ("(c) Lynsay A. Shepherd, October 2009.\n");
		System.out.println ("This is the SERVER\n\n");
	} // end program information


    //main
    public static void main(String[] args) {
    	programInformation();
        Server application = new Server();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println("Starting runclient");
        application.runServer();
        }//end main message
        
}//end server class

