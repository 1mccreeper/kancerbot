package me.kancer.discord.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class nword extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String args[] = event.getMessage().getContentRaw().split(" ");
        if(args[0].equalsIgnoreCase("nigger") || args[0].equalsIgnoreCase("nigga")) {
            EmbedBuilder nword = new EmbedBuilder();
            event.getChannel().sendMessage("Wanna say the n-word on my Christian Server?");
            nword.setTitle("Wanna say the n-word on my Christian Server?");
            nword.setDescription("Then Perish");
            nword.setImage("https://i.kym-cdn.com/photos/images/original/001/342/448/526.jpg");
            event.getChannel().sendMessage(nword.build()).queue();
        }
    }
}
