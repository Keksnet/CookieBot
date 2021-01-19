package de.neo.cookiebot.commands.server.games;

import de.neo.cookiebot.Main;
import de.neo.cookiebot.commands.server.ServerCommand;
import de.neo.cookiebot.game.tictactoe.TicTacToe;
import de.neo.cookiebot.util.Embed;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

/**
 * GameCommand.
 * Command: !tictactoe [@Member]
 * 
 * @author Neo8
 * @version 1.0
 */
public class COMMAND_TicTacToe implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel c, Message msg) {
        if(!msg.getMentionedMembers().isEmpty()) {
            Member t = msg.getMentionedMembers().get(0);
            if(Main.tictactoe_request.containsKey(t)) {
                if(Main.tictactoe_request.get(t).equals(m)) {
                    TicTacToe game = new TicTacToe(t, m);
                    game.start();
                }else {
                    c.sendMessage(new Embed("TicTacToe (Anfrage)", t.getAsMention() + " m&ouml;chte nicht mit dir TicTacToe spielen!", Color.pink).build()).queue();
                }
            }else if(Main.tictactoe_request.containsKey(m)){
                c.sendMessage(new Embed("TicTacToe (Anfrage)", "Anfrage zur&uuml;ckgezogen!", Color.green).build()).queue();
                Main.tictactoe_request.remove(m);
            }else {
                c.sendMessage(new Embed("TicTacToe (Anfrage)", "Anfrage versendet!", Color.green).build()).queue();
                c.sendMessage(t.getAsMention() + " " + m.getAsMention() + " m&ouml;chte mit dir TicTacToe spielen! Schreibe !tictactoe " + m.getAsMention() + " um das Spiel zu starten!").queue();
                Main.tictactoe_request.put(m, t);
            }
        }else {
            c.sendMessage(new Embed("TicTacToe (Anfrage)", "Bitte verwende: `!tictactoe <Spieler>`", Color.green).build()).queue();
        }
        msg.delete().queue();
    }
}
