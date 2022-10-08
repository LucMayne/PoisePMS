package PoisedPackage;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

/**
 * 
 * @author Luc Mayne
 * @version 10/5/2022
 */

public class SQLdata {	
	
	/*
	 * FileData attributes
	 */	
	Connection connection;
	Statement statement;

	/**
	 * Initiates the connection and statement
	 * @throws SQLException if an error occurs
	 */
	public void createConnections() throws SQLException {		
		this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "user1", "admin");
		this.statement = connection.createStatement();
	}
	
	/**
	 * Closes the connection and statement
	 * @throws SQLException if an error occurs
	 */
	public void closeConnections() throws SQLException {
		statement.close();
		connection.close();
	}
	
	/**
	 * Saves a project to the 'project' table in the PoisePMS database
	 * @param projectData is an array of the projects data
	 * @throws SQLException if an error occurs
	 */
	public void saveProjectToDatabase(Object[] projectData) throws SQLException {
		
		try {
			// separate data
			int id = (Integer) projectData[0];
			String name = (String) projectData[1];
			String build = (String) projectData[2];	
			String address = (String) projectData[3];
			int erf = (Integer) projectData[4];
			int totalFee = (Integer) projectData[5];
			int feePaid = (Integer) projectData[6];
			String dueDate = (String) projectData[7];			
			
			// add to table project
			statement.executeUpdate("INSERT INTO project VALUES (" + id + ", '" + name + "', '" + build + "', '" + address + "', " + erf + ", " + totalFee + ", " + feePaid + ", '" + dueDate + "', 'n', '0000-00-00')");
			
			System.out.println("\nProject Added.");
			
		}
		// if the project id already exists in the database print an error message
		catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("\nThis Project ID already exists! Please enter a new one.");
		}		
	}
	
	/**
	 * Saves a person to the 'person' table in the PoisePMS database
	 * @param personData is an array of the persons data
	 * @throws SQLException if an error occurs
	 */
	public void savePersonToDatabase(String[] personData) throws SQLException {
		
		// separate data
		String id = personData[0];
		String type = personData[1];
		String name =  personData[2];
		String surname =  personData[3];
		String phoneNum =  personData[4];
		String email =  personData[5];
		String homeAddress =  personData[6];
		
		// add to table person
		statement.executeUpdate("INSERT INTO person VALUES (" + id + ", '" + type + "', '" + name + "', '" + surname + "', '" + phoneNum + "', '" + email + "', '" + homeAddress + "')");
		
		System.out.println("\nPerson Added");		
	}
	
	/**
	 * Deletes a project from the PoisePMS database
	 * @param id is the projects pro_id
	 * @throws SQLException if an error occurs
	 */
	public void deleteProject(String id) throws SQLException {	
		
		// delete where the project id equals the id given by user
		statement.executeUpdate("DELETE FROM project WHERE pro_id=" + id);
		statement.executeUpdate("DELETE FROM person WHERE pro_id=" + id);
		
		System.out.println("\nProject Deleted.");		
	}
	
	/**
	 * If the project is finalised the method returns true
	 * @param id is the projects pro_id
	 * @return finalised boolean
	 * @throws SQLException if an error occurs
	 */
	public boolean projectFinalised(String id) throws SQLException {
		
		boolean finalised = false;
		
		// create project ResultSet which selects all from the project table
		ResultSet project = statement.executeQuery("SELECT * FROM project");
		
		while (project.next()) {
			
			// if the id matches an id in the table
			if (Objects.equals(id, project.getString("pro_id"))) {
				
				// if the fin_pro value is 'y' set finalised to true
				if (Objects.equals("y" ,project.getString("fin_pro"))) {
					finalised = true;
				}
				break;
			}				
		}
		
		// close project 
		project.close();
		
		return finalised;
	}
	
	/**
	 * If there is an id match return true
	 * @param id is the projects pro_id
	 * @return idMatch boolean
	 * @throws SQLException if an error occurs
	 */
	public boolean checkProjectNumber(String id) throws SQLException {
		
		boolean idMatch = false;
		
		// create project ResultSet which selects all from the project table
		ResultSet project = statement.executeQuery("SELECT * FROM project");
		
		while (project.next()) {	

			// if there is a match set idMatch to true
			if (Objects.equals(id, project.getString("pro_id"))) {
				
				idMatch = true;
				break;
			}				
		}
		
		// close project 
		project.close();
		
		return idMatch;
	}
	
	/**
	 * If details for the person have already been entered the method will return true
	 * @param personType indicates if its an architect, contractor or customer
	 * @param id is the projects pro_id
	 * @return exists boolean
	 * @throws SQLException if an error occurs
	 */
	public boolean checkPerson(String personType, String id) throws SQLException {
				
		boolean exists = false;
		
		// create person ResultSet which selects all from the person table
		ResultSet person = statement.executeQuery("SELECT * FROM person");
		
		while (person.next()) {
			
			// if details for the person have already been entered set exists to true
			if (Objects.equals(personType, person.getString("person_type")) && Objects.equals(id, person.getString("pro_id"))) {
				
				System.out.println("\nYou have already entered details for the " + personType + ".");
				exists = true;
				break;
			}
		}
		
		// close person
		person.close();
		
		return exists;
	}
	
	/**
	 * If all details for the project have been entered return true
	 * @param id is the projects pro_id
	 * @return allDetails boolean
	 * @throws SQLException if an error occurs
	 */
	public boolean allDetailsEntered(String id) throws SQLException {
		
		boolean allDetails = false;
		
		// create person ResultSet which selects all from the person table
		// where the pro_id equals the id given by the user
		ResultSet person = statement.executeQuery("SELECT * FROM person WHERE pro_id=" + id);
		
		// counts the amount of persons in the person table for the specified id
		int personCount = 0;
		
		// update personCount for each person
		while (person.next()) {
			personCount++;
		}
		
		// if all three person types exist, set allDetails to true
		if (personCount == 3) {
			allDetails = true;
		}
		
		person.close();
		
		return allDetails;
	}
	
	/**
	 * Displays the project for the id given by the user
	 * @param id is the projects pro_id
	 * @throws SQLException if an error occurs
	 */
	public void displayProject(String id) throws SQLException {
				
		// create project ResultSet which selects all from the project table
		ResultSet project = statement.executeQuery("SELECT * FROM project");
		
		while (project.next()) {
			
			// if the id's match print the project
			if (Objects.equals(id, project.getString("pro_id"))) {
				
				String projectInfo = "\nID Number: " + project.getString("pro_id")
						+ "\nName: " + project.getString("pro_name")
						+ "\nBuilding Type: " + project.getString("build_type")
						+ "\nAddress: " + project.getString("pro_address")
						+ "\nERF Number: "  + project.getString("erf_num")
						+ "\nTotal Fee: " + project.getString("total_fee")
						+ "\nTotal Paid: " + project.getString("total_paid")
						+ "\nDeadline: " + project.getString("deadline");
				
				System.out.println(projectInfo);
				break;
			}
		}
		project.close();
	}
	
	/**
	 * Displays the persons for the id given by the user
	 * @param id is the projects pro_id
	 * @throws SQLException if an error occurs
	 */
	public void displayPerson(String id) throws SQLException {
		
		// create person ResultSet which selects all from the person table
		ResultSet person = statement.executeQuery("SELECT * FROM person");
		
		while (person.next()) {
			
			// if the id's match, print the person
			if (Objects.equals(id, person.getString("pro_id"))) {
				
				String personInfo = "\n" + person.getString("person_type") + " details."
						+ "\nName: " + person.getString("name")
						+ "\nSurname: " + person.getString("surname")
						+ "\nPhone Number: " + person.getString("phone_num")
						+ "\nEmail: "  + person.getString("email")
						+ "\nHome Address: " + person.getString("home_address");
				
				System.out.println(personInfo);
			}
		}
		person.close();
	}
	
	/**
	 * Displays all projects that are marked as incomplete in project table 
	 * @throws SQLException if an error occurs
	 */
	public void displayIncompleteProjects() throws SQLException {
		
		// create project ResultSet which selects all from the project table
		ResultSet project = statement.executeQuery("SELECT * FROM project");
		
		while (project.next()) {
			
			// if the projects fin_pro equals 'n', print the project
			if (Objects.equals("n", project.getString("fin_pro"))) {
				
				String projectInfo = "\nID Number: " + project.getString("pro_id")
				+ "\nName: " + project.getString("pro_name")
				+ "\nBuilding Type: " + project.getString("build_type")
				+ "\nAddress: " + project.getString("pro_address")
				+ "\nERF Number: "  + project.getString("erf_num")
				+ "\nTotal Fee: " + project.getString("total_fee")
				+ "\nTotal Paid: " + project.getString("total_paid")
				+ "\nDeadline: " + project.getString("deadline");
				
				System.out.println(projectInfo + "\n-----------------------");
			}
		}
	}
	
	/**
	 * Displays all overdue projects
	 * @throws SQLException if an error occurs
	 * @throws ParseException if an error occurs
	 */
	public void displayOverdueProjects() throws SQLException, ParseException {
		
		// create project ResultSet which selects all from the project table
		ResultSet project = statement.executeQuery("SELECT * FROM project");
		
		// get the current date
		LocalDate currentDate = LocalDate.now();
		
		// format of the date
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date todaysDate;
		Date projectDate;
		
		// boolean to check if the project is overdue
		boolean projectOverdue = false;
		
		while (project.next()) {
			
			// if the project fin_pro equals 'n'
			if (Objects.equals("n", project.getString("fin_pro"))) {
				
				// get todayDate and the projectDate
				todaysDate = dateFormat.parse(currentDate.toString());
				projectDate = dateFormat.parse(project.getString("deadline"));
				
				// if the project is overdue set projectOverdue to true
				if (projectDate.compareTo(todaysDate) < 0) {
					projectOverdue = true;
				}	
				
				// if projectOverdue print the details
				if (projectOverdue) {
					System.out.println("\nThis Project is overdue:"
							+ "\nID: " + project.getString("pro_id")
							+ "\nName: " + project.getString("pro_name")
							+ "\nDeadline: " + project.getString("deadline"));
				}
				
				// set projectOverdue to false before looping again
				projectOverdue = false;
			}
		}		
	}
	
	/**
	 * Updates the contractor in the person table at the id given by the user
	 * @param phoneNum is the contractors phone number
	 * @param email is the contractors email
	 * @param id is the projects pro_id
	 * @throws SQLException if an error occurs
	 */
	public void updateContractorDetails(String phoneNum, String email, String id) throws SQLException {
		
		// update contractor
		statement.executeUpdate("UPDATE person SET phone_num='" + phoneNum + "', email='" + email + "' WHERE pro_id=" + id + " AND person_type='contractor'");
		System.out.println("\nUpdated Contractor Details.");
	}
	
	/**
	 * Updates the projects deadline in the project table at the id given by the user
	 * @param deadline is the projects due date
	 * @param id is the projects pro_id
	 * @throws SQLException if an error occurs
	 */
	public void updateDeadline(String deadline, String id) throws SQLException {
		
		// update deadline
		statement.executeUpdate("UPDATE project SET deadline='" + deadline + "' WHERE pro_id=" + id);
		System.out.println("\nDeadline Updated.");		
	}
	
	/**
	 * Updates the projects total fee paid at the id given by the user
	 * @param totalPaid is the total amount paid for the project
	 * @param id is the projects pro_id
	 * @throws SQLException if an error occurs
	 */
	public void updateTotalFee(int totalPaid, String id) throws SQLException {
		
		// update total_paid
		statement.executeUpdate("UPDATE project SET total_paid=" + totalPaid + " WHERE pro_id=" + id);		
	}
	
	/**
	 * If the customer owes money, the customers contact details are displayed
	 * Update project
	 * @param id is the projects pro_id
	 * @throws SQLException if an error occurs
	 */
	public void generateInvoice(String id) throws SQLException {
		
		// create totals ResultSet which selects the total_fee, total_paid from the project table
		// where the pro_id equals the id given by the user
		ResultSet totals = statement.executeQuery("SELECT total_fee, total_paid FROM project WHERE pro_id=" + id);
		
		int total = 0;
		int paid = 0;
		
		while (totals.next()) {
			// add total_fee to total, add total_paid to paid
			total = totals.getInt("total_fee");
			paid = totals.getInt("total_paid");
		}
		
		totals.close();
		
		// if the customer still owes money
		if (paid < total) {
			
			int customerOwes = total - paid;
			
			// create customer ResultSet which selects all from the person table
			ResultSet customer = statement.executeQuery("SELECT * from person");
			
			while (customer.next()) {
				
				// if the person type is customer and the id correct, print contact details
				if ("customer".equals(customer.getString("person_type")) && id.equals(customer.getString("pro_id"))) {
					
					System.out.println("\nCustomer Contact Details:");			
					System.out.println("Phone Number - " + customer.getString("phone_num"));
					System.out.println("Email - " + customer.getString("email"));
					System.out.println("Customer Owes - R" + customerOwes);
				}				
			}			
			customer.close();
		}		
		
		// get the current date
		LocalDate currentDate = LocalDate.now();
		
		// format the current date
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String date = currentDate.format(dateFormat);
		
		// update fin_date to the current date
		statement.executeUpdate("UPDATE project SET fin_date='" + date + "' WHERE pro_id=" + id);
		// update fin_pro to 'y'
		statement.executeUpdate("UPDATE project SET fin_pro='y' WHERE pro_id=" + id);
		
	}
}
