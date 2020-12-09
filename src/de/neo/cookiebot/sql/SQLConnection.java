package de.neo.cookiebot.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
	
	public static String getPath() {
		String path = "";
		path = SQLConnection.class.getProtectionDomain().getCodeSource().getLocation().getPath().replaceAll("%20", " ");
		if(path.endsWith(".jar")) {
			path = path.substring(0, path.lastIndexOf("/"));
		}
		return path;
	}
	
	public static void connect() {
		try {
			Connection con = null;
			String url = "jdbc:sqlite:config.db";
			con = DriverManager.getConnection(url);
			System.out.println("Verbindung hergestellt!");
			if(con != null) {
				con.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
