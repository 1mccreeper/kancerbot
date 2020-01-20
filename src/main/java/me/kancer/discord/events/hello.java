package me.kancer.discord.events;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class hello extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        String name = event.getMember().getUser().getName();
        if(args[0].equalsIgnoreCase("hey")) {
            if(!event.getMember().getUser().isBot()) {
                event.getChannel().sendMessage("Sup " + name).queue();
            }
        }
    }
}
