package me.kancer.discord.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

public class Info extends Command {

    public Info() {
        super.name = "info";
    }

    @Override
    public void execute(CommandEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        if(args[0].equalsIgnoreCase("~info")) {
            if(!event.getMember().getUser().isBot()) {
                EmbedBuilder info = new EmbedBuilder();
                info.setTitle("Kancer DnD Bot Information");
                info.setDescription("Completely useless information about a useless bot.");
                info.addField("Creator","PureKancer", false);
                info.setColor(0x990000);
                info.setThumbnail("https://lh3.googleusercontent.com/-OvLcFJYtmv8/AAAAAAAAAAI/AAAAAAAAAAA/xYRT9-ECzDg/s39-c-k/photo.jpg");

                event.getChannel().sendMessage(info.build()).queue();
            }
        }
    }
}
