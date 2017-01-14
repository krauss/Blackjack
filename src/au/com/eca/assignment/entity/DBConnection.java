package au.com.eca.assignment.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	private static Connection conn;
	private static Statement statement;
	private static ResultSet result;
	
	
	public DBConnection(){
		
		try {
			
			conn = DriverManager.getConnection("jdbc:mysql://172.17.0.2:3306/", "root", "mamicamole");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	

	public Player getAuthentication(String user, char[] pass) throws SQLException {
		Player p = null;
		String password = String.copyValueOf(pass);
		
		statement = conn.createStatement();
		
		statement.executeQuery("use BlackJack;");
		result = statement.executeQuery("select * from Login where username = '" + user + "';");

		if (result.next()) {
			if (result.getString("password").equalsIgnoreCase(password)) {
				p = new Player(result.getString("username"));
				p.setScore(result.getInt("score"));	
				p.setName(result.getString("name"));
			}
		}

		conn.close();
		return p;
		
	}
	
	//Increase the user's score to plus 100
	public void setPlayerScore(Player p){
		
		try {
			
			statement = conn.createStatement();
			statement.executeQuery("use BlackJack;");
			statement.executeUpdate("update Login set score = score + 100 where username = '" + p.getUserName() + "';");
			statement.executeUpdate("commit;");
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
	}

	public static Connection getConn() {
		return conn;
	}

	public static Statement getStatement() {
		return statement;
	}

}
