package de.neo.cookiebot.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;

/**
 * Ein simplerer EmbedBuilder.
 * 
 * @author Neo8
 * @version 1.0
 * @see net.dv8tion.jda.api.EmbedBuilder
 */
public class Embed {

    String title;
    String description;
    String footer;
    Color c;
    
    /**
     * Neues Embed.
     * 
     * @param title Titel des Embeds.
     * @param description Beschreibung des Embeds.
     * @param c Farbe des Embeds.
     */
    public Embed(String title, String description, Color c) {
        this.title = title;
        this.description = description;
        this.footer = "CookieBot by Neo8#4608";
        this.c = c;
    }
    
    /**
     * Gibt die fertige Nachricht zurück.
     * 
     * @return Message, die das Embed enthält.
     */
    public Message build() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(this.title);
        embed.setDescription(this.description);
        embed.setColor(this.c);
        embed.setFooter(this.footer);
        MessageBuilder builder = new MessageBuilder();
        builder.setEmbed(embed.build());
        embed.clear();
        return builder.build();
    }
}
