package PoisedPackage;

import java.util.*;

/**
 * 
 * @author Luc Mayne
 * @version 10/5/2022
 */

public class Person {

	/**
	 * Person attributes
	 */
	String name;
	String surname; 
	String phoneNum;
	String emailAddress;
	String homeAddress;
	String personType;
	String projectID;
	
	// Scanner object
	private static Scanner stringScanner = new Scanner(System.in);	
	
	/**
	 * Allows a user to set up a new person object
	 * @param personType indicates if its an architect, contractor or customer
	 * @param id is the id for the current project
	 */
	public void setPersonInfo(String personType, String id) {
		
		// set the personType
		this.personType = personType;	
		// set the projectID
		this.projectID = id;
		
		// get data for a person object from the user
		System.out.println("\nEnter Details for the " + personType + ":");
		
		System.out.println("Name:");
		this.name = userString();	
		
		System.out.println("Surname:");
		this.surname = userString();
		
		System.out.println("Phone Number:");
		this.phoneNum = userString();
		
		System.out.println("Email:");
		this.emailAddress = userString();
		
		System.out.println("Home Address:");
		this.homeAddress = userString();
	}
	
	/**
	 * setContractorDetails
	 * Updates contractor details
	 */
	public void setContractorDetails() {
		
		// ask user for phone number and email
		System.out.println("Phone Number:");
		this.phoneNum = userString();
		
		System.out.println("Email:");
		this.emailAddress = userString();
	}
	
	/**
	 * Get an array of the persons information
	 * @return personData[]
	 */
	public String[] getPersonArray() {
		// create a new array personData
		String[] personData = new String[7];
		
		// add data to the array
		personData[0] = projectID;
		personData[1] = personType;
		personData[2] = name;
		personData[3] = surname;
		personData[4] = phoneNum;
		personData[5] = emailAddress;
		personData[6] = homeAddress;
		
		return personData;
	}
	
	/**
	 * Gets a String from the user
	 * @return the string inputed by the user
	 */
	private static String userString() {
		
		String stringInput = "";
		
		// get input
		stringInput = stringScanner.nextLine();
		
		// reset the Scanner
		stringScanner.reset();
		
		return stringInput;
	}
	
	
	
	/**
	 * Closes the String Scanner
	 */
	public void closeScanner() {
		// close Scanner
		stringScanner.close();
	}
	
}
