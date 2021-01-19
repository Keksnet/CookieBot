package de.neo.cookiebot;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.security.auth.login.LoginException;

import de.neo.cookiebot.commands.CommandManager;
import de.neo.cookiebot.config.Config;
import de.neo.cookiebot.game.GameManager;
import de.neo.cookiebot.listener.GuildJoinListener;
import de.neo.cookiebot.listener.MessageReceivedListener;
import de.neo.cookiebot.setup.Setup;
import de.neo.cookiebot.sql.ID_SQL;
import de.neo.cookiebot.sql.License_SQL;
import de.neo.cookiebot.tick.LicenseTick;
import de.neo.cookiebot.util.ErrorReporter;
import de.neo.cookiebot.vars.VarManager;
import de.neo.cookiebot.vars.VarType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

/**
 * Mainklasse.
 * 
 * @author Neo8
 * @version 1.0
 */
public class Main {
	
	private String TOKEN = "";
	
	public JDA jda;
	public JDA manager;
	public static Main INSTANCE;
	public static Config conf;
	public static GameManager gm;
	public static CommandManager cm;
	public static LicenseTick lt;
	
	public static HashMap<String, VarManager> vars = new HashMap<>();
	public static HashMap<Member, Member> tictactoe_request = new HashMap<>();
	public static Boolean setup = false;
	public static Setup setup_c = null;
	
	/**
	 * Hier startet der Bot.
	 * 
	 * @param args Befehlszeilenargumente.
	 * @throws InterruptedException Fehler beim Start.
	 * @throws LoginException Fehler beim Anmelden mit dem BotToken.
	 * @throws SQLException Fehler bei der Datenbankverbindung.
	 */
	public static void main(String[] args) throws InterruptedException, LoginException, SQLException {
		new Main();
	}
	
	/**
	 * Bot wird gestartet angemeldet und alles wird vorbereitet.
	 * 
	 * @throws LoginException Fehler bei der Anmeldung mit dem BotToken.
	 * @throws InterruptedException Fehler beim Start.
	 * @throws SQLException Fehler bei der Datenbankverbindung.
	 */
	public Main() throws LoginException, InterruptedException, SQLException {
		INSTANCE = this;
		conf = new Config();
		gm = new GameManager();
		cm = new CommandManager();
		
		conf.getSQL().openConnection();
		initTables();
		getToken();
		
		JDABuilder builder = JDABuilder.createDefault(TOKEN);
		builder.setActivity(Activity.watching("www.neo8.de/cookiebot/"));
		builder.enableIntents(EnumSet.allOf(GatewayIntent.class));
		builder.setMemberCachePolicy(MemberCachePolicy.ALL);
		builder.addEventListeners(new MessageReceivedListener());
		builder.addEventListeners(new GuildJoinListener());
		jda = builder.build();
		jda.awaitReady();
		manager = jda;
		System.out.println("Online!");
		setToken();
		this.start();
	}
	
	/**
	 * Initialisierung aller Einstellungen, erstellen aller Accounts und weitere Prozesse.
	 */
	public void start() {
		for(Guild g : this.manager.getGuilds()) {
			VarManager vars = new VarManager();
			for(Entry<VarType, String> set : ID_SQL.getAll(g.getId()).entrySet()) {
				vars.add(set.getKey(), set.getValue());
			}
			if(vars.contains(VarType.LICENSE_KEY)){
				if(this.checkKey(vars.get(VarType.LICENSE_KEY), g.getId())){
					License_SQL.setLicense(g.getId(), vars.get(VarType.LICENSE_KEY));
					System.out.println(g.getName() + " hat einen g&uuml;ltigen Lizenschl&uuml;ssel.");
				}else {
					vars.add(VarType.LICENSE_KEY, "AAAA-BBBB-CCCC-DDDD");
					License_SQL.setLicense(g.getId(), "AAAA-BBBB-CCCC-DDDD");
					try{
						g.getOwner().getUser().openPrivateChannel().complete().sendMessage(new ErrorReporter("Bitte hole dir f&uuml;r " + g.getName() + " auf https://www.neo8.de/cookiebot/license.php eine Lizenzschl&uuml;ssel und l&ouml;se diesen im !setup ein.", true).build()).complete();
					}catch (ErrorResponseException ignore){
					}
				}
			}else {
				vars.add(VarType.LICENSE_KEY, "AAAA-BBBB-CCCC-DDDD");
				License_SQL.setLicense(g.getId(), "AAAA-BBBB-CCCC-DDDD");
				try{
					g.getOwner().getUser().openPrivateChannel().complete().sendMessage(new ErrorReporter("Bitte hole dir f&uuml;r " + g.getName() + " auf https://www.neo8.de/cookiebot/license.php eine Lizenzschl&uuml;ssel und l&ouml;se diesen im !setup ein.", true).build()).complete();
				}catch (ErrorResponseException ignore){
				}
			}
			if(!vars.contains(VarType.PREFIX)) {
				vars.add(VarType.PREFIX, "!");
			}
			try {
				ID_SQL.sync(vars.getVars(), g.getId());
			} catch(SQLException ignore) {
			}
			Main.vars.put(g.getId(), vars);
		}
		conf.getSQL().closeConnection();
		lt = new LicenseTick(120);
		lt.run();
	}
	
	/**
	 * Server wird gepr&uuml;ft.
	 * 
	 * @param g Server, der gepr&uuml;ft wird.
	 */
	public void add(Guild g) {
		conf.getSQL().openConnection();
		VarManager vars = new VarManager();
		for(Entry<VarType, String> set : ID_SQL.getAll(g.getId()).entrySet()) {
			vars.add(set.getKey(), set.getValue());
		}
		if(vars.contains(VarType.LICENSE_KEY)){
			if(this.checkKey(vars.get(VarType.LICENSE_KEY), g.getId())){
				License_SQL.setLicense(g.getId(), vars.get(VarType.LICENSE_KEY));
				System.out.println(g.getName() + " hat einen g&uuml;ltigen Lizenschl&uuml;ssel.");
			}else {
				vars.add(VarType.LICENSE_KEY, "AAAA-BBBB-CCCC-DDDD");
				License_SQL.setLicense(g.getId(), "AAAA-BBBB-CCCC-DDDD");
				try{
					g.getOwner().getUser().openPrivateChannel().complete().sendMessage(new ErrorReporter("Bitte hole dir f&uuml;r " + g.getName() + " auf https://www.neo8.de/cookiebot/license.php eine Lizenzschl&uuml;ssel und l&ouml;se diesen im !setup ein.", true).build()).complete();
				}catch (ErrorResponseException ignore){
				}
			}
		}else {
			vars.add(VarType.LICENSE_KEY, "AAAA-BBBB-CCCC-DDDD");
			License_SQL.setLicense(g.getId(), "AAAA-BBBB-CCCC-DDDD");
			try{
				g.getOwner().getUser().openPrivateChannel().complete().sendMessage(new ErrorReporter("Bitte hole dir f&uuml;r " + g.getName() + " auf https://www.neo8.de/cookiebot/license.php eine Lizenzschl&uuml;ssel und l&ouml;se diesen im !setup ein.", true).build()).complete();
			}catch (ErrorResponseException ignore){
			}
		}
		if(!vars.contains(VarType.PREFIX)) {
			vars.add(VarType.PREFIX, "!");
		}
		try {
			ID_SQL.sync(vars.getVars(), g.getId());
		} catch(SQLException ignore) {
		}
		Main.vars.put(g.getId(), vars);
		conf.getSQL().closeConnection();
	}
	
	/**
	 * Fehler wird meldet.
	 * 
	 * @param g Server, auf dem der Fehler aufgetreten ist.
	 * @param msg Fehlernachricht, die zugestellt werden muss.
	 */
	public void reportError(Guild g, Message msg) {
		if(Boolean.parseBoolean(Main.vars.get(g.getId()).get(VarType.REPORT_ERRORS))) {
			for(Member m : g.getMembersWithRoles(g.getRoleById(Main.vars.get(g.getId()).get(VarType.DEBUG)))) {
				try{
					m.getUser().openPrivateChannel().complete().sendMessage(msg).complete();
				}catch(ErrorResponseException ignore){
					System.out.println("Der Fehlerbericht konnte nicht an " + m.getUser().getAsTag() + " gesendet werden!");
				}
			}
		}
	}
	
	/**
	 * Vorbereiten der SQLTables.
	 * 
	 * @throws SQLException Fehler bei der Datenbank.
	 */
	private void initTables() throws SQLException {
		PreparedStatement st = conf.getSQL().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS config (guild varchar(255), namespace varchar(255), val varchar(255))");
		st.execute();
		st = conf.getSQL().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS enviroment (guild varchar(255), namespace varchar(255), val varchar(255))");
		st.execute();
		st = conf.getSQL().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS licenses (guild varchar(255), key varchar(255), lastSuccess varchar(255))");
		st.execute();
	}
	
	/**
	 * BotToken bekommen.
	 */
	private void getToken() {
		try {
			TOKEN = "";
			if(hasToken()) {
				PreparedStatement st = conf.getSQL().getConnection().prepareStatement("SELECT * FROM config WHERE guild = ? AND namespace = ?");
				st.setString(1, "BOT");
				st.setString(2, "TOKEN");
				ResultSet rs = st.executeQuery();
				if(rs.next()) {
					TOKEN = rs.getString("val");
				}else {
					Scanner scanner = new Scanner(System.in);
					System.out.println("Discord Bot Token:");
					TOKEN = scanner.nextLine();
					scanner.close();
				}
			}else {
				Scanner scanner = new Scanner(System.in);
				System.out.println("Discord Bot Token:");
				TOKEN = scanner.nextLine();
				scanner.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gibt an, ob ein BotToken vorliegt.
	 * 
	 * @return Boolean, ob ein BotToken vorliegt.
	 */
	private Boolean hasToken() {
		try {
			PreparedStatement st = conf.getSQL().getConnection().prepareStatement("SELECT * FROM config WHERE guild = ? AND namespace = ?");
			st.setString(1, "BOT");
			st.setString(2, "TOKEN");
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				System.out.println("Token gefunden!");
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Kein Token gefunden!");
		return false;
	}
	
	/**
	 * Setzt/&Auml;ndert den BotToken.
	 */
	private void setToken() {
		try {
			if(hasToken()) {
				PreparedStatement st = conf.getSQL().getConnection().prepareStatement("UPDATE config SET val = ? WHERE guild = ? AND namespace = ?");
				st.setString(1, TOKEN);
				st.setString(2, "BOT");
				st.setString(3, "TOKEN");
				st.execute();
			}else {
				PreparedStatement st = conf.getSQL().getConnection().prepareStatement("INSERT INTO config (guild, namespace, val) VALUES (?, ?, ?)");
				st.setString(1, "BOT");
				st.setString(2, "TOKEN");
				st.setString(3, TOKEN);
				st.execute();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Pr&uuml;ft den Lizenzschl&uuml;ssel.
	 * 
	 * @param key Lizenzschl&uuml;ssel zum Pr&uuml;fen.
	 * @param id ID des Servers, f&uuml;r den der Lizenzschl&uuml;ssel gepr&uuml;ft wird.
	 * @return Boolean, ob der Lizenzschl&uuml;ssel g&uuml;ltig ist.
	 */
	public Boolean checkKey(String key, String id) {
		if(!key.equals("AAAA-BBBB-CCCC-DDDD")) {
			try{
				String params = "key=" + URLEncoder.encode(key, "UTF-8") + "&guild=" + URLEncoder.encode(id, "UTF-8");
				URL url = new URL("https://www.neo8.de/cookiebot/validate.php");
				HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
				con.setDoOutput(true);
				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				con.setFixedLengthStreamingMode(params.getBytes().length);

				OutputStreamWriter cw = new OutputStreamWriter(con.getOutputStream());
				cw.write(params);
				cw.flush();
				cw.close();

				String response = this.parse(con.getInputStream());
				con.disconnect();
				if(response.toLowerCase().equals("valid")) {
					return true;
				}else {
					return false;
				}
			}catch(IOException ignore) {
				return false;
			}
		}else {
			return false;
		}
	}
	
	/**
	 * Pr&uuml;ft, ob diese Botinstanz selbst gehostet wird.
	 * 
	 * @return Boolean, ob dieses Botinstanz selbst gehostet wird.
	 */
	public Boolean isSelfHosted() {
		try {
			URL url = new URL("https://www.neo8.de/cookiebot/hostingtype.php");
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			
			String response = this.parse(con.getInputStream());
			con.disconnect();
			if(response.toLowerCase().equals("self")) {
				return true;
			}else {
				return false;
			}
		}catch(IOException e) {
		}
		return true;
	}
	
	/**
	 * Antwort des Servers parsen.
	 * 
	 * @param is InputStream der HttpsConnection.
	 * @return Antwort des Servers.
	 */
	private String parse(InputStream is) {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String az;
		try{
			while((az = br.readLine()) != null){
				sb.append(az);
				sb.append("\n");
			}

			br.close();
		}catch(IOException ignore){
		}

		return sb.toString().trim();
	}
}
