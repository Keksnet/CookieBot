package de.neo.cookiebot.game;

import java.util.HashMap;

public class GameManager {

    HashMap<String, Game> games;

    public GameManager() {
        this.games = new HashMap<>();
    }

    public void add(Game g) {
        this.games.put(g.getTextChannel().getId(), g);
    }

    public void remove(Game g) {
        this.games.remove(g.getTextChannel().getId());
    }

    public void remove(String id) {
        this.remove(this.games.get(id));
    }

    public Game get(String id) {
        return this.games.get(id);
    }

    public Boolean contains(String id) {
        return this.games.containsKey(id);
    }
}
