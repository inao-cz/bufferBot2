package me.inao.discordbot.lentils;

import me.inao.dbbp.Config;
import me.inao.discordbot.exception.NoSuchServerTextChannelException;
import me.inao.dbbp.annotations.Inject;
import me.inao.dbbp.annotations.Stateless;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Stateless
public class LoggerLentil {

    @Inject(outside = true)
    private Config config;

    @Inject(outside = true)
    private DiscordApi api;

    public void log(Class<?> source, String text, Level level){
        this.log(source, "", text, false, level);
    }

    public void log(Class<?> source, String embedTitle, String text, boolean isDiscord, Level level){
        new Thread(() -> {
                LogManager.getLogger(source).log(level, text);
                if(isDiscord){
                    Color color;
                    switch (level.name().toLowerCase()) {
                        case "info":
                            color = Color.BLUE;
                            break;
                        case "warn":
                            color = Color.ORANGE;
                            break;
                        case "error":
                            color = Color.RED;
                            break;
                        case "fatal":
                            color = Color.BLACK;
                            break;
                        default:
                            color = Color.GRAY;
                    }
                        new MessageBuilder().setEmbed(
                                new EmbedBuilder()
                                        .setTitle(embedTitle)
                                        .setDescription(text)
                                        .setColor(color)
                                        .setFooter("Level: " + level.name() + " | At: " + new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(new Date()))
                        ).send(api.getChannelsByName(config.getFeatureChannel("Logger")).iterator().next().asServerTextChannel().orElseThrow(NoSuchServerTextChannelException::new));
                }
        }).start();
    }
}
