package de.neo.cookiebot.config;

import java.io.File;

import de.neo.cookiebot.sql.SQL;

/**
 * K&uuml;mmert sich um die Config.
 * 
 * @author Neo8
 * @version 1.0
 */
public class Config {
	private SQL sql;
	
	/**
	 * Neue Instanz der Config.
	 */
	public Config() {
		new File("config.db");
		this.sql = new SQL();
		this.createSQL();
	}
	
	/**
	 * Erstellt die Config Datenbank.
	 */
	public void createSQL() {
		this.sql.createDB();
	}
	
	/**
	 * Gibt die SQLVerbindung zur&uuml;ck.
	 * 
	 * @return SQLVerbindung.
	 */
	public SQL getSQL() {
		return this.sql;
	}
}
