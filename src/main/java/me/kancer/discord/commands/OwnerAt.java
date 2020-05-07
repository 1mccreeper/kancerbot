package me.kancer.discord.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class OwnerAt extends Command {

    public OwnerAt() {
        super.name = "owner";
    }
    @Override
    public void execute(CommandEvent event) {
        String ownerID = event.getGuild().getOwner().getEffectiveName();
        String serverName = event.getGuild().getName();
        event.getChannel().sendMessageFormat("Owner of %s is %s", serverName, ownerID).queue();
    }

}
