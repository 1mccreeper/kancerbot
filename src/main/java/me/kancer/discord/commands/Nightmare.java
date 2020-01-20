package me.kancer.discord.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Nightmare extends Command {

    public Nightmare() {
        super.name = "nightmare";
    }

    @Override
    protected void execute(CommandEvent event) {
        for (int i = 0; i < 15; i++) {
            event.getChannel().sendMessage("Nightmare").queue();
        }
    }
}
