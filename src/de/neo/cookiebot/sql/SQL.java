package de.neo.cookiebot.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Verwaltet SQLVerbindung und &auml;hnliches.
 * 
 * @author Neo8
 * @version 1.0
 */
public class SQL {
	
	Connection con;
	String url;
	
	/**
	 * Neue Datenbankverbindung.
	 */
	public SQL() {
		this.url = "jdbc:sqlite:config.db";
	}
	
	/**
	 * Datenbankverbidung aufbauen.
	 */
	public void openConnection() {
		try {
			if(this.con == null || this.con.isClosed()) {
				this.con = DriverManager.getConnection(this.url);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Datenbankverbindung schlie&szlig;en.
	 */
	public void closeConnection() {
		try {
			if(!this.con.isClosed() && this.con != null) {
				this.con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gibt die {@link java.sql.Connection} zur&uuml;ck.
	 * 
	 * @return Datenbankverbindung.
	 */
	public Connection getConnection() {
		return this.con;
	}
	
	/**
	 * Erstellt die Datenbank.
	 */
	public void createDB() {
		try {
			String url = "jdbc:sqlite:config.db";
			Connection con = DriverManager.getConnection(url);
			if(con != null) {
				DatabaseMetaData meta = con.getMetaData();
				System.out.println("Drivername: " + meta.getDriverName());
				System.out.println("Datenbank erstellt!");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
