package de.neo.cookiebot.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;

public class ErrorReporter {

    String msg;
    String footer;
    Boolean embed;

    public ErrorReporter(String msg, Boolean embed) {
        this.msg = msg;
        this.footer = "CookieBot by Neo8#4608";
        this.embed = embed;
    }

    public Message build() {
        if(this.embed) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Fehler Bericht");
            embed.setDescription(":x: " + this.msg + " :x:");
            embed.setColor(Color.red);
            embed.setFooter(this.footer);
            MessageBuilder builder = new MessageBuilder();
            builder.setEmbed(embed.build());
            embed.clear();
            return builder.build();
        }else {
            MessageBuilder builder = new MessageBuilder();
            builder.setContent(":x: " + this.msg + " :x:");
            return builder.build();
        }
    }
}
