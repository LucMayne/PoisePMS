package PoisedPackage;
import java.sql.SQLException;
import java.text.*;
import java.util.*;

/**
 * @author Luc Mayne
 * @version 10/5/2022
 */

public class Main {

	/**
	 * This is the main method that lets the user modify projects
	 * @param args for main method
	 * @throws SQLException if an error occurs
	 * @throws ParseException if an error occurs
	 */
	public static void main(String[] args) throws SQLException, ParseException {
		
		// project id
		String id;
		 
		// create an SQLdata object and call createConnections method
		SQLdata sqlData = new SQLdata();
		sqlData.createConnections();
		
		// menu, menu input
		String menu;
		String menuInput;
		
		// create architect, contractor, customer, new poised project
		Person architect = new Person();
		Person contractor = new Person();
		Person customer = new Person();
		Project project = new Project();					
		
		// set up Scanners
		Scanner inputScanner = new Scanner(System.in);
		
		// main loop
		while (true) {
			
			// main menu
			menu = "\nPick one of the options below:"
					+ "\npd - Enter Project Details"
					+ "\ndp - Delete Project"
					+ "\nsp - Select and Update a Project"
					+ "\nvp - View Project"
					+ "\nip - Incomplete Projects"
					+ "\nop - Overdue Projects"
					+ "\nfp - Finalise Project"
					+ "\ne  - Exit Program";
			
			System.out.println(menu);
			
			// get menu input
			menuInput = inputScanner.nextLine().toLowerCase();
			
			// if user chooses to exit
			if (menuInput.charAt(0) == 'e') {
				
				// close all Scanners
				closeScanners(inputScanner, project, architect, contractor, customer);
				
				// close connections
				sqlData.closeConnections();
				
				// exit
				System.out.println("Goodbye!");
				break;			
			}
			
			// switch on the users input
			switch (menuInput) {
			
			// enter project details
			case "pd":
					
					// loop until user chooses to exit
					while (true) {
						
						// project menu
						menu = "\nPick one of the options below:"
								+ "\npro - Enter project details"
								+ "\narc - Enter architect details"
								+ "\ncon - Enter contractor details"
								+ "\ncus - Enter customer details"
								+ "\ne   - Exit to main menu";
						System.out.println(menu);
						
						// get input from user
						menuInput = inputScanner.nextLine().toLowerCase();
						
						// exit to main menu
						if (menuInput.charAt(0) == 'e') {
							break;
						}	
						
						switch (menuInput) {	
						
						// create a new project
						case "pro":
							
							// set info for new project
							project.setProjectInfo();	
							// save project to database
							sqlData.saveProjectToDatabase(project.getProjectArray());								
							break;
													
						// create new architect
						case "arc":
							
							System.out.println("\nPlease Enter the Projects ID to enter details:");
							id = inputScanner.nextLine();
							
							// if checkProjectNumber is true and checkPerson is false
							if (sqlData.checkProjectNumber(id) && !sqlData.checkPerson("architect", id)) {	
								// set person info
								architect.setPersonInfo("architect", id);
								
								// save person to database
								sqlData.savePersonToDatabase(architect.getPersonArray());								
							}							
							break;
							
						// create a new contractor
						case "con":
							
							// get the id from the user
							System.out.println("\nPlease Enter the Projects ID to enter details:");
							id = inputScanner.nextLine();
							
							// if checkProjectNumber is true and checkPerson is false
							if (sqlData.checkProjectNumber(id) && !sqlData.checkPerson("contractor", id)) {
									// set person info
									contractor.setPersonInfo("contractor", id);
									
									// save person to database
									sqlData.savePersonToDatabase(contractor.getPersonArray());						
							}							
							break;
						
						// create a new architect
						case "cus":
							
							// get the id from the user
							System.out.println("\nPlease Enter the Projects ID to enter details:");
							id = inputScanner.nextLine();
							
							// if checkProjectNumber is true and checkPerson is false
							if (sqlData.checkProjectNumber(id) && !sqlData.checkPerson("customer", id)) {
								// set person info
								customer.setPersonInfo("customer", id);
								
								// save person to database
								sqlData.savePersonToDatabase(customer.getPersonArray());
							}							
							break;						
							
						default:
							System.out.println("\nIncorrect input.");
						}
					}
					break;
			
			case "dp":
				
				System.out.println("\nEnter The projects ID to delete it:");
				
				// get the id from the user
				id = inputScanner.nextLine();		
				
				// delete the project with the given id
				sqlData.deleteProject(id);				
				break;
				
			// select project 
			case "sp":
				
				// get the id from the user
				System.out.println("\nEnter the project ID to select it:");				
				id = inputScanner.nextLine();
				
				// if checkProjectNumber is true and projectFinalised is false
				if (sqlData.checkProjectNumber(id) && !sqlData.projectFinalised(id)) {
					
					// display the project with the given id
					sqlData.displayProject(id);
					// display the persons with the given id
					sqlData.displayPerson(id);
					
					while (true) {
						// project menu
						menu = "\nPick one of the options below:"
								+ "\nuc - Update Contractor Details"
								+ "\nud - Update Due Date"
								+ "\nut - Update Total Fee"
								+ "\ne  - Exit to Main Menu";
						System.out.println(menu);
						
						// get input from user
						menuInput = inputScanner.nextLine().toLowerCase();
						
						// exit to main menu
						if (menuInput.charAt(0) == 'e') {
							break;
						}
						
						switch (menuInput) {
						
						// Update contractor details
						case "uc":
							
							// set the contractor details
							contractor.setContractorDetails();
							// update the contractor details on the database
							sqlData.updateContractorDetails(contractor.phoneNum, contractor.emailAddress, id);							
							break;
							
						// Update Due Date
						case "ud":
							
							// set the project due date
							project.setDueDate();
							// update the project deadline on the database
							sqlData.updateDeadline(project.dueDate, id);							
							break;
							
						// Update total fee
						case "ut":
							
							// set the projects total fee paid
							project.setTotalFee();
							// update the total fee paid
							sqlData.updateTotalFee(project.totalPaid, id);							
							break;
							
						default:
							System.out.println("\nIncorrect Input.");
						}							
					}					
				}
				else {
					System.out.println("\nInvalid ID.");
				}				
				break;
				
			// view project	
			case "vp":
				
				// get the project id from the user
				System.out.println("\nEnter the project ID to view it:");				
				id = inputScanner.nextLine();
				
				// if the project id is valid
				if (sqlData.checkProjectNumber(id)) {
					
					// display the project with the given id
					sqlData.displayProject(id);
					// display the persons with the given id
					sqlData.displayPerson(id);
				}				
				break;
				
			// view incomplete projects
			case "ip":				
				
				// display the incomplete projects
				sqlData.displayIncompleteProjects();
				break;
				
			// overdue projects
			case "op":
				
				// display overdue projects
				sqlData.displayOverdueProjects();											
				break;				
				
			case "fp":
				
				// get the project id from the user to finalise it
				System.out.println("\nEnter the project ID to finalise it:");				
				id = inputScanner.nextLine();
				
				// if the id number is valid and all details are entered for the project
				if (sqlData.checkProjectNumber(id) && sqlData.allDetailsEntered(id)) {
					
					// call generateInvoice method
					sqlData.generateInvoice(id);
					System.out.println("\nProject Finalised.");
				}
				else {
					System.out.println("\nYou have not entered all of the details for the project.");
				}				
				break;
				
			default:
				System.out.println("\nIncorrect input.");
			}
		}
	}
	
	/**
	 * Closes all Scanner objects
	 * @param scan1 inputScanner
	 * @param scan2 Project Scanner
	 * @param scan3 Person Scanner
	 * @param scan4 Person Scanner
	 * @param scan5 Person Scanner
	 */
	public static void closeScanners(Scanner scan1, Project scan2, Person scan3, Person scan4, Person scan5) {
		// main Scanners
		scan1.close();
		
		// Project Scanner
		scan2.closeScanner();
		
		// Person Scanners
		scan3.closeScanner();
		scan4.closeScanner();
		scan5.closeScanner();
	}	
	
}
