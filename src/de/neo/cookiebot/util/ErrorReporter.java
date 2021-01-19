package de.neo.cookiebot.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;

/**
 * Erstellt ein FehlerReport mit vorgegebenen Werten.
 * 
 * @author Neo8
 * @version 1.0
 * @see de.neo.cookiebot.util.Embed
 * @see net.dv8tion.jda.api.EmbedBuilder
 */
public class ErrorReporter {

    String msg;
    String footer;
    Boolean embed;
    
    /**
     * Neues ErrorReport.
     * 
     * @param msg Fehlermeldung.
     * @param embed Boolean, ob die Nachricht ein Embed werden soll.
     */
    public ErrorReporter(String msg, Boolean embed) {
        this.msg = msg;
        this.footer = "CookieBot by Neo8#4608";
        this.embed = embed;
    }
    
    /**
     * Gibt die fertige Message zur&uuml;ck.
     * 
     * @return Message.
     */
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
