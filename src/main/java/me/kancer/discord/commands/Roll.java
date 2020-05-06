package me.kancer.discord.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.List;

public class Roll extends Command {

    public Roll() {
        super.name = "r";
    }

    @Override
    public void execute(CommandEvent event) {
        String bonus = "1", dices, sides = "";
        int total = 0, dice;
        boolean addBonus = false;
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args.length < 2) {
            EmbedBuilder usage = new EmbedBuilder();
            usage.setColor(0x990000);
            usage.setTitle("Specify Amount of Dice and Sides to roll");
            usage.setDescription("Usage: `~r <# of Dice>d<# of Sides>`");
            event.getChannel().sendMessage(usage.build()).queue();
        } else {
            String[] n = args[1].split("d");
            if (n[1].contains("+")) {
                int index = n[1].indexOf("+");
                sides = n[1].substring(0, index);
                bonus = n[1].substring(index + 1);
                dices = n[0];
                addBonus = true;
            } else {
                sides = n[1];
                dices = n[0];
            }

            List roll  = new ArrayList();
            if (Integer.valueOf(dices) > 100) {
                event.reply("Bruh, I'm not rolling more than 100 Dices.");
            }
            if (Integer.valueOf(dices) <= 100) {

                for (int i = 0; i < Integer.valueOf(dices); i++) {
                    dice = (int)(Math.random()*Integer.valueOf(sides)+1);
                    total += dice;
                    roll.add(dice);
                }
                if (addBonus) {
                    total += Integer.valueOf(bonus);
                }

                event.getChannel().sendMessageFormat("Rolls: %s", roll.toString()).queue();
                event.getChannel().sendMessageFormat("Total: %d", total).queue();
            }
        }

    }
}