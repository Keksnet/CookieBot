package de.neo.cookiebot.config;

import java.io.File;

import de.neo.cookiebot.sql.SQL;

public class Config {
	private SQL sql;
	
	public Config() {
		new File("config.db");
		this.sql = new SQL();
		this.createSQL();
	}
	
	public void createSQL() {
		this.sql.createDB();
	}
	
	public SQL getSQL() {
		return this.sql;
	}
}
