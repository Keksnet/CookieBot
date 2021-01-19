package de.neo.cookiebot.commands.server.debug;

import java.awt.Color;
import java.sql.SQLException;

import de.neo.cookiebot.Main;
import de.neo.cookiebot.commands.server.ServerCommand;
import de.neo.cookiebot.sql.ID_SQL;
import de.neo.cookiebot.util.Embed;
import de.neo.cookiebot.util.Logger;
import de.neo.cookiebot.vars.VarType;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * DebugCommand.
 * Command: !sync
 * 
 * @author Neo8
 * @version 1.0
 */
public class COMMAND_Sync implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel c, Message msg) {
		if(m.hasPermission(Permission.ADMINISTRATOR) || m.getRoles().contains(m.getGuild().getRoleById(Main.vars.get(m.getGuild().getId()).get(VarType.TECHNIK)))) {
			c.sendMessage(new Embed("Sync", ":gear: Der Sync wurde gestartet. :gear:", Color.cyan).build()).queue();
			try {
				ID_SQL.sync(Main.vars.get(m.getGuild().getId()).getVars(), m.getGuild().getId());
				c.sendMessage(new Embed("Sync", ":white_check_mark: Der Sync wurde erfolgreich ausgeführt. :white_check_mark:", Color.green).build()).queue();
			} catch (SQLException e) {
				Logger l = new Logger(m.getGuild());
				c.sendMessage(new Embed("Sync", ":x: Der Sync konnte nicht ausgeführt werden. :x:\nLogID: " + l.getLogID(), Color.red).build()).queue();
				l.log("[LOG ANFANG]");
				l.log("Sync konnte nicht ausgeführt werden: " + e.getMessage());
				for(StackTraceElement s : e.getStackTrace()) {
					l.log(s.toString());
				}
				l.log("[LOG ENDE]");
				l.save();
			}
		}
	}
}
