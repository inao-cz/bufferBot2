package me.inao.discordbot.autoload.listeners;

import lombok.RequiredArgsConstructor;
import me.inao.discordbot.lentils.LoggerLentil;
import me.inao.dbbp.annotations.Autoload;
import me.inao.dbbp.enums.AutoloadType;
import me.inao.dbbp.annotations.Inject;
import me.inao.dbbp.commands.CommandsProcessor;
import me.inao.dbbp.persistant.StorageUnit;
import org.apache.logging.log4j.Level;
import org.javacord.api.DiscordApi;
import org.javacord.api.event.message.MessageCreateEvent;

@RequiredArgsConstructor
@Autoload(type = AutoloadType.LISTENER)
public class MessageCreateListener implements org.javacord.api.listener.message.MessageCreateListener {

    private final StorageUnit storageUnit;

    @Inject
    private DiscordApi api;

    @Inject
    private LoggerLentil logger;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if(event.getMessageAuthor().isBotUser() || event.getMessage().isPrivateMessage()){
            return;
        }
        if (!event.getMessageContent().isEmpty() && event.getMessage().getContent().startsWith(String.valueOf(storageUnit.getConfig().getPrefix()))) {
            if(!new CommandsProcessor().startCommandIfAny(event.getMessage(), event, storageUnit)){
                event.getMessage().getChannel().sendMessage("Unknown command!");
            }
            logger.log(this.getClass(), "Exec try", "User has tried to execute command. " + event.getMessage(), true, Level.INFO);
        }
    }
}
