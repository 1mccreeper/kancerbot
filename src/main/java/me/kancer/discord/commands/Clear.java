package me.kancer.discord.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.util.List;

public class Clear extends Command {

    public Clear() {
        super.name = "clear";
    }

    @Override
    public void execute(CommandEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        if(args[0].equalsIgnoreCase("~clear")) {
            if (args.length < 2) {
                EmbedBuilder usage = new EmbedBuilder();
                usage.setColor(0x990000);
                usage.setTitle("Specify Amount of Messages to Delete");
                usage.setDescription("Usage: `~clear [# of messages]`");
                event.getChannel().sendMessage(usage.build()).queue();
            }
            if ((Integer.valueOf(args[1]) >= 100)) {
                event.getChannel().sendMessage("Too many Messages have been selected.").queue();
                event.getChannel().sendMessage("Only 1-99 Messages can be deleted at a time.").queue();
            }
            else {
                List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
                for (int i = 0; i < messages.size(); i++) {
                    event.getGuild().getTextChannelById("669382029279887371").sendMessage("**Cleared Commands: **" + messages.get(i)).queue();
                }
                event.getChannel().purgeMessages(messages);
            }
        }
        }
}
