package me.kancer.discord.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

public class Help extends Command {

    public Help() {
        super.name = "help";
    }

    @Override
    public void execute(CommandEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        if(args[0].equalsIgnoreCase("~help")) {
            if(!event.getMember().getUser().isBot()) {
                EmbedBuilder info = new EmbedBuilder();
                info.setTitle("PureKancer Help info");
                info.setDescription("Here are some commands you can use.");
                info.addField("Commands","~info, ~crackhead, ~nightmare, ~ownerat, ~ping, ~roll, ~help", false);
                info.setColor(0x990000);
                info.setThumbnail("https://lh3.googleusercontent.com/-OvLcFJYtmv8/AAAAAAAAAAI/AAAAAAAAAAA/xYRT9-ECzDg/s39-c-k/photo.jpg");

                event.getChannel().sendMessage(info.build()).queue();
            }
        }
    }
}
