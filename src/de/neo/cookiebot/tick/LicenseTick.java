package de.neo.cookiebot.tick;

import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import de.neo.cookiebot.Main;
import de.neo.cookiebot.sql.License_SQL;
import de.neo.cookiebot.util.Embed;
import de.neo.cookiebot.vars.VarType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

/**
 * Regelmäßiges Prüfen der Lizenz.
 * 
 * @author Neo8
 * @version 1.0
 */
public class LicenseTick implements Tick {
	
	final int time;
	boolean ticking;
	
	/**
	 * Neuer LizenzTick.
	 * 
	 * @param min Anzahl der Minuten, die zwischen den Ticks gewartet werden soll.
	 */
	public LicenseTick(int min) {
		this.time = (min * 60) * 1000;
		this.ticking = true;
	}

	@Override
	public void run() {
		ExecutorService threadpool = Executors.newCachedThreadPool();
		threadpool.submit(new Runnable() {
			@Override
			public void run() {
				try {
					tick();
					Thread.sleep(time);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void tick() {
		System.out.println("LicenseTick!");
		Main.conf.getSQL().openConnection();
		for(Guild g : Main.INSTANCE.manager.getGuilds()) {
			System.out.println("Prüfe " + g.getName());
			if(Main.INSTANCE.checkKey(Main.vars.get(g.getId()).get(VarType.LICENSE_KEY), g.getId())) {
				System.out.println(g.getName() + " hat eine gültige Lizenz!");
				License_SQL.setSuccess(g.getId());
			}else {
				System.out.println(g.getName() + " hat keine Lizenz!");
				Long lastSuccess = License_SQL.getLastSuccess(g.getId());
				if(((System.currentTimeMillis() / 1000) - lastSuccess) >= 259200L) {
					try {
						g.getOwner().getUser().openPrivateChannel().complete().sendMessage(new Embed("Es wurde keine Lizenz aktiviert.", "Da seit mehr als drei Tagen keine Lizenz aktiviert wurde, wurde der Bot nun von deinem Server entfernt. Du kannst ihn erneut hinzufügen und dann eine Lizenz aktivieren.", Color.red).build()).complete();
					}catch(ErrorResponseException e) {
					}
					g.leave().queue();
				}else if(((System.currentTimeMillis() / 1000) - lastSuccess) >= 172800L) {
					try {
						g.getOwner().getUser().openPrivateChannel().complete().sendMessage(new Embed("Es wurde keine Lizenz aktiviert.", "Bitte aktiviere innerhalb von 24 Stunden eine gültige Lizenz oder der Bot muss von deinem Server entfernt werden.", Color.red).build()).complete();
					}catch(ErrorResponseException e) {
					}
				}else if(((System.currentTimeMillis() / 1000) - lastSuccess) >= 86400L) {
					try {
						g.getOwner().getUser().openPrivateChannel().complete().sendMessage(new Embed("Es wurde keine Lizenz aktiviert.", "Bitte aktiviere innerhalb von 48 Stunden eine gültige Lizenz oder der Bot muss von deinem Server entfernt werden.", Color.red).build()).complete();
					}catch(ErrorResponseException e) {
					}
				}else {
					try {
						g.getOwner().getUser().openPrivateChannel().complete().sendMessage(new Embed("Es wurde keine Lizenz aktiviert.", "Bitte aktiviere eine gültige Lizenz oder der Bot muss von deinem Server entfernt werden.", Color.red).build()).complete();
					}catch(ErrorResponseException e) {
					}
				}
			}
			System.out.println(g.getName() + " wurde geprüft.");
		}
		Main.conf.getSQL().closeConnection();
	}

}
