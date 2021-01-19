package de.neo.cookiebot.game;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * Interface f�r ein Game.
 * 
 * @author Neo8
 * @version 1.0
 */
public interface Game {
	
	/**
	 * Gibt eine Info �ber den aktuellen Spielstand zur�ck.
	 * 
	 * @return Info �ber den aktuellen Spielstand.
	 */
    public abstract GameInfo getInfo();
    
    /**
     * Gibt den TextChannel, des Spiels zur�ck.
     * 
     * @return TextChannel in dem das Spiel stattfindet.
     */
    public abstract TextChannel getTextChannel();
    
    /**
     * Gibt an, ob ein Member mit dem Spiel interagieren kann.
     * 
     * @param m Member, der versucht zu interagieren.
     * @return Boolean, ob der Member inteagieren kann.
     */
    public abstract Boolean canInteract(Member m);
    
    /**
     * Startet das Spiel.
     */
    public abstract void start();
    
    /**
     * L�sst eine Interaction mit dem Spiel zu.
     * 
     * @param msg Message, die mit dem Spiel interagiert.
     */
    public abstract void interact(Message msg);
    
    /**
     * Stoppt das Spiel.
     */
    public abstract void stop();
    
    /**
     * L�sst einen Spieler gewinnen.
     * 
     * @param m Member, der gewinnt.
     * @throws IllegalAccessException Manche Spiele sind unendlich und k�nnen nicht gewonnen werden.
     */
    public abstract void win(Member m) throws IllegalAccessException;
    
    /**
     * L�sst einen Spieler verlieren.
     * 
     * @param m Member, der verliert.
     * @throws IllegalAccessException Manche Spiele sind unendlich und k�nnen nicht verloren werden.
     */
    public abstract void loose(Member m) throws IllegalAccessException;
    
    /**
     * Startet ein Spiel neu.
     * 
     * @throws IllegalAccessException Manche Spiele m�ssen manuell neugestartet werden.
     */
    public abstract void restart() throws IllegalAccessException;
}
