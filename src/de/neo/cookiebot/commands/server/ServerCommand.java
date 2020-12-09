package de.neo.cookiebot.commands.server;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public interface ServerCommand {

    public abstract void performCommand(Member m, TextChannel c, Message msg);
}
