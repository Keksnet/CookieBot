package de.neo.cookiebot.commands;

import de.neo.cookiebot.Main;
import de.neo.cookiebot.commands.server.ServerCommand;
import de.neo.cookiebot.commands.server.debug.COMMAND_Set;
import de.neo.cookiebot.commands.server.debug.COMMAND_Setup;
import de.neo.cookiebot.commands.server.debug.COMMAND_Sync;
import de.neo.cookiebot.commands.server.gadget.COMMAND_Cookie;
import de.neo.cookiebot.commands.server.games.COMMAND_TicTacToe;
import de.neo.cookiebot.commands.server.team.COMMAND_TalkMute;
import de.neo.cookiebot.commands.server.team.COMMAND_TalkUnMute;
import de.neo.cookiebot.vars.VarType;
import net.dv8tion.jda.api.entities.Message;

import java.util.HashMap;

/**
 * Verwaltet die Commands.
 * 
 * @author Neo8
 * @version 1.0
 */
public class CommandManager {

    HashMap<String, ServerCommand> serverCommands;
    
    /**
     * Neue Instanz vom CommandManager.
     */
    public CommandManager() {
        this.serverCommands = new HashMap<>();

        this.serverCommands.put("tictactoe", new COMMAND_TicTacToe());
        this.serverCommands.put("set", new COMMAND_Set());
        this.serverCommands.put("cookie", new COMMAND_Cookie());
        this.serverCommands.put("setup", new COMMAND_Setup());
        this.serverCommands.put("talkmute", new COMMAND_TalkMute());
        this.serverCommands.put("talkunmute", new COMMAND_TalkUnMute());
        this.serverCommands.put("sync", new COMMAND_Sync());
    }
    
    /**
     * F&uuml;hrt den Command aus.
     * 
     * @param msg Message, in der der Command enthalten ist.
     */
    public void perform(Message msg) {
        String[] args = msg.getContentRaw().split(" ");
        if(this.serverCommands.containsKey(args[0].replace(Main.vars.get(msg.getGuild().getId()).get(VarType.PREFIX), ""))) {
            this.serverCommands.get(args[0].replace(Main.vars.get(msg.getGuild().getId()).get(VarType.PREFIX), "")).performCommand(msg.getMember(), msg.getTextChannel(), msg);
        }
    }
}
