package de.neo.cookiebot.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.neo.cookiebot.Main;

public class License_SQL {
	
	public static Long getLastSuccess(String guildid) {
		try {
			PreparedStatement st = Main.conf.getSQL().con.prepareStatement("SELECT * FROM licenses WHERE guild = ?");
			st.setString(1, guildid);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				return rs.getLong("lastSuccess");
			}
		}catch(SQLException e) {
		}
		return 0l;
	}
	
	public static void setSuccess(String guildid) {
		try {
			PreparedStatement st = Main.conf.getSQL().con.prepareStatement("UPDATE licenses SET lastSuccess = ? WHERE guild = ?");
			st.setLong(1, (System.currentTimeMillis() / 1000));
			st.setString(2, guildid);
			st.execute();
		}catch(SQLException e) {
		}
	}
	
	public static void setLicense(String guildid, String license) {
		try {
			PreparedStatement st = Main.conf.getSQL().con.prepareStatement("INSERT INTO licenses (guild, key, lastSuccess) VALUES (?, ?, ?)");
			st.setString(1, guildid);
			st.setString(2, license);
			st.setLong(3, (System.currentTimeMillis() / 1000));
			st.execute();
		}catch(SQLException e) {
		}
	}
}
