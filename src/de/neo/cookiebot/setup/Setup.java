package de.neo.cookiebot.setup;

import de.neo.cookiebot.Main;
import de.neo.cookiebot.sql.ID_SQL;
import de.neo.cookiebot.util.Embed;
import de.neo.cookiebot.vars.VarManager;
import de.neo.cookiebot.vars.VarType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Setup {

    private final Member m;
    private SetupStep step;
    HashMap<VarType, String> vals;

    public Setup(Member m) {
        this.m = m;
        this.step = SetupStep.STEP_0;
        this.vals = new HashMap<>();
    }

    public void interact(Message msg) {
        Member m = msg.getMember();
        if(this.m.getId().equals(m.getId())) {
            Guild g = msg.getGuild();
            VarManager vars = Main.vars.get(g.getId());
            String content = msg.getContentRaw();
            switch (this.step) {
                case STEP_0:
                    if(content.toLowerCase().equals("start")){
                        this.step = SetupStep.STEP_1;
                        this.sendMessage(msg.getTextChannel());
                    }else{
                        this.sendError(msg.getTextChannel());
                    }
                    break;

                case STEP_1:
                    if(!msg.getMentionedRoles().isEmpty()) {
                        vals.put(VarType.TECHNIK, msg.getMentionedRoles().get(0).getId());
                        this.step = SetupStep.STEP_2;
                        this.sendMessage(msg.getTextChannel());
                    }else {
                        this.sendError(msg.getTextChannel());
                    }
                    break;

                case STEP_2:
                    if(!msg.getMentionedRoles().isEmpty()) {
                        vals.put(VarType.DEBUG, msg.getMentionedRoles().get(0).getId());
                        this.step = SetupStep.STEP_3;
                        this.sendMessage(msg.getTextChannel());
                    }else {
                        this.sendError(msg.getTextChannel());
                    }
                    break;

                case STEP_3:
                    if(content.toLowerCase().equals("true") || content.toLowerCase().equals("false")){
                        if(content.toLowerCase().equals("true")) {
                            vals.put(VarType.REPORT_ERRORS, "true");
                            this.step = SetupStep.STEP_4;
                        }else {
                            vals.put(VarType.REPORT_ERRORS, "false");
                            this.step = SetupStep.STEP_4;
                        }
                        this.sendMessage(msg.getTextChannel());
                    }else {
                        this.sendError(msg.getTextChannel());
                    }
                    break;

                case STEP_4:
                    if(!msg.getMentionedChannels().isEmpty()) {
                        vals.put(VarType.RULES, msg.getMentionedChannels().get(0).getId());
                        this.step = SetupStep.STEP_5;
                        this.sendMessage(msg.getTextChannel());
                    }else {
                        this.sendError(msg.getTextChannel());
                    }
                    break;

                case STEP_5:
                    if(!msg.getMentionedChannels().isEmpty()) {
                        vals.put(VarType.SUPPORT, msg.getMentionedChannels().get(0).getId());
                        this.step = SetupStep.STEP_6;
                        this.sendMessage(msg.getTextChannel());
                    }else {
                        this.sendError(msg.getTextChannel());
                    }
                    break;

                case STEP_6:
                    content = content.replace("-", "").toUpperCase();
                    if(content.length() == 16) {
                        if(this.checkKey(content, g.getId())) {
                            vals.put(VarType.LICENSE_KEY, content);
                            this.step = SetupStep.STEP_7;
                            this.sendMessage(msg.getTextChannel());
                        }else {
                            vars.clear();
                        }
                    }else {
                        vars.clear();
                    }
                    break;

                case STEP_7:
                    vals.put(VarType.DEBUG, content);
                    this.step = SetupStep.STEP_8;
                    this.sendMessage(msg.getTextChannel());
                    break;
            }
        }
    }

    public void sendMessage(TextChannel c) {
        switch (this.step) {
            case STEP_1:
                c.sendMessage(new Embed("Setup [2/9]", "Bitte pinge die Technikrolle, die den Bot uneingeschränkt nutzen kann.", Color.green).build()).queue();
                break;

            case STEP_2:
                c.sendMessage(new Embed("Setup [3/9]", "Bitte pinge die Debugrolle, die wichtige Nachrichten erhalten soll. (DM müssen aktiviert sein.)", Color.green).build()).queue();
                break;

            case STEP_3:
                c.sendMessage(new Embed("Setup [4/9]", "Soll die Debugrolle wichtige Nachrichten erhalten? (true/false)", Color.green).build()).queue();
                break;

            case STEP_4:
                c.sendMessage(new Embed("Setup [5/9]", "Bitte pinge den Kanal mit den Regeln (z.B. " + c.getAsMention() + ")", Color.green).build()).queue();
                break;

            case STEP_5:
                c.sendMessage(new Embed("Setup [6/9]", "Bitte pinge den Kanal mit dem Support (z.B. " + c.getAsMention() + ")", Color.green).build()).queue();
                break;

            case STEP_6:
                c.sendMessage(new Embed("Setup [7/9]", "Bitte gebe den 16-stelligen Lizenzschlüssel ein.", Color.green).build()).queue();
                break;

            case STEP_7:
                c.sendMessage(new Embed("Setup [8/9]", "Bitte gebe die ID der Kategorie für TicTacToe ein.", Color.green).build()).queue();
                break;

            case STEP_8:
                c.sendMessage(new Embed("Setup [9/9]", "Das Setup wurde erfolgreich beendet! :tada:", Color.green).build()).queue();
                Main.setup = false;
                for(Map.Entry<VarType, String> set : this.vals.entrySet()){
                    Main.vars.get(c.getGuild().getId()).add(set.getKey(), set.getValue());
                }
                try{
                    ID_SQL.sync(Main.vars.get(c.getGuild().getId()).getVars(), c.getGuild().getId());
                }catch(SQLException ignore){
                }
                break;
        }
    }

    public void sendError(TextChannel c) {
        c.sendMessage(new Embed("Setup [Fehler]", ":x: Dieser Wert ist nicht gültig! :x:", Color.red).build()).queue();
    }

    public Boolean checkKey(String key, String id) {
        try{
            String params = "key=" + URLEncoder.encode(key, "UTF-8") + "&guild=" + URLEncoder.encode(id, "UTF-8");
            URL url = new URL("https://www.neo8.de/cookiebot/validate.php");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setFixedLengthStreamingMode(params.getBytes().length);

            OutputStreamWriter cw = new OutputStreamWriter(con.getOutputStream());
            cw.write(params);
            cw.flush();
            cw.close();

            String response = this.parse(con.getInputStream());
            con.disconnect();
            if(response.toLowerCase().equals("valid")) {
                return true;
            }else {
                return false;
            }
        }catch(IOException ignore) {
            return false;
        }
    }

    private String parse(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String az;
        try{
            while((az = br.readLine()) != null){
                sb.append(az);
                sb.append("\n");
            }

            br.close();
        }catch(IOException ignore){
        }

        return sb.toString().trim();
    }
}
