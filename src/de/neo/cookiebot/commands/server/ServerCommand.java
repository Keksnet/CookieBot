package de.neo.cookiebot.commands.server;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * Interface f&uuml;r einen ServerCommand.
 * 
 * @author Neo8
 * @version 1.0
 */
public interface ServerCommand {
	
	/**
	 * Command wird ausgef&uuml;hrt.
	 * 
	 * @param m Member, welcher den Command ausgef&uuml;hrt hat.
	 * @param c TextChannel, in dem der Command ausgef&uuml;hrt wurde.
	 * @param msg Message, in der der Command steht.
	 */
    public abstract void performCommand(Member m, TextChannel c, Message msg);
}
