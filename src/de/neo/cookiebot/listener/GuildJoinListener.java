package de.neo.cookiebot.listener;

import de.neo.cookiebot.Main;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Event.
 * Ein Nutzer betritt den Server.
 * 
 * @author Neo8
 * @version 1.0
 */
public class GuildJoinListener extends ListenerAdapter {
	
	/**
	 * Event wird bearbeitet.
	 */
	@Override
	public void onGuildJoin(GuildJoinEvent e) {
		if(!Main.vars.containsKey(e.getGuild().getId())) {
			Main.INSTANCE.add(e.getGuild());
		}
	}
}
