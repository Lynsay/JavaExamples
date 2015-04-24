/*
	File: Client.java
	Version: 1.1
	Date: October 2009
	Author: Lynsay A. Shepherd
	
	Description:  Java program demonstrating a client/server and GUI.  This is the client.
*/

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Client extends JFrame {

    private JTextField enterField;
    private JTextArea displayArea;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket connectToServer;
    private String message = "";
    private String chatServer;

	//constructor
    public Client() {
    }
    
	//constructor
    public Client(String host) {
    
        super("Client");

        //set server to which the client connects
        chatServer = host;

        Container container = getContentPane();
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

        //create display area
        displayArea = new JTextArea();
        container.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        setSize(300, 150);
        setLocation(0, 155);
        setVisible(true);
    }//end constructor


    //set up and run client
    public void runClient() {
        System.out.println("Started runclient");
        try {
            connectToServer();
            getIOStreams();
            processConnections();
            closeConnections();
        }
        
        //server closes connection
        catch (EOFException eofException) {
            System.out.println("Server terminated connection");
        }
        
        //process problems communications with the server
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }//end run client


    
    //send message to server
    private void sendData(String message) {
        try {
            output.writeObject("Client>> " + message);
            output.flush();
            displayArea.append("\nClient>> " + message);

            //if client sends bye, then close the client window
            if (message.equals("BYE")) {
                System.exit(0);
            }

        }//end try
        
        //process problems sending to server
        catch (IOException ioException) {
            displayArea.append("\nError writing object");
        }
    }//end senddata method


   
    //connect to server
    private void connectToServer() throws IOException {
        System.out.println("started connect to server");
        displayArea.setText("Attempting connection \n");
        connectToServer = new Socket(InetAddress.getByName(chatServer), 0000);
        displayArea.append("Connected to " + connectToServer.getInetAddress().getHostName());
    }//end connect to server


   
    //set streams to send and receive data
    private void getIOStreams() throws IOException {
        output = new ObjectOutputStream(connectToServer.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connectToServer.getInputStream());
        displayArea.append("\nGot IO Stream\n");
    }//end get iostream method

   
   
    //process connection with server
    private void processConnections() throws IOException {
        
        enterField.setEnabled(true);

        do {
            try {
                message = (String) input.readObject();
                displayArea.append("\n" + message);
                displayArea.setCaretPosition(displayArea.getText().length());
            	}
            	
            //process problems reading from server
            catch (ClassNotFoundException classNotFoundException) {
                displayArea.append("\nUnknown object type received");
            }
            
        } while (!message.equals("Server>> BYE"));

        if (message.equals("Server>> BYE")) {
            //if bye, close window
            System.exit(0);
        }

    }//end process connections method


    
    //close streams and connections
    private void closeConnections() throws IOException {
        displayArea.append("\nClosing connections");
        output.close();
        input.close();
        connectToServer.close();
    }//end close connection
    
    //display program information
	private static void programInformation ()
	{
		System.out.println ("=== Java program demonstrating a client/server and GUI.  Only works for one client.  This is the client. ===");
		System.out.println ("(c) Lynsay A. Shepherd, October 2009.\n");
		System.out.println ("This is the CLIENT\n\n");
	} // end program information


    //main
    public static void main(String[] args) {
    	programInformation();
        Client application = new Client("localhost");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println("Starting runclient");
        application.runClient();
    }//end main message
}//end class

