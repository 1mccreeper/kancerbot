package me.kancer.discord.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class lewd extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        String name = event.getMember().getUser().getName();
        if(args[0].equalsIgnoreCase("lewd")){
            if(!event.getMember().getUser().isBot()) {
                EmbedBuilder lewd = new EmbedBuilder();
                lewd.setTitle("Like Lewds?");
                lewd.setDescription(";)");
                lewd.setImage("https://i.kym-cdn.com/entries/icons/original/000/017/225/zAp2LzJ.jpg");
                event.getChannel().sendMessage(lewd.build()).queue();
            }
        }
    }
}
