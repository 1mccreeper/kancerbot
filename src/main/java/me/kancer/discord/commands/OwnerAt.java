package me.kancer.discord.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class OwnerAt extends Command {

    public OwnerAt() {
        super.name = "owner";
    }
    @Override
    public void execute(CommandEvent event) {
        String owner = event.getGuild().getOwner().getAsMention();
        event.reply("Sup nerd " + owner);

    }

}
