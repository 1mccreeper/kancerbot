package me.kancer.discord;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import jdk.internal.event.Event;
import me.kancer.discord.commands.*;
import me.kancer.discord.events.*;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.InterfacedEventManager;
import org.graalvm.compiler.nodes.NodeView;
import sun.net.www.content.text.Generic;

import javax.security.auth.login.LoginException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static String token = "NjY3ODY0NTM5MjgyMjc2MzUz.XiOauw.O_TnE8NSyYg_GlZfVWd292OHBWo";

    private Main() throws LoginException {
        final JDA jda = new JDABuilder(AccountType.BOT)
                .setToken(token).build();

        CommandClientBuilder builder = new CommandClientBuilder();
        builder.setPrefix("~");
        builder.setOwnerId("189548680146190336");
        builder.setActivity(Activity.watching("You"));
        builder.setHelpWord("helpme");

        CommandClient client = builder.build();
        client.addCommand(new Ping());
        client.addCommand(new OwnerAt());
        client.addCommand(new Info());
        client.addCommand(new Roll());
        client.addCommand(new Nightmare());
        client.addCommand(new Help());
        client.addCommand(new Crackhead());
        jda.addEventListener(client);
        jda.addEventListener(new watching());
        jda.addEventListener(new hellothere());
        jda.addEventListener(new kancer());
        jda.addEventListener(new lewd());
        jda.addEventListener(new hello());
        jda.addEventListener(new nword());
    }

    public static void main(String[] args) throws LoginException{
        long enable = System.currentTimeMillis();
        new Main();
        System.out.println("Bot enabled in " + TimeUnit.MILLISECONDS.toSeconds((System.currentTimeMillis() - enable)) + " second(s)!");
    }
}
