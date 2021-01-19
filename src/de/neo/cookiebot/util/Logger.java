package de.neo.cookiebot.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import de.neo.cookiebot.Main;
import de.neo.cookiebot.vars.VarType;
import net.dv8tion.jda.api.entities.Guild;

/**
 * Logger f&uuml;r Fehler
 * 
 * @author Neo8
 * @version 1.0
 */
public class Logger {
	
	private File f;
	private String log;
	private String logID;
	
	/**
	 * Neuer Logger
	 * 
	 * @param g Server, f&uuml;r den geloggt werden soll.
	 */
	public Logger(Guild g) {
		File d = new File("logs/");
		if(!d.exists()) {
			d.mkdir();
		}
		this.logID = this.genKey();
		String filename = g.getId() + "-" + logID + ".log";
		this.f = new File("logs/" + filename);
		if(this.f.isFile()) {
			if(!this.f.exists()) {
				try {
					this.f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		this.log = "CookieBot Logging System          (c) Neo8 2021          " + String.valueOf(new SimpleDateFormat("dd.MM.yyyy - hh:mm:ss").format(new Date())) + "\n";
		this.log += "Guild-Name: " + g.getName() + "          Guild-ID: " + g.getId() + "          Guild-Owner: " + g.getOwner().getUser().getId() + "\n";
		this.log += "Lizenzschl&uuml;ssel: " + Main.vars.get(g.getId()).get(VarType.LICENSE_KEY) + "          Hosting: " + Main.INSTANCE.isSelfHosted() + "\n";
	}
	
	/**
	 * Neuen Logeintrag hinzuf&uuml;gen.
	 * 
	 * @param text Neuer Logeintrag.
	 */
	public void log(String text) {
		this.log += "[" + String.valueOf(new SimpleDateFormat("dd.MM.yyyy - hh:mm:ss").format(new Date())) + "] " + text + "\n";
	}
	
	/**
	 * Log als Datei speichern.
	 */
	public void save() {
		if(this.f.exists()) {
			PrintWriter pw;
			try {
				pw = new PrintWriter(new BufferedWriter(new FileWriter(this.f)));
				pw.print(this.log);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			try {
				if(f.createNewFile()) {
					PrintWriter pw;
					try {
						pw = new PrintWriter(new BufferedWriter(new FileWriter(this.f)));
						pw.print(this.log);
						pw.flush();
						pw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Gibt die LogID zur&uuml;ck.
	 * 
	 * @return LogID.
	 */
	public String getLogID() {
		return this.logID;
	}
	
	/**
	 * Generiert die LogID.
	 * 
	 * @return LogID.
	 */
	private String genKey() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		String key = "";
		for(int i = 0; i < 16; i++) {
			key += chars.charAt(ThreadLocalRandom.current().nextInt(0, 62));
		}
		return key;
	}
}
