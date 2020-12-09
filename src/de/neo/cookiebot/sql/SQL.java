package de.neo.cookiebot.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL {
	
	Connection con;
	String url;
	
	public SQL() {
		this.url = "jdbc:sqlite:config.db";
	}
	
	public void openConnection() {
		try {
			this.con = DriverManager.getConnection(this.url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeConnection() {
		try {
			if(!this.con.isClosed() && this.con != null) {
				this.con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return this.con;
	}
	
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
