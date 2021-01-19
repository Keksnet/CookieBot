package de.neo.cookiebot.commands.server.gadget;

import de.neo.cookiebot.commands.server.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * GadgetCommand.
 * Command: !cookie
 * 
 * @author Neo8
 * @version 1.0
 */
public class COMMAND_Cookie implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel c, Message msg) {
        c.sendMessage(":cookie:").queue();
        msg.delete().queue();
    }
}
