package au.com.eca.assignment.game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import au.com.eca.assignment.entity.BJPlayer;

/**
 * 
 * @author jrkrauss
 *
 * Class responsible for the database connection, user authentication and update scores
 *
 */
public class BJDatabaseConn {

	private  Connection conn;
	private  Statement statement;
	private  ResultSet result;
	
	
	public BJDatabaseConn(){
		
		try {
			
			conn = DriverManager.getConnection("jdbc:mysql://172.17.0.2:3306/?useSSL=false", "root", "mamicamole");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	

	public BJPlayer getAuthentication(String user, char[] pass) throws SQLException {
		BJPlayer p = null;
		String password = String.copyValueOf(pass);
		
		statement = conn.createStatement();
		
		statement.executeQuery("use BlackJack;");
		result = statement.executeQuery("select * from Login where username = '" + user + "';");

		if (result.next()) {
			if (result.getString("password").equalsIgnoreCase(password)) {
				p = new BJPlayer(result.getString("username"));
				p.setScore(result.getInt("score"));	
				p.setName(result.getString("name"));
			}
		}

		conn.close();
		return p;
		
	}
	
	//Increase the user's score to plus 100
	public void setPlayerScore(BJPlayer p){
		
		try {
			
			statement = conn.createStatement();
			statement.executeQuery("use BlackJack;");
			statement.executeUpdate("update Login set score = score + 100 where username = '" + p.getUserName() + "';");
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
	}

	public Connection getConn() {
		return conn;
	}

	public Statement getStatement() {
		return statement;
	}

}
