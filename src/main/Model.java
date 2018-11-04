//Class responsible of the communication of all controllers with the database.
//All queries will be done through this class

package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

	//verifies if there's a tupple in the database that matches the pass and email passed as argument.
	public boolean login(String pass, String email) {
		try {
			//verifying if it's a customer account
			String query = "SELECT * FROM customers WHERE password = '" + pass + "' AND email = '" + email + "';";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				new customer.CostController();
				return true;
			}
			//verifying if it's a barber account
			query = "SELECT * FROM barbers WHERE password = '" + pass + "' AND email = '" + email + "';";
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				new customer.CostController();
				return true;
			}
			//verifying if it's an admin account
			query = "SELECT * FROM admin WHERE password = '" + pass + "' AND email = '" + email + "';";
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				new customer.CostController();
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

}
