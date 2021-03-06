//Class responsible of the communication of all controllers with the database.
//All queries will be done through this class

package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Model {

	private Statement stmt;
	private Connection conn;

	public Model(){

		try{
			// Load the database driver
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance() ;

			String dbServer = "jdbc:mysql://localhost:3306/barberSystem?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			String user = "root";
			String password = "";


			// Get a connection to the database
			Connection conn = DriverManager.getConnection(dbServer, user, password) ;
			this.conn = conn;

			// Get a statement from the connection
			this.stmt = conn.createStatement() ;
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
		PreparedStatement stmt = null;

		try {
			//verifying if it's a customer account
			String query = "SELECT * FROM customers WHERE password = ? AND email = ?;";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, hashed);
			stmt.setString(2, email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String user = rs.getString("firstName");
				int id = rs.getInt("csID");
				new customer.CostController(user, id);
				return true;
			} else {
				//verifying if it's a barber account
				query = "SELECT * FROM barbers WHERE password = ? AND email = ?;";
				stmt = conn.prepareStatement(query);
				stmt.setString(1, hashed);
				stmt.setString(2, email);
				rs = stmt.executeQuery();
				if (rs.next()) {
					String approved = rs.getString("verified");
					if (approved.equals("N")) {
						JOptionPane.showMessageDialog(new JFrame(), "Account Waiting Administrator Verification.", "Unverified Account",
								JOptionPane.ERROR_MESSAGE);
						return false;
					}
					String user = rs.getString("fullName");
					int id = rs.getInt("barbID");

					new barber.BarbController(user, id);
					return true;
				} else {
					//verifying if it's an admin account
					query = "SELECT * FROM admins WHERE password = ? AND email = ?;";
					stmt = conn.prepareStatement(query);
					stmt.setString(1, hashed);
					stmt.setString(2, email);
					rs = stmt.executeQuery();
					if (rs.next()) {
						new admin.AdmController();
						return true;
					} else {
						return false;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	//SIGN UP

	//insert a new entry in the customer
	public void newCust(String fName, String lName, String mobile, String email, String pass) {
		Hasher hasher = new Hasher(pass);
		String hashed = hasher.getHash();

		PreparedStatement stmt = null;
		try {

			stmt = conn.prepareStatement("INSERT INTO customers (firstName, lastName, mobileNumber, email, password)"
					+ " VALUES (?,?,?,?,?)");
			stmt.setString(1, fName);
			stmt.setString(2, lName);
			stmt.setString(3, mobile);
			stmt.setString(4, email);
			stmt.setString(5, hashed);
			stmt.executeUpdate();
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

		Hasher hasher = new Hasher(pass);
		String hashed = hasher.getHash();

		PreparedStatement stmt = null;
		try {

			stmt = conn.prepareStatement("INSERT INTO barbers (fullName, phoneNumber, location, email, password, verified)"
					+ " VALUES (?,?,?,?,?,?)");
			stmt.setString(1, name);
			stmt.setString(2, mobile);
			stmt.setString(3, loc);
			stmt.setString(4, email);
			stmt.setString(5, hashed);
			stmt.setString(6, "N");
			stmt.executeUpdate();

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

		String query = "SELECT fullName FROM barbers WHERE verified = 'Y' ORDER BY fullName;";
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

	//return an array of strings with the location of all the barbers in the DB to populate new appointment JComboBox
	public String[] getLocationList() {
		String query = "SELECT location FROM barbers WHERE verified = 'Y' ORDER BY location;";
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
	public void newApt(int userID, int barbID, String date, String hour) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("INSERT INTO appointments (csID, barbID, date, hour, approved) VALUES (?, ?, ?, ?, ?)");
			stmt.setInt(1, userID);
			stmt.setInt(2, barbID);
			stmt.setString(3, date);
			stmt.setString(4, hour);
			stmt.setString(5, "N");
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		String query = "INSERT INTO appointments(csID, barbID, date, hour, approved)"
		//				+ " VALUES ('" + userId + "', '" + barbID + "', '" + date + "', '" + hour + "', 'N');";
		//		try {
		//			stmt.executeUpdate(query);
		JOptionPane.showMessageDialog(new JFrame(), "Appointment booked! Waiting for barbers confirmation", "Booking Complete",
				JOptionPane.PLAIN_MESSAGE);

	}

	//Inserts a new entry in the admin table in the database
	public void newAdmin(String email, String pass) {

		Hasher hasher = new Hasher(pass);
		String hashed = hasher.getHash();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("INSERT INTO admins (email, password) VALUES (?, ?)");
			stmt.setString(1, email);
			stmt.setString(2, hashed);
			stmt.executeUpdate();
			JOptionPane.showMessageDialog(new JFrame(), "Administrator Account Created.", "Welcome!",
					JOptionPane.INFORMATION_MESSAGE);

			//email being UNIQUE, it will throw an exception if the user tries to register a new account in an registered email	
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), "E-mail Already Registered!", "Duplicate User!",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	//returns array of string with unverified barbers in the database
	public String[] getUnvBarbersList() {

		String query = "SELECT fullName FROM barbers WHERE verified = 'N' ORDER BY fullName;";
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

	//changes barbers' status to "verified" after administrator approval
	public void verify(String selectedBarb) {
		PreparedStatement stmt = null;
		String query = "UPDATE barbers SET verified = 'Y' WHERE fullName = ?;";
		try {
			stmt = conn.prepareStatement(query);
			stmt.setString(1, selectedBarb);
			stmt.execute();
			JOptionPane.showMessageDialog(new JFrame(), "Barber Approved!.", "Confirmation Screen",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//retrieves all apts of a barber using the ID, gets "approved" if boolean is true or gets "review" if boolean is false.
	public Object[][] gettingAppData(int id, boolean check) {

		//Hard-coded, not ideal
		Object[][] data = new Object[100][6]; 
		int row = 0;

		if (check) {
			PreparedStatement stmt = null;
			String query = "SELECT appointments.apID, customers.firstName, customers.lastName, customers.mobileNumber, "
					+ "appointments.date, appointments.hour, appointments.approved FROM appointments INNER JOIN customers "
					+ "ON appointments.csID = customers.csID WHERE appointments.barbID = ? AND appointments.approved = 'N'";

			try {
				stmt = conn.prepareStatement(query);
				stmt.setInt(1, id);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					int apID = rs.getInt("apID");
					String name = rs.getString("firstName") + " " + rs.getString("lastName");
					String number = rs.getString("mobileNumber");
					String date = rs.getString("date");
					String hour = rs.getString("hour");
					data[row][0] = apID;
					data[row][1] = name;
					data[row][2] = number;
					data[row][3] = date;
					data[row][4] = hour;
					data[row][5] = false;
					row++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return data;
		} else {
			PreparedStatement stmt = null;
			String query = "SELECT appointments.apID, customers.firstName, customers.lastName, customers.mobileNumber, "
					+ "appointments.date, appointments.hour, appointments.review FROM appointments INNER JOIN customers "
					+ "ON appointments.csID = customers.csID WHERE appointments.barbID = ? AND appointments.approved = 'Y'";

			try {
				stmt = conn.prepareStatement(query);
				stmt.setInt(1, id);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					int apID = rs.getInt("apID");
					String name = rs.getString("firstName") + " " + rs.getString("lastName");
					String number = rs.getString("mobileNumber");
					String date = rs.getString("date");
					String hour = rs.getString("hour");
					String review = rs.getString("review");
					data[row][0] = apID;
					data[row][1] = name;
					data[row][2] = number;
					data[row][3] = date;
					data[row][4] = hour;
					data[row][5] = review;
					row++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return data;
		}
	}

	//gets an array with the Appointments ID from the barber controller and changes it's "Approved" status to "Y" in the database
	public void approveAppointments(ArrayList<?> appIDs) {

		PreparedStatement stmt = null;
		String query = "UPDATE appointments SET approved = 'Y' WHERE apID = ?;";
		try {
			stmt = conn.prepareStatement(query);

			for(int x = 0; x < appIDs.size(); x++) {
				stmt.setInt(1, (int) appIDs.get(x));
				stmt.execute();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(new JFrame(), "Appointments Approved!.", "Confirmation Screen",
				JOptionPane.INFORMATION_MESSAGE);

	}

	//returns and object[][] with the appointments of the user which ID is being passed as arg
	public Object[][] gettingCustomerAppointments(int userId) {

		//Hard-coded, not ideal
		Object[][] data = new Object[100][6]; 
		int row = 0;

		PreparedStatement stmt = null;
		String query = "SELECT appointments.apID, barbers.fullName, barbers.phoneNumber, appointments.date, appointments.hour, appointments.review "
				+ "FROM appointments INNER JOIN barbers ON appointments.barbID = barbers.barbID "
				+ "WHERE appointments.csID = ?;";

		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int apID = rs.getInt("apID");
				String name = rs.getString("fullName");
				String number = rs.getString("phoneNumber");
				String date = rs.getString("date");
				String hour = rs.getString("hour");
				String review = rs.getString("review");
				data[row][0] = apID;
				data[row][1] = name;
				data[row][2] = number;
				data[row][3] = date;
				data[row][4] = hour;
				data[row][5] = review;
				row++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public void updatingReviews(ArrayList<?> reviewsList) {
		PreparedStatement stmt = null;
		String query = "UPDATE appointments SET review = ? WHERE apID = ?;";
		try {
			stmt = conn.prepareStatement(query);

			for(int x = 0; x < reviewsList.size(); x += 2) {
				stmt.setString(1, (String) reviewsList.get(x));
				stmt.setInt(2, (int) reviewsList.get(x + 1));
				stmt.execute();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(new JFrame(), "Reviews Submited!.", "Confirmation Screen",
				JOptionPane.INFORMATION_MESSAGE);		
	}		
}
