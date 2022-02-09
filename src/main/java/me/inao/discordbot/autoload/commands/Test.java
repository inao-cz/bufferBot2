package me.inao.discordbot.autoload.commands;

import me.inao.discordbot.lentils.MessageSenderUtil;
import me.inao.dbbp.processing.annotations.Autoload;
import me.inao.dbbp.processing.annotations.Inject;
import me.inao.discordbot.enums.AutoloadType;
import me.inao.discordbot.interfaces.ICommand;
import me.inao.dbbp.processing.annotations.Permission;
import me.inao.discordbot.statics.Colors;
import org.javacord.api.entity.message.Message;

import java.awt.*;

@Permission(PermissionMask = 8)
@Autoload(type = AutoloadType.COMMAND)
public class Test implements ICommand {

    @Inject
    private MessageSenderUtil messageSenderUtil;

    @Inject(function = "getMessage")
    private Message message;

    @Override
    public void process() {
        messageSenderUtil.sendEmbedMessage("Testing success", "This is my testing message. Hello :)", Color.decode(Colors.COLOR_SUCCESS), message.getChannel());
    }
}
