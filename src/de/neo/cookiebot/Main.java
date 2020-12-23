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
import de.neo.cookiebot.listener.GuildJoin;
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
	
	public static void main(String[] args) throws InterruptedException, LoginException, SQLException {
		new Main();
	}
	
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
		builder.addEventListeners(new GuildJoin());
		jda = builder.build();
		jda.awaitReady();
		manager = jda;
		System.out.println("Online!");
		setToken();
		this.start();
	}
	
	public void start() {
		for(Guild g : this.manager.getGuilds()) {
			VarManager vars = new VarManager();
			for(Entry<VarType, String> set : ID_SQL.getAll(g.getId()).entrySet()) {
				vars.add(set.getKey(), set.getValue());
			}
			if(vars.contains(VarType.LICENSE_KEY)){
				if(this.checkKey(vars.get(VarType.LICENSE_KEY), g.getId())){
					License_SQL.setLicense(g.getId(), vars.get(VarType.LICENSE_KEY));
					System.out.println(g.getName() + " hat einen gültigen Lizenschlüssel.");
				}else {
					License_SQL.setLicense(g.getId(), "AAAA-BBBB-CCCC-DDDD");
					try{
						g.getOwner().getUser().openPrivateChannel().complete().sendMessage(new ErrorReporter("Bitte hole dir für " + g.getName() + " auf https://www.neo8.de/cookiebot/license.php eine Lizenzschlüssel und löse diesen im !setup ein.", true).build()).complete();
					}catch (ErrorResponseException ignore){
					}
				}
			}else {
				License_SQL.setLicense(g.getId(), "AAAA-BBBB-CCCC-DDDD");
				try{
					g.getOwner().getUser().openPrivateChannel().complete().sendMessage(new ErrorReporter("Bitte hole dir für " + g.getName() + " auf https://www.neo8.de/cookiebot/license.php eine Lizenzschlüssel und löse diesen im !setup ein.", true).build()).complete();
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
	
	public void add(Guild g) {
		conf.getSQL().openConnection();
		VarManager vars = new VarManager();
		for(Entry<VarType, String> set : ID_SQL.getAll(g.getId()).entrySet()) {
			vars.add(set.getKey(), set.getValue());
		}
		if(vars.contains(VarType.LICENSE_KEY)){
			if(this.checkKey(vars.get(VarType.LICENSE_KEY), g.getId())){
				License_SQL.setLicense(g.getId(), vars.get(VarType.LICENSE_KEY));
				System.out.println(g.getName() + " hat einen gültigen Lizenschlüssel.");
			}else {
				License_SQL.setLicense(g.getId(), "AAAA-BBBB-CCCC-DDDD");
				try{
					g.getOwner().getUser().openPrivateChannel().complete().sendMessage(new ErrorReporter("Bitte hole dir für " + g.getName() + " auf https://www.neo8.de/cookiebot/license.php eine Lizenzschlüssel und löse diesen im !setup ein.", true).build()).complete();
				}catch (ErrorResponseException ignore){
				}
			}
		}else {
			License_SQL.setLicense(g.getId(), "AAAA-BBBB-CCCC-DDDD");
			try{
				g.getOwner().getUser().openPrivateChannel().complete().sendMessage(new ErrorReporter("Bitte hole dir für " + g.getName() + " auf https://www.neo8.de/cookiebot/license.php eine Lizenzschlüssel und löse diesen im !setup ein.", true).build()).complete();
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
	
	private void initTables() throws SQLException {
		PreparedStatement st = conf.getSQL().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS config (guild varchar(255), namespace varchar(255), val varchar(255))");
		st.execute();
		st = conf.getSQL().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS enviroment (guild varchar(255), namespace varchar(255), val varchar(255))");
		st.execute();
		st = conf.getSQL().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS licenses (guild varchar(255), key varchar(255), lastSuccess varchar(255))");
		st.execute();
	}
	
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

	public Boolean checkKey(String key, String id) {
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
	}

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
