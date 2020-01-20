package me.kancer.discord.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Crackhead extends Command {

    public Crackhead() {
        super.name = "crackhead";
    }

    @Override
    protected void execute(CommandEvent event) {
        for (int i = 0; i < 15; i++) {
            event.getChannel().sendMessage("Crackhead").queue();
        }
    }
}
