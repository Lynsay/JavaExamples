/*
	File: bankAccountWithoutSynch.java
	Version: 1.1
	Date: September 2009, modified April 2015
	Author: Lynsay A. Shepherd

	Description:  Java program demonstrating the use of threads without synchronization.  Program simulates a bank account.  If the threads aren't synched, they will not display the correct account balance.  Written as part of an MSc class.
*/

public class bankAccountWithoutSynch
{
	private bankAccount bank = new bankAccount();
	private Thread[] thread = new Thread[5];

	
	//constructor
	public bankAccountWithoutSynch()
	{

		// Create and launch threads
		for (int i = 0;  i < 5; i++)
		{
			thread[i] = new Thread(new addAPennyThread("Thread "+i));
			thread[i].start();
		}

	}  // end constructor

	public static void main(String[] args)
	{
		programInformation();
		bankAccountWithoutSynch account =new bankAccountWithoutSynch();
		System.out.println("Balance is " +account.bank.getBal());
	}  // end main

	//--- display program information
	private static void programInformation ()
	{
		System.out.println ("===  Java program demonstrating the use of threads without synchronization.  Program simulates a bank account.  If the threads aren't synched, they will not display the correct account balance. ===");
		System.out.println ("(c) Lynsay A. Shepherd, September 2009");
		System.out.println ();
	} // end program information method



	class addAPennyThread extends Thread
	{
		public addAPennyThread (String name){
		super (name);
		System.out.println("Creating thread:"+ getName());
		}
	
		public void run(){
			int newBal = bank.getBal() + 1;
			System.out.println("Balance read by thread " + getName()+" as "+ bank.getBal());
		
			try
			{
				Thread.sleep(10);
			}
		
			catch (InterruptedException ex)
			{
				System.out.println(ex);
			}
		
			bank.setBal(newBal);
			System.out.println("Balance increased by thread  " + getName()+" to "+ newBal);
		}  // end run

	}  // end class addAPennyThread

}  // end class bankAccountWithoutSynch


class bankAccount
{
	//start the balance at 10
	private int balance = 10;
	public void setBal(int balance){this.balance = balance;}
	public int  getBal(){return balance;}
}  // end class bankAccount

