package de.neo.cookiebot.commands.server.debug;

import de.neo.cookiebot.Main;
import de.neo.cookiebot.commands.server.ServerCommand;
import de.neo.cookiebot.sql.ID_SQL;
import de.neo.cookiebot.util.Embed;
import de.neo.cookiebot.util.ErrorReporter;
import de.neo.cookiebot.vars.VarManager;
import de.neo.cookiebot.vars.VarType;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.IMentionable;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.sql.SQLException;

/**
 * DebugCommand.
 * Command: !set [Key] [Value]
 * 
 * @author Neo8
 * @version 1.0
 */
public class COMMAND_Set implements ServerCommand {
	
    @Override
    public void performCommand(Member m, TextChannel c, Message msg) {
        if(m.hasPermission(Permission.ADMINISTRATOR) || m.getRoles().contains(m.getGuild().getRoleById(Main.vars.get(m.getGuild().getId()).get(VarType.TECHNIK)))) {
            if(!msg.getMentions(Message.MentionType.ROLE, Message.MentionType.CHANNEL).isEmpty()) {
                IMentionable men = msg.getMentions(Message.MentionType.CHANNEL, Message.MentionType.ROLE).get(0);
                Main.vars.get(m.getGuild().getId()).add(VarType.valueOf(msg.getContentRaw().split(" ")[1].toUpperCase()), men.getId());
                try{
                    ID_SQL.sync(Main.vars.get(m.getGuild().getId()).getVars(), m.getGuild().getId());
                    c.sendMessage(new Embed("Debug [Set]", "Daten gesetzt!", Color.cyan).build()).queue();
                }catch(SQLException e) {
                    Main.INSTANCE.reportError(m.getGuild(), new ErrorReporter("Die SQL-Verbindung funktioniert nicht! Bitte versuche es sp&auml;ter erneut!", true).build());
                }
            }else if(msg.getContentRaw().split(" ")[2].toLowerCase().equals("cat")) {
                VarManager man = Main.vars.get(m.getGuild().getId());
                VarType type = VarType.valueOf(msg.getContentRaw().split(" ")[1].toUpperCase());
                String val = msg.getContentRaw().split(" ")[3];
                man.add(type, val);
                try{
                    ID_SQL.sync(Main.vars.get(m.getGuild().getId()).getVars(), m.getGuild().getId());
                    c.sendMessage(new Embed("Debug [Set]", "Daten gesetzt!", Color.cyan).build()).queue();
                }catch(SQLException e) {
                    Main.INSTANCE.reportError(m.getGuild(), new ErrorReporter("Die SQL-Verbindung funktioniert nicht! Bitte versuche es sp&auml;ter erneut!", true).build());
                }
            }
        }
    }
}
