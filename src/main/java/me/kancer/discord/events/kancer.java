package me.kancer.discord.events;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class kancer extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String[] args = e.getMessage().getContentRaw().split(" ");
        String name = e.getMember().getUser().getName();
        if (args[0].equalsIgnoreCase("kancerfy")) {
            if (!e.getMember().getUser().isBot()) {
                e.getMember().modifyNickname("kancerboi");
            }
        }
    }
}
