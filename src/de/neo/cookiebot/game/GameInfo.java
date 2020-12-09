package de.neo.cookiebot.game;

import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;
import java.util.HashMap;

public class GameInfo {

    String name;
    ArrayList<Member> players;
    GameType type;
    GameState state;

    public GameInfo(String name, GameType type, GameState state){
        this.name = name;
        this.players = new ArrayList<>();
        this.type = type;
        this.state = state;
    }

    public String getName(){
        return this.name;
    }

    public Boolean canInteract(Member m) {
        return this.players.contains(m) && this.state.equals(GameState.STARTED);
    }

    public GameType getType(){
        return this.type;
    }

    public GameState getState(){
        return this.state;
    }

    public ArrayList<Member> getPlayers() {
        return this.players;
    }

    public void setName(String name){
        this.name = name;
    }

    public void addPlayer(Member m){
        this.players.add(m);
    }

    public void setType(GameType type){
        this.type = type;
    }

    public void setState(GameState state){
        this.state = state;
    }
}
