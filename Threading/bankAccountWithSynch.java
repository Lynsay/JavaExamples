/*
	File: bankAccountWithSynch.java
	Version: 1.1
	Date: September 2009, modified April 2015
	Author: Lynsay A. Shepherd

	Description:  Java program demonstrating the use of threads with synchronization.  Program simulates a bank account.  If the threads are synched, they will display the correct account balance.  Written as part of an MSc class.
*/

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class bankAccountWithSynch {

    private bankAccount bank = new bankAccount();
    ExecutorService executor = Executors.newCachedThreadPool();
    int newBal=0;

	//constructor
    public bankAccountWithSynch() {

        // Create and launch threads
        for (int i = 0; i < 5; i++) {
            executor.execute(new addAPennyThread("Thread "+i));
        }

        executor.shutdown();

        while (!executor.isTerminated()) {
            
        }
    }  // end constructor
    
    
    
	//main methods
    public static void main(String[] args) {
        bankAccountWithSynch bankAccount = new bankAccountWithSynch();
        System.out.println("Final balance is " +bankAccount.bank.getBal());
    }  // end main
    
    
    
    //display program information
	private static void programInformation ()
	{
		System.out.println ("===  Java program demonstrating the use of threads with synchronization.  Program simulates a bank account.  If the threads are synched, they will display the correct account balance. ===");
		System.out.println ("(c) Lynsay A. Shepherd, September 2009");
		System.out.println ();
	} // end program information method



	//increase bank balance
    private synchronized void addAPenny(String threadName) {
        
       System.out.println("Balance read by " + threadName + " as " + newBal);
        newBal= bank.getBal() + 1;
        try {
        	Thread.sleep(10);
        }
        catch (InterruptedException ex) {
            System.out.println(ex);
        }
        
        bank.setBal(newBal);
        System.out.println("Balance increased by " + threadName + " to " + newBal);
    }  // end addAPenny


	
    class addAPennyThread extends Thread {

        public addAPennyThread(String name) {
            super(name);
            System.out.println("Creating thread:" + getName());
        }

        @Override
        public void run() {
            addAPenny(getName());
        }//end run
        
    }  // end class addAPennyThread
    
}  // end class bankAccountWithoutSynch


//balance getters/setters
class bankAccount {

    private int balance = 0;

    public void setBal(int balance) {
        this.balance = balance;
    }

    public int getBal() {
        return balance;
    }
}  // end class bankAccount
