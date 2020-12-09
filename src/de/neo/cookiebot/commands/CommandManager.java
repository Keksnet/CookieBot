package de.neo.cookiebot.commands;

import de.neo.cookiebot.commands.server.ServerCommand;
import de.neo.cookiebot.commands.server.debug.COMMAND_Set;
import de.neo.cookiebot.commands.server.games.COMMAND_TicTacToe;
import net.dv8tion.jda.api.entities.Message;

import java.util.HashMap;

public class CommandManager {

    HashMap<String, ServerCommand> serverCommands;

    public CommandManager() {
        this.serverCommands = new HashMap<>();

        this.serverCommands.put("tictactoe", new COMMAND_TicTacToe());
        this.serverCommands.put("set", new COMMAND_Set());
    }

    public void perform(Message msg) {
        String[] args = msg.getContentRaw().split(" ");
        if(this.serverCommands.containsKey(args[0].replace("!", ""))) {
            this.serverCommands.get(args[0].replace("!", "")).performCommand(msg.getMember(), msg.getTextChannel(), msg);
        }
    }
}
