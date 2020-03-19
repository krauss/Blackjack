package org.krauss.game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.krauss.entity.Player;

/**
 * 
 * @author jrkrauss
 *
 *         Class responsible for the database connection, user authentication
 *         and update scores
 *
 */
public class DatabaseHandler {

	private Connection conn;
	private Statement statement;
	private ResultSet result;

	public DatabaseHandler() {

		try {

			conn = DriverManager.getConnection("jdbc:sqlite:res/database/blackjack.db");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Player authenticateAdmin(String user, char[] pass) throws SQLException {
		Player p = null;
		String password = String.copyValueOf(pass);

		statement = conn.createStatement();

		result = statement.executeQuery("select * from Login where username = '" + user + "';");

		if (result.next()) {
			if (result.getString("password").equalsIgnoreCase(password)) {
				p = new Player(result.getString("username"));
				p.setScore(result.getInt("score"));
				updateLastLogin(statement, p);
			}
		}

		conn.close();
		return p;

	}

	public Player authenticate(String user, char[] pass) throws SQLException {
		Player p = null;
		String password = String.copyValueOf(pass);

		statement = conn.createStatement();

		result = statement.executeQuery("select * from Login where username = '" + user + "';");

		if (result.next()) {
			if (result.getString("password").equalsIgnoreCase(password.hashCode() + "")) {
				p = new Player(result.getString("username"));
				p.setScore(result.getInt("score"));
				updateLastLogin(statement, p);
			}
		}

		conn.close();
		return p;

	}

	private void updateLastLogin(Statement statement, Player user) {

		try {
			statement.executeUpdate("update Login set lastLogin = '" + getDate() + "' where username = '" + user.getUserName() + "';");
			user.setLastLogin(getDate());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void refreshPlayerData(Player p) {
		try {
			statement = conn.createStatement();
			result = statement.executeQuery("select * from Login where username = '" + p.getUserName() + "';");

			if (result.next()) {
				p.setScore(result.getInt("score"));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Increase the user's score to plus 1000
	public void setPlayerScore(Player p) {

		try {

			statement = conn.createStatement();
			statement.executeUpdate("update Login set score = score + 1000 where username = '" + p.getUserName() + "';");

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public boolean checkExistingUser(String userName) {
		boolean result = false;

		try {

			statement = conn.createStatement();
			ResultSet r = statement.executeQuery("Select username from Login where username = '" + userName + "';");

			if (r.next()) {
				if (r.getString("username").equalsIgnoreCase(userName)) {
					result = true;
				}
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public void insertNewUser(Player p, char[] pass) {

		String userPass = String.copyValueOf(pass);
		int nextValidId = 0;

		try {

			statement = conn.createStatement();

			nextValidId = statement.executeQuery("select max(idLogin)+2 as maxId from Login;").getInt("maxId");

			statement = conn.createStatement();

			statement.execute("insert into Login values ('" + nextValidId + "', '" + p.getUserName() + "', '"
					+ userPass.hashCode() + "', '" + p.getUserName() + "', 0, '" + getDate() + "');");

			// Update the lastLogin field on the Player object
			p.setLastLogin(getDate());

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private String getDate() {
		LocalDateTime now = LocalDateTime.now();
		String date = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm"));
		return date;
	}

	public ArrayList<String> getDatabaseData() {

		ArrayList<String> result = new ArrayList<String>();

		try {

			statement = conn.createStatement();
			ResultSet r = statement.executeQuery("Select username, score, lastlogin from Login;");

			while (r.next()) {

				result.add(r.getString("username") + "," + r.getInt("score") + "," + r.getString("lastLogin"));

			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

}
