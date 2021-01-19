package de.neo.cookiebot.game.tictactoe;

import de.neo.cookiebot.Main;
import de.neo.cookiebot.game.Game;
import de.neo.cookiebot.game.GameInfo;
import de.neo.cookiebot.game.GameState;
import de.neo.cookiebot.game.GameType;
import de.neo.cookiebot.util.Embed;
import de.neo.cookiebot.util.ErrorReporter;
import de.neo.cookiebot.vars.VarType;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * TicTacToe Game.
 * 
 * @author Neo8
 * @version 1.0
 */
public class TicTacToe implements Game {

    GameInfo info;
    Guild g;
    TextChannel text;
    Member p1;
    Member p2;
    Member next;
    HashMap<String, FieldState> table;
    
    /**
     * Neues TicTacToe Game beginnen.
     * 
     * @param p1 Spieler 1.
     * @param p2 Spieler 2.
     */
    public TicTacToe(Member p1, Member p2){
        GameInfo info = new GameInfo("tictactoe-" + p1.getUser().getAsTag() + "-" + p2.getUser().getAsTag(), GameType.TICTACTOE, GameState.READY);
        info.addPlayer(p1);
        info.addPlayer(p2);
        this.info = info;
        this.g = p1.getGuild();
        this.p1 = p1;
        this.p2 = p2;
        this.next = p1;
        this.table = new HashMap<>();
        this.initTable();
    }
    
    /**
     * Spielfeld vorbereiten.
     */
    public void initTable() {
        for(String s : new String[] {"OL", "OM", "OR", "ML", "MM", "MR", "UL", "UM", "UR"}) {
            this.table.put(s, FieldState.UNSET);
        }
    }
    
    /**
     * Aktuelles Spielfeld in Text umwandeln.
     * 
     * @return String, der das Spielfeld in Text darstellt.
     */
    public String getTable() {
        String table = "";
        for(String s : new String[] {"OL", "OM", "OR", "\n", "ML", "MM", "MR", "\n", "UL", "UM", "UR"}) {
            if(s.equals("\n")) {
                table += "\n" + "----------------" + "\n";
            }else {
                if(this.table.get(s).equals(FieldState.UNSET)) {
                    table += ":white_large_square:";
                }else if(this.table.get(s).equals(FieldState.PLAYER_1)) {
                    table += ":blue_square:";
                }else if(this.table.get(s).equals(FieldState.PLAYER_2)) {
                    table += ":red_square:";
                }
                if(!s.endsWith("R")) {
                    table += " | ";
                }
            }
        }
        return table;
    }
    
    /**
     * �berpr�fen, ob bereits jemand gewonnen hat.
     * 
     * @param id FeldID, des letzten Zugs.
     * @return Boolean, ob jemand gewonnen hat.
     */
    public Boolean checkWin(String id) {
        id = id.toUpperCase();
        FieldState field = this.table.get(id);
        switch (id) {
            case "OL":
                if(this.table.get("OM").equals(field) && this.table.get("OR").equals(field)) {
                    return true;
                }else if(this.table.get("MM").equals(field) && this.table.get("UR").equals(field)) {
                    return true;
                }else if(this.table.get("ML").equals(field) && this.table.get("UL").equals(field)) {
                    return true;
                }
                break;

            case "OM":
                if(this.table.get("OL").equals(field) && this.table.get("OR").equals(field)) {
                    return true;
                }else if(this.table.get("MM").equals(field) && this.table.get("UM").equals(field)) {
                    return true;
                }
                break;

            case "OR":
                if(this.table.get("OM").equals(field) && this.table.get("OL").equals(field)) {
                    return true;
                }else if(this.table.get("MM").equals(field) && this.table.get("UL").equals(field)) {
                    return true;
                }else if(this.table.get("MR").equals(field) && this.table.get("UR").equals(field)) {
                    return true;
                }
                break;

            case "ML":
                if(this.table.get("OL").equals(field) && this.table.get("UL").equals(field)) {
                    return true;
                }else if(this.table.get("MM").equals(field) && this.table.get("MR").equals(field)) {
                    return true;
                }
                break;

            case "MM":
                if(this.table.get("OL").equals(field) && this.table.get("UR").equals(field)) {
                    return true;
                }else if(this.table.get("OR").equals(field) && this.table.get("UL").equals(field)) {
                    return true;
                }else if(this.table.get("OM").equals(field) && this.table.get("UM").equals(field)) {
                    return true;
                }else if(this.table.get("ML").equals(field) && this.table.get("MR").equals(field)) {
                    return true;
                }
                break;

            case "MR":
                if(this.table.get("OR").equals(field) && this.table.get("UR").equals(field)) {
                    return true;
                }else if(this.table.get("ML").equals(field) && this.table.get("MM").equals(field)) {
                    return true;
                }
                break;

            case "UL":
                if(this.table.get("OL").equals(field) && this.table.get("ML").equals(field)) {
                    return true;
                }else if(this.table.get("UM").equals(field) && this.table.get("UR").equals(field)) {
                    return true;
                }else if(this.table.get("MM").equals(field) && this.table.get("OR").equals(field)) {
                    return true;
                }
                break;

            case "UM":
                if(this.table.get("UL").equals(field) && this.table.get("UR").equals(field)) {
                    return true;
                }else if(this.table.get("MM").equals(field) && this.table.get("OM").equals(field)) {
                    return true;
                }
                break;

            case "UR":
                if(this.table.get("UL").equals(field) && this.table.get("UM").equals(field)) {
                    return true;
                }else if(this.table.get("OR").equals(field) && this.table.get("MR").equals(field)) {
                    return true;
                }else if(this.table.get("MM").equals(field) && this.table.get("OL").equals(field)) {
                    return true;
                }
                break;
        }
        return false;
    }

    @Override
    public GameInfo getInfo() {
        return this.info;
    }

    @Override
    public TextChannel getTextChannel() {
        return this.text;
    }

    @Override
    public Boolean canInteract(Member m) {
        return this.info.canInteract(m) && m.equals(this.next);
    }

    @Override
    public void start() {
        if(this.info.getState().equals(GameState.READY)){
            Category cat = Main.INSTANCE.manager.getCategoryById(Main.vars.get(this.g.getId()).get(VarType.TICTACTOE_CATEGORY));
            if(cat != null) {
                this.text = cat.createTextChannel(this.info.getName()).complete();
                this.text.sendMessage(new Embed("TicTacToe (Bedeutung der Felder)", this.p1.getEffectiveName() + " ist :blue_square:\n" + this.p2.getEffectiveName() + " ist :red_square:\nFreie Felder sind :white_large_square:", Color.cyan).build()).queue();
                this.text.sendMessage(new Embed("TicTacToe (Anleitung [1/2])", "Jedes Feld hat eine ID:\n```OL | OM | OR\n----------------\nML | MM | MR\n----------------\nUL | UM | UR```", Color.cyan).build()).queue();
                this.text.sendMessage(new Embed("TicTacToe (Anleitung [2/2])", "Wenn du am Zug bist, schreibe einfach die ID des Feldes, auf welches du setzen möchtest, in diesen Textkanal.", Color.cyan).build()).queue();
                this.text.sendMessage(new Embed("TicTacToe (" + this.p1.getEffectiveName() + " ist am Zug)", this.getTable(), Color.blue).build()).queue();
                for(Member m : this.info.getPlayers()) {
                    Collection<Permission> allow = new ArrayList<>();
                    allow.add(Permission.MESSAGE_WRITE);
                    allow.add(Permission.MESSAGE_HISTORY);
                    allow.add(Permission.MESSAGE_READ);
                    this.text.createPermissionOverride(m).setAllow(allow).queue();
                }
                this.info.setState(GameState.STARTED);
                Main.gm.add(this);
            }else {
                Main.INSTANCE.reportError(this.g, new ErrorReporter("Die TicTacToe-Kategorie ist nicht verfügbar oder nicht gesetzt!", true).build());
            }
        }
    }

    @SuppressWarnings("unused")
	@Override
    public void interact(Message msg) {
        Member m = msg.getMember();
        String content = msg.getContentRaw();
        if(this.canInteract(m)) {
            if(this.table.containsKey(content.toUpperCase())) {
                if(this.table.get(content.toUpperCase()).equals(FieldState.UNSET)) {
                    if(m.equals(this.p1)) {
                        this.table.remove(content.toUpperCase());
                        this.table.put(content.toUpperCase(), FieldState.PLAYER_1);
                        this.text.sendMessage(new Embed("TicTacToe (" + this.p2.getEffectiveName() + " ist am Zug)", this.getTable(), Color.red).build()).queue();
                        this.next = this.p2;
                        if(this.checkWin(content)) {
                            try{
                                this.win(m);
                            }catch(IllegalAccessException ignore){
                            }
                        }
                    }else if(m.equals(this.p2)) {
                        this.table.remove(content.toUpperCase());
                        this.table.put(content.toUpperCase(), FieldState.PLAYER_2);
                        this.text.sendMessage(new Embed("TicTacToe (" + this.p1.getEffectiveName() + " ist am Zug)", this.getTable(), Color.blue).build()).queue();
                        this.next = this.p1;
                        if(this.checkWin(content)) {
                            try{
                                this.win(m);
                            }catch(IllegalAccessException ignore){
                            }
                        }else {
                            Boolean isDraw = true;
                            for(Map.Entry<String, FieldState> set : this.table.entrySet()) {
                                if(set.getValue().equals(FieldState.UNSET)) {
                                    isDraw = false;
                                }
                            }
                            if(isDraw) {
                                this.text.sendMessage(new Embed("TicTacToe (Ende)", "Unentschieden!\n\nDieser Channel wird in 20 Sekunden gelöscht!", Color.green).build()).complete();
                                ExecutorService threadpool = Executors.newCachedThreadPool();
                                Future<?> task = threadpool.submit(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(20000L);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        text.delete().queue();
                                    }
                                });
                                Main.tictactoe_request.remove(p1);
                                this.info.setState(GameState.STOPPED);
                                Main.gm.remove(this);
                            }
                        }
                    }
                }else {
                    this.text.sendMessage(new Embed("TicTacToe (" + this.next.getEffectiveName() + " ist am Zug)", "Dieses Feld ist schon belegt!", Color.cyan).build()).queue();
                    this.text.sendMessage(new Embed("TicTacToe (" + this.next.getEffectiveName() + " ist am Zug)", this.getTable(), Color.cyan).build()).queue();
                }
            }else {
                this.text.sendMessage(new Embed("TicTacToe (" + this.next.getEffectiveName() + " ist am Zug)", "Diese ID ist ungültig!", Color.cyan).build()).queue();
                this.text.sendMessage(new Embed("TicTacToe (" + this.next.getEffectiveName() + " ist am Zug)", this.getTable(), Color.cyan).build()).queue();
            }
        }
    }

    @Override
    public void stop() {
        if(this.info.getState().equals(GameState.STARTED)) {
            this.text.delete().queue();
            Main.gm.remove(this);
            this.info.setState(GameState.STOPPED);
        }
    }

    @SuppressWarnings("unused")
	@Override
    public void win(Member m) throws IllegalAccessException {
        this.text.sendMessage(new Embed("TicTacToe (Ende)", m.getEffectiveName() + " hat gewonnen! :tada: Herzlichen Glückwunsch! :gift:\n\nDieser Channel wird in 20 Sekunden gelöscht!", Color.green).build()).complete();
        ExecutorService threadpool = Executors.newCachedThreadPool();
        Future<?> task = threadpool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                text.delete().queue();
            }
        });
        Main.tictactoe_request.remove(p1);
        this.info.setState(GameState.STOPPED);
        Main.gm.remove(this);
    }

    @Override
    public void loose(Member m) throws IllegalAccessException {
        throw new IllegalAccessException("This game can not be loosed!");
    }

    @Override
    public void restart() throws IllegalAccessException {
        throw new IllegalAccessException("This game has no restart!");
    }
}
