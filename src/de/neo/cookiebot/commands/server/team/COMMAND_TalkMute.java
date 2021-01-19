package de.neo.cookiebot.commands.server.team;

import de.neo.cookiebot.Main;
import de.neo.cookiebot.commands.server.ServerCommand;
import de.neo.cookiebot.vars.VarType;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * ModerationCommand.
 * Command: !talkmute
 * 
 * @author Neo8
 * @version 1.0
 */
public class COMMAND_TalkMute implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel c, Message msg) {
		if(m.hasPermission(Permission.VOICE_MUTE_OTHERS) || m.getRoles().contains(m.getGuild().getRoleById(Main.vars.get(m.getGuild().getId()).get(VarType.TECHNIK)))) {
			if(m.getVoiceState().inVoiceChannel()) {
				for(Member mem : m.getVoiceState().getChannel().getMembers()) {
					mem.mute(true).queue();
				}
			}
		}
		msg.delete().queue();
	}

}
