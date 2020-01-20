package me.kancer.discord.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Roll extends Command {

    public Roll() {
        super.name = "roll";
    }

    @Override
    public void execute(CommandEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        String[] n = args[1].split("d");
        String sides = n[1];
        String dices = n[0];
        if (Integer.valueOf(dices) > 100) {
            event.reply("Bruh, I'm not rolling more than 100 Dices.");
        }
        if (Integer.valueOf(dices) <= 100) {
            for (int i = 0; i < Integer.valueOf(dices); i++) {
                int dice = (int)(Math.random()*Integer.valueOf(sides)+Integer.valueOf(dices));
                event.reply(String.valueOf(dice));
            }
        }
    }
}
