package de.neo.cookiebot.game;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public interface Game {

    public abstract GameInfo getInfo();

    public abstract TextChannel getTextChannel();

    public abstract Boolean canInteract(Member m);

    public abstract void start();

    public abstract void interact(Message msg);

    public abstract void stop();

    public abstract void win(Member m) throws IllegalAccessException;

    public abstract void loose(Member m) throws IllegalAccessException;

    public abstract void restart() throws IllegalAccessException;
}
