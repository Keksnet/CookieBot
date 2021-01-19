package de.neo.cookiebot.game;

import java.util.HashMap;

/**
 * Verwaltet alle laufenden Spiele.
 * 
 * @author Neo8
 * @version 1.0
 */
public class GameManager {

    HashMap<String, Game> games;
    
    /**
     * Neue Instanz des GameManagers.
     */
    public GameManager() {
        this.games = new HashMap<>();
    }
    
    /**
     * F&uuml;gt ein neues Spiel hinzu.
     * 
     * @param g Neues Spiel.
     */
    public void add(Game g) {
        this.games.put(g.getTextChannel().getId(), g);
    }
    
    /**
     * Entfernt ein altes Spiel.
     * 
     * @param g Altes Spiel.
     */
    public void remove(Game g) {
        this.games.remove(g.getTextChannel().getId());
    }
    
    /**
     * Entfernt ein altes Spiel.
     * 
     * @param id ID des TextChannels, in dem das Spiel stattfindet.
     * @see de.neo.cookiebot.game.GameManager#remove(Game)
     */
    public void remove(String id) {
        this.remove(this.games.get(id));
    }
    
    /**
     * Gibt ein Spiel zur&uuml;ck, welches in einem TextChannel stattfindet.
     * 
     * @param id ID des TextChannels, in dem das Spiel stattfindet.
     * @return Spiel, welches stattfindet.
     */
    public Game get(String id) {
        return this.games.get(id);
    }
    
    /**
     * Gibt an, ob bereits ein Spiel in einem TextChannel stattfindet.
     * 
     * @param id ID des TextChannels.
     * @return Boolean, ob bereits ein Spiel in einem TextChannel stattfindet.
     */
    public Boolean contains(String id) {
        return this.games.containsKey(id);
    }
}
