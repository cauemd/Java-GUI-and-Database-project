//Class responsible of the communication of all controllers with the database.
//All queries will be done through this class

package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Model {

	private Statement stmt;

	public Model(){

		try{
			// Load the database driver
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance() ;

			String dbServer = "jdbc:mysql://localhost:3306/barberSystem?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			String user = "root";
			String password = "";


			// Get a connection to the database
			Connection conn = DriverManager.getConnection(dbServer, user, password) ;

			// Get a statement from the connection
			this.stmt = conn.createStatement() ;

			//			// Loop through the result set
			//			while(rs.next()) {
			//				System.out.println( rs.getString("fullName") + "\t" + rs.getString("mobileNumber") + 
			//						"\t" + rs.getString("email") + "\t" + rs.getString("password")) ;
			//			}
			//
			//			// Close the result set, statement and the connection
			//			rs.close() ;
			//stmt.close() ;
			//conn.close() ;
		}
		catch( SQLException se ){
			System.out.println( "SQL Exception:" ) ;

			// Loop through the SQL Exceptions
			while( se != null ){
				System.out.println( "State  : " + se.getSQLState()  ) ;
				System.out.println( "Message: " + se.getMessage()   ) ;
				System.out.println( "Error  : " + se.getErrorCode() ) ;

				se = se.getNextException() ;
			}
		}
		catch( Exception e ){
			System.out.println( e ) ;
		}
	}

	//LOGIN

	//verifies if there's a tupple in the database that matches the pass and email passed as argument.
	public boolean login(String pass, String email) {
		Hasher hasher = new Hasher(pass);
		String hashed = hasher.getHash();

		try {
			//verifying if it's a customer account
			String query = "SELECT * FROM customers WHERE password = '" + hashed + "' AND email = '" + email + "';";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				String user = rs.getString("fullName");
				int id = rs.getInt("csID");
				new customer.CostController(user, id);
				return true;
			}
			//verifying if it's a barber account
			query = "SELECT * FROM barbers WHERE password = '" + hashed + "' AND email = '" + email + "';";
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				String user = rs.getString("fullName");
				new barber.BarbController(user);
				return true;
			}
			//verifying if it's an admin account
			query = "SELECT * FROM admin WHERE password = '" + hashed + "' AND email = '" + email + "';";
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				new admin.AdmController();
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	//SIGN UP

	//insert a new entry in the customer
	public void newCust(String name, String mobile, String email, String pass) {
		Hasher hasher = new Hasher(pass);
		String hashed = hasher.getHash();

		String query = "INSERT INTO customers (fullName, mobileNumber, email, password) VALUES ('" + name + "', '" + mobile + "', '"
				+ email + "', '" + hashed + "');";
		try {
			stmt.executeUpdate(query);
			JOptionPane.showMessageDialog(new JFrame(), "User Successfully Registered!", "Welcome!",
					JOptionPane.PLAIN_MESSAGE);

			//email being UNIQUE, it will throw an exception if the user tries to register a new account in an registered email
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), "E-mail Already Registered!", "Duplicate User!",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	//Insert a new entry into the barber relation
	public void newBarb(String name, String mobile, String loc, String email, String pass) {


		String query = "INSERT INTO barbers (fullName, mobileNumber, location, email, password, verified) VALUES ('" + 
				name + "', '" + mobile + "', '" + loc + "', '"+ email + "', '" + pass + "' ,'N');";
		try {
			stmt.executeUpdate(query);
			JOptionPane.showMessageDialog(new JFrame(), "Barber Registered! Waiting for Admin verification.", "Welcome!",
					JOptionPane.INFORMATION_MESSAGE);

			//email being UNIQUE, it will throw an exeption if the user tries to register a new account in an registered email	
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), "E-mail Already Registered!", "Duplicate User!",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	//CUSTOMER
	//gets list of registered barbers accepted by adm to populate new appointment JComboBox
	public String[] getBarbersList() {

		String query = "SELECT fullName FROM barbers ORDER BY fullName;";
		ArrayList<String> barbList = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				barbList.add(rs.getString("fullName"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] barbers = barbList.toArray(new String[0]);
		return barbers;
	}

	//return an array of strings with the location of all the barbers in the DB
	public String[] getLocationList() {
		String query = "SELECT location FROM barbers ORDER BY location;";
		ArrayList<String> locList = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				locList.add(rs.getString("location"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] locations = locList.toArray(new String[0]);
		return locations;
	}

	//getting the id of a barber using its name if flag is true or its location if flag is false
	public int gettingBarberId(String barber, boolean flag) {

		int barbId = 0;

		if(flag) {
			String query = "SELECT barbID FROM barbers WHERE fullName = '" + barber + "';";
			try {
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()) {	
					barbId = rs.getInt("barbID");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return barbId;

		}else {
			String query = "SELECT barbID FROM barbers WHERE location = '" + barber + "';";
			try {
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()) {	
					barbId = rs.getInt("barbID");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return barbId;	
		}
	}

	//creates a new tupple in the appointment relationship using the data from the CostView
	public void newApt(int userId, int barbID, String date, String hour) {
		String query = "INSERT INTO appointments(csID, barbID, date, hour, approved)"
				+ " VALUES ('" + userId + "', '" + barbID + "', '" + date + "', '" + hour + "', 'N');";
		try {
			stmt.executeUpdate(query);
			JOptionPane.showMessageDialog(new JFrame(), "Appointment booked! Waiting for barbers confirmation", "Booking Complete",
					JOptionPane.PLAIN_MESSAGE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
