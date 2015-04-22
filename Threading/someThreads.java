/*
	File: someThreads.java
	Version: 1.1
	Date: September 2009
	Author: Lynsay A. Shepherd
	
	Description:  Java program demonstrating the use of threads.  Written as part of an MSc class.
*/

public class someThreads {

    public static void main(String args[]) {
    
    	//Threads required
        PrintThread thread1, thread2, thread3, thread4,thread5, thread6;
        
        //Program header.
        programInformation();

        //Create four PrintThread objects
        thread1 = new PrintThread("Thread 1 ");
        thread2 = new PrintThread("Thread 2 ");
        thread3 = new PrintThread("Thread 3 ");
        thread4 = new PrintThread("Thread 4 ");
        thread5 = new PrintThread("Thread 5 ");
        thread6 = new PrintThread("Thread 6 ");

        System.out.println("\nNow about to start the threads\n");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();

    }  // end main
    
    //display program information
	private static void programInformation ()
	{
		System.out.println ("=== Java program demonstrating the use of threads.  Written as part of an MSc class. ===");
		System.out.println ("(c) Lynsay A. Shepherd April 2009.\n");
	} // end program information
	
}  // end class someThreads



class PrintThread extends Thread {

	//Threads will have a random sleep time calculated (milliseconds)
    private int sleepTime;

    public PrintThread(String name) {
        super(name);
        sleepTime = (int) (Math.random() * 10000);
        System.out.println("Creating Thread: " + getName() +". It will sleep for " + sleepTime+" milliseconds");
    } // end constructor PrintThread


    public void run() {
        try {
        	System.out.println(getName() + "going to  sleep for " +sleepTime);
            Thread.sleep(sleepTime);
            System.out.println(getName() + "has finished sleeping");
        } 
        
        catch (InterruptedException ex) {
            System.out.println(ex.toString());
        }  // end try / catch

        
    }  // end run
}  // end class PrintThread
