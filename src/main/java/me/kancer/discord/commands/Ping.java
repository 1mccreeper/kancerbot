package me.kancer.discord.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Ping extends Command {

    public Ping() {
        super.name = "ping";
    }
    @Override
    protected void execute(CommandEvent event) {
        event.reply("Your Ping Pong Ching Chong: **" + event.getJDA().getGatewayPing() + "ms**");
    }
}
