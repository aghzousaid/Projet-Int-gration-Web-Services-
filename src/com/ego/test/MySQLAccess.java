package com.ego.test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class allow to make the connection to a database and to execute a query
 * @author sarah
 *
 */
public class MySQLAccess {
	private final static String addressDB="jdbc:mysql://localhost/dblp?" + "user=root&password=";
	//private final static String addressDB="jdbc:mysql://localhost/phpmyadmin"  + "user=root&password=";

	private Connection connect = null;
	private Statement statement = null;
	
	// Constructor
	public MySQLAccess() throws Exception
	{
		try {
			
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");

			// Setup the connection with the DB
			connect = DriverManager
					.getConnection(addressDB);

		}
		catch (Exception e) {
			throw e;
		} 
		finally {
			//close();			
		}
		
	}
	
	
	/**
	 * @param query the query to execute
	 * @return the result of the query
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String query) throws SQLException{
		
		ResultSet resultSet=null;
		try {
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			
			// execute the query
			resultSet=statement.executeQuery(query);
			
			// return query result
		    return  resultSet;
		
		}
		catch (Exception e) {
			throw e;
		} 
		finally {
			//if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
		}
		
	}
	
	/**
	 *  To close the resultSet
	 */
	public void close() {
		try {
			if (connect != null) {
				connect.close();
			}					
		} 
		catch (Exception e) {
			}
	}
	
	public void closeStatement() {
		if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	}	
		
	
}
