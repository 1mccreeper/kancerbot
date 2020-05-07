package me.kancer.discord;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.kancer.discord.commands.*;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends ListenerAdapter {

    public static void main(String[] args) throws Exception {
        new JDABuilder(AccountType.BOT)
                .setToken(token)
                .addEventListeners(new Main())
                .build();

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
        client.addCommand(new Help());
        client.addCommand(new Clear());
    }

    public static String token = "NjY3ODY0NTM5MjgyMjc2MzUz.XnlA5w.azQ0uzqdHwUPba80evVrRMMTGgE";

    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    private Main() throws LoginException {

        this.musicManagers = new HashMap<>();

        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }
    TextChannel channel;
    VoiceChannel connectedChannel;
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        channel = event.getChannel();
        connectedChannel = event.getMember().getVoiceState().getChannel();
        String[] command = event.getMessage().getContentRaw().toLowerCase().split(" ", 2);

        /**
         * Bot Joins and plays music of choice
         */
        if ("~play".equals(command[0]) && command.length == 2) {
            loadAndPlay(event.getChannel(), command[1]);
            /**
             * Skips current song bot is playing
             */
        } else if ("~skip".equals(command[0])) {
            skipTrack(event.getChannel());

            /**
             * Bot joins User's voice channel if possible
             */
        } else if ("~join".equals(command[0])) {
            if (!event.getGuild().getSelfMember().hasPermission(channel, Permission.VOICE_CONNECT)) {
                channel.sendMessage("The bot does not have permission to join the voice channel.").queue();
                return;
            }
            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                channel.sendMessage("You are not in a voice Channel.").queue();
                return;
            }
            AudioManager audioManager = event.getGuild().getAudioManager();
            if (audioManager.isAttemptingToConnect()) {
                channel.sendMessage("The bot is already trying to connect! Chill the fuck out.").queue();
                return;
            }
            audioManager.openAudioConnection(connectedChannel);
            channel.sendMessage("Connected to voice chat.").queue();

            /**
             * Bot leaves voice channel if possible.
             */
        } else if ("~leave".equals(command[0])) {
            VoiceChannel connectedChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();

            if (connectedChannel == null) {
                channel.sendMessage("I am not connected to a voice channel.").queue();
                return;
            }

            event.getGuild().getAudioManager().closeAudioConnection();
            channel.sendMessage("Disconnected from the voice channel!").queue();

            /**
             * Bot rolls specified number of sided dice
             */
        } else if ("~r".equals(command[0])) {
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

                List roll = new ArrayList();
                if (Integer.valueOf(dices) > 100) {
                    event.getChannel().sendMessage("Bruh, I'm not rolling more than 100 Dices.");
                }
                if (Integer.valueOf(dices) <= 100) {

                    for (int i = 0; i < Integer.valueOf(dices); i++) {
                        dice = (int) (Math.random() * Integer.valueOf(sides) + 1);
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

            /**
             * clears a specified number of messages from that channel
             */
        } else if ("~clear".equals(command[0])) {
            if (command.length < 2) {
                EmbedBuilder usage = new EmbedBuilder();
                usage.setColor(0x990000);
                usage.setTitle("Specify Amount of Messages to Delete");
                usage.setDescription("Usage: `~clear [# of messages]`");
                event.getChannel().sendMessage(usage.build()).queue();
            }
            if ((Integer.valueOf(command[1]) >= 100)) {
                event.getChannel().sendMessage("Too many Messages have been selected.").queue();
                event.getChannel().sendMessage("Only 1-99 Messages can be deleted at a time.").queue();
            }
            else {
                List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(command[1]) + 1).complete();
                event.getChannel().purgeMessages(messages);
            }

            /**
             * Gives information about bot
             */
        } else if ("~info".equals(command[0])) {
            if(!event.getMember().getUser().isBot()) {
                EmbedBuilder info = new EmbedBuilder();
                info.setTitle("Kancer DnD Bot Information");
                info.setDescription("Completely useless information about a useless bot.");
                info.addField("Creator","PureKancer", false);
                info.setColor(0x990000);
                info.setThumbnail("https://lh3.googleusercontent.com/-OvLcFJYtmv8/AAAAAAAAAAI/AAAAAAAAAAA/xYRT9-ECzDg/s39-c-k/photo.jpg");

                event.getChannel().sendMessage(info.build()).queue();
            }

            /**
             * Tells who the owner of the server is
             */
        } else if ("~owner".equals(command[0])) {
            String ownerID = event.getGuild().getOwner().getEffectiveName();
            String serverName = event.getGuild().getName();
            event.getChannel().sendMessageFormat("Owner of %s is %s", serverName, ownerID).queue();

            /**
             * Help for commands that bot has
             */
        } else if ("~help".equals(command[0])) {
            if(!event.getMember().getUser().isBot()) {
                EmbedBuilder info = new EmbedBuilder();
                info.setTitle("PureKancer Help info");
                info.setDescription("Here are some commands, and their usages, that you can use");
                info.addField("Commands","~info, ~crackhead, ~nightmare, ~owner, ~ping, ~roll, ~help", false);
                info.addField("Info", "~info", false);
                info.addField("Owner At", "~owner", false);
                info.addField("Ping", "~ping", false);
                info.addField("Roll Die", "~r <Dices>d<Sides>", false);
                info.addField("Help", "~help", false);
                info.addField("Clear", "~clear <Messages>", false);
                info.setColor(0x990000);
                info.setThumbnail("https://lh3.googleusercontent.com/-OvLcFJYtmv8/AAAAAAAAAAI/AAAAAAAAAAA/xYRT9-ECzDg/s39-c-k/photo.jpg");

                event.getChannel().sendMessage(info.build()).queue();
            }

            /**
             * Shows your ping
             */
        } else if ("~ping".equals(command[0])) {
            event.getChannel().sendMessage("Your Ping Pong Ching Chong: **" + event.getJDA().getGatewayPing() + "ms**").queue();
        }

        super.onGuildMessageReceived(event);
    }

    private void loadAndPlay(final TextChannel channel, final String trackUrl) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                channel.sendMessage("Adding to queue " + track.getInfo().title).queue();

                play(channel.getGuild(), musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

                play(channel.getGuild(), musicManager, firstTrack);
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Nothing found by " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Could not play: " + exception.getMessage()).queue();
            }
        });
    }

    private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {

        connectToFirstVoiceChannel(guild.getAudioManager());

        musicManager.scheduler.queue(track);
    }

    private void skipTrack(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.nextTrack();

        channel.sendMessage("Skipped to next track.").queue();
    }

    private void connectToFirstVoiceChannel(AudioManager audioManager) {
        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
            audioManager.openAudioConnection(connectedChannel);
        }
    }
}
