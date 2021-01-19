package de.neo.cookiebot.game;

import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;

/**
 * Repräsentiert eine SpielInfo.
 * 
 * @author Neo8
 * @version 1.0
 */
public class GameInfo {

    String name;
    ArrayList<Member> players;
    GameType type;
    GameState state;
    
    /**
     * Neue GameInfo.
     * 
     * @param name Name des Spiels.
     * @param type Typ des Spiels.
     * @param state Status des Spiels.
     */
    public GameInfo(String name, GameType type, GameState state){
        this.name = name;
        this.players = new ArrayList<>();
        this.type = type;
        this.state = state;
    }
    
    /**
     * Gibt den Spielnamen zurück.
     * 
     * @return Spielname.
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Gibt an, ob ein Member mit dem Spiel interagieren kann.
     * 
     * @param m Member, der versucht zu Interagieren.
     * @return Boolean, ob der Member interagiere darf.
     */
    public Boolean canInteract(Member m) {
        return this.players.contains(m) && this.state.equals(GameState.STARTED);
    }
    
    /**
     * Gibt den Typ des Spiels an.
     * 
     * @return Typ des Spiels.
     */
    public GameType getType(){
        return this.type;
    }
    
    /**
     * Gibt den Status des Spiels zurück.
     * 
     * @return Status des Spiels.
     */
    public GameState getState(){
        return this.state;
    }
    
    /**
     * Gibt alle Mitspieler zurück.
     * 
     * @return ArrayList mit allen Mitspielern.
     */
    public ArrayList<Member> getPlayers() {
        return this.players;
    }
    
    /**
     * Ändert den Namen des Spiels.
     * 
     * @param name Neuer Name des Spiels.
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * Fügt einen Mitspieler hinzu.
     * 
     * @param m Neuer Mitspieler.
     */
    public void addPlayer(Member m){
        this.players.add(m);
    }
    
    /**
     * Ändert den Typ des Spiels.
     * 
     * @param type Neuer Typ des Spiels.
     */
    public void setType(GameType type){
        this.type = type;
    }
    
    /**
     * Ändert den Status des Spiels.
     * 
     * @param state Neuer Status des Spiels.
     */
    public void setState(GameState state){
        this.state = state;
    }
}
