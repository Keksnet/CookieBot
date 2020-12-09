package de.neo.cookiebot.commands.server.gadget;

import de.neo.cookiebot.commands.server.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class COMMAND_Cookie implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel c, Message msg) {
        c.sendMessage(":cookie:").queue();
        msg.delete().queue();
    }
}
