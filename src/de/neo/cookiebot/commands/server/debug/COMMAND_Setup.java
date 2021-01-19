package de.neo.cookiebot.commands.server.debug;

import de.neo.cookiebot.Main;
import de.neo.cookiebot.commands.server.ServerCommand;
import de.neo.cookiebot.setup.Setup;
import de.neo.cookiebot.util.Embed;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

/**
 * EinrichtungsCommand.
 * Command: !setup
 * 
 * @author Neo8
 * @version 1.0
 */
public class COMMAND_Setup implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel c, Message msg) {
        if(m.getGuild().getOwnerId().equals(m.getId())) {
            c.sendMessage(new Embed("Setup [1/10]", "Hallo, ich bin der CookieBot :cookie:!\nIch werde dich nun durch mein Setup begleiten.\nSchreibe `start` um mit dem Setup zu beginnen!", Color.green).build()).queue();
            Setup s = new Setup();
            Main.setup_c = s;
            Main.setup = true;
        }
    }
}
