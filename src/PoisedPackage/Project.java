package PoisedPackage;

import java.util.*;

/**
 * 
 * @author Luc Mayne
 * @version 10/5/2022
 */

public class Project {

	/**
	 * Project attributes
	 */
	int number;
	String name;
	String buildType;
	String address;
	int erfNumber;
	int totalFee;
	int totalPaid;
	String dueDate;
	
	/**
	 * String Scanner
	 * Integer Scanner
	 */
	private static Scanner stringScanner = new Scanner(System.in);
	private static Scanner intScanner = new Scanner(System.in);
			
	
	/**
	 * Allows a user to enter data for a new project
	 */
	public void setProjectInfo() {	
		
		// get data from the user to create a new project object
		System.out.println("\nEnter Project Details.");
		
		System.out.println("Project Number:");
		this.number = userInteger("Number");
		
		System.out.println("Project Name:");
		this.name = userString();
		
		System.out.println("Building Type:");
		this.buildType = userString();
		
		System.out.println("Project Address:");
		this.address = userString();
		
		System.out.println("ERF Number:");
		this.erfNumber = userInteger("ERF Number");
		
		System.out.println("Total Fee:");
		this.totalFee = userInteger("Total Fee");
		
		System.out.println("Total Fee Paid:");
		this.totalPaid = userInteger("Total Fee Paid");
		
		System.out.println("Project Deadline: (yyyy-mm-dd)");
		this.dueDate = userString();
		
		// if user doesn't enter a project name
		if (name.length() == 0) {
			name = buildType;
		}
	}
	
	/**
	 * Get an array of the project information
	 * @return projectData[]
	 */
	public Object[] getProjectArray() {
		// create new array for project information
		Object[] projectData = new Object[8];
		
		// add the data to the array
		projectData[0] = number;
		projectData[1] = name;
		projectData[2] = buildType;
		projectData[3] = address;
		projectData[4] = erfNumber;
		projectData[5] = totalFee;
		projectData[6] = totalPaid;
		projectData[7] = dueDate;
		
		return projectData;
	}
	
	/**
	 * Allows a user to update the due date
	 */
	public void setDueDate() {
		System.out.println("\nEnter new Due Date - (yyyy-mm-dd)");
		// get the due date from the user and set it
		this.dueDate = userString();
	}
	
	/**
	 * Allows a user to update the total fee paid
	 */
	public void setTotalFee() {
		// ask user for the new total fee paid
		System.out.println("\nEnter the total fee paid so far:");
		// set the totalPaid
		this.totalPaid = userInteger("Total Fee Paid");
		
		System.out.println("\nTotal fee paid updated to R" + totalPaid);
	}
	
	/**
	 * Gets an integer from the user
	 * @param info
	 * @return the users integer
	 */
	private static int userInteger(String info) {
		// returns an integer value
		int intValue = 0;
		
		// loop until user enters the correct data
		do {
			try {
				intValue = intScanner.nextInt();
				// if no errors are raised, break
				break;
			} catch (InputMismatchException e) {
				// print an error message
				System.out.println("\nIncorrect input, Please enter the " + info + " again:");
			}
			// clear the Scanner
			intScanner.next();
		} while (true);
		
		return intValue;
	}
	
	/**
	 * Gets a String from the user
	 * @return the string inputed by the user
	 */
	private static String userString() {
		// returns string input
		String stringInput = "";
		stringInput = stringScanner.nextLine();
		// reset the Scanner
		stringScanner.reset();
		return stringInput;
	}
	
	/**
	 * Closes the String and Int Scanner
	 */
	public void closeScanner() {
		// close the Scanners
		intScanner.close();
		stringScanner.close();
	}
}
