package de.neo.cookiebot.listener;

import de.neo.cookiebot.Main;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildJoin extends ListenerAdapter {
	
	@Override
	public void onGuildJoin(GuildJoinEvent e) {
		if(!Main.vars.containsKey(e.getGuild().getId())) {
			Main.INSTANCE.add(e.getGuild());
		}
	}
}
