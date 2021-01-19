package de.neo.cookiebot.listener;

import de.neo.cookiebot.Main;
import de.neo.cookiebot.game.tictactoe.TicTacToe;
import de.neo.cookiebot.vars.VarType;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Event.
 * Eine Nachricht wird empfangen.
 * 
 * @author Neo8
 * @version 1.0
 */
public class MessageReceivedListener extends ListenerAdapter {
	
	/**
	 * Event wird bearbeitet.
	 */
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if(e.getChannel().getType().equals(ChannelType.TEXT)) {
            if(Main.setup) {
                Main.setup_c.interact(e.getMessage());
            }else {
                if(e.getMessage().getContentRaw().startsWith(Main.vars.get(e.getGuild().getId()).get(VarType.PREFIX))) {
                    Main.cm.perform(e.getMessage());
                }else if(e.getTextChannel().getName().startsWith("tictactoe") && Main.gm.contains(e.getTextChannel().getId())) {
                    TicTacToe tictactoe = (TicTacToe) Main.gm.get(e.getTextChannel().getId());
                    tictactoe.interact(e.getMessage());
                }
            }
        }
    }
}
