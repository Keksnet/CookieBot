package de.neo.cookiebot.listener;

import de.neo.cookiebot.Main;
import de.neo.cookiebot.game.tictactoe.TicTacToe;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceivedListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if(e.getChannel().getType().equals(ChannelType.TEXT)) {
            if(e.getMessage().getContentRaw().startsWith("!")) {
                Main.cm.perform(e.getMessage());
            }else if(e.getTextChannel().getName().startsWith("tictactoe") && Main.gm.contains(e.getTextChannel().getId())) {
                TicTacToe tictactoe = (TicTacToe) Main.gm.get(e.getTextChannel().getId());
                tictactoe.interact(e.getMessage());
            }
        }
    }
}
