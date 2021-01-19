package de.neo.cookiebot.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import de.neo.cookiebot.Main;
import de.neo.cookiebot.vars.VarType;

/**
 * SQL für das Verwalten der Config.
 * 
 * @author Neo8
 * @version 1.0
 * @see de.neo.cookiebot.sql.SQL
 * @see de.neo.cookiebot.sql.SQLConnection
 */
public class ID_SQL {
	
	/**
	 * Gibt die Einstellungen zurück.
	 * 
	 * @param guildId ID des Servers.
	 * @return HashMap mit allen Einstellungen.
	 */
	public static HashMap<VarType, String> getAll(String guildId) {
		HashMap<VarType, String> vars = new HashMap<>();
		try {
			PreparedStatement st = Main.conf.getSQL().getConnection().prepareStatement("SELECT * FROM enviroment WHERE guild = ?");
			st.setString(1, guildId);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				vars.put(VarType.valueOf(rs.getString("namespace").toUpperCase()), rs.getString("val"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return vars;
	}
	
	/**
	 * Speichern einer Einstellung.
	 * 
	 * @param key Name der Einstellung.
	 * @param value Wert der Einstellung.
	 * @param guildId ID des Servers, auf dem die Einstellung gilt.
	 * @throws SQLException Datenbankfehler.
	 */
	public static void set(String key, String value, String guildId) throws SQLException {
		PreparedStatement st = Main.conf.getSQL().getConnection().prepareStatement("INSERT INTO enviroment (guild, namespace, val) VALUES (?, ?, ?)");
		st.setString(1, guildId);
		st.setString(2, key);
		st.setString(3, value);
		st.execute();
	}
	
	/**
	 * Löschen aller Einstellungen.
	 * 
	 * @param key Name der Einstellung.
	 * @param guildId ID des Servers, auf dem die Einstellung gilt.
	 * @throws SQLException Datenbankfehler.
	 */
	public static void delete(String key, String guildId) throws SQLException {
		PreparedStatement st = Main.conf.getSQL().getConnection().prepareStatement("DELETE FROM enviroment WHERE guild = ? AND namespace = ?");
		st.setString(1, guildId);
		st.setString(2, key);
		st.execute();
	}
	
	/**
	 * Speichern aller Einstellungen.
	 * 
	 * @param vars HashMap mit allen Einstellungen.
	 * @param guildId ID des Servers, auf dem die Einstellungen gelten.
	 * @throws SQLException Datenbankfehler.
	 */
	public static void sync(HashMap<VarType, String> vars, String guildId) throws SQLException {
		Main.conf.getSQL().openConnection();
		for(Map.Entry<VarType, String> set : vars.entrySet()) {
			delete(set.getKey().name(), guildId);
			set(set.getKey().name(), set.getValue(), guildId);
		}
		Main.conf.getSQL().closeConnection();
	}
}
