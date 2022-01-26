package me.inao.dbbp.autoload.listeners;

import lombok.RequiredArgsConstructor;
import me.inao.dbbp.processing.annotations.Autoload;
import me.inao.dbbp.enums.AutoloadType;
import me.inao.dbbp.processing.commands.CommandsProcessor;
import me.inao.dbbp.processing.persistant.StorageUnit;
import org.javacord.api.event.message.MessageCreateEvent;

@RequiredArgsConstructor
@Autoload(type = AutoloadType.LISTENER)
public class MessageCreateListener implements org.javacord.api.listener.message.MessageCreateListener {

    private final StorageUnit storageUnit;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if(event.getMessageAuthor().isBotUser() || event.getMessage().isPrivateMessage()){
            return;
        }
        if (!event.getMessageContent().isEmpty() && event.getMessage().getContent().startsWith(String.valueOf(storageUnit.getConfig().getPrefix()))) {
            if(!new CommandsProcessor().startCommandIfAny(event.getMessage(), event, storageUnit)){
                event.getMessage().getChannel().sendMessage("Unknown command!");
            }
        }
    }
}
