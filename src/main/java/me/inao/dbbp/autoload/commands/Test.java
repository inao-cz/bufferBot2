package me.inao.dbbp.autoload.commands;

import me.inao.dbbp.lentils.MessageSenderUtil;
import me.inao.dbbp.processing.annotations.Autoload;
import me.inao.dbbp.processing.annotations.Inject;
import me.inao.dbbp.enums.AutoloadType;
import me.inao.dbbp.interfaces.ICommand;
import me.inao.dbbp.processing.annotations.Permission;
import me.inao.dbbp.statics.Colors;
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
        messageSenderUtil.sendEmbedMessage("Testing inform", "This is my testing message. Hello :)", Color.decode(Colors.COLOR_INFORM), message.getChannel());
        messageSenderUtil.sendEmbedMessage("Testing error", "This is my testing message. Hello :)", Color.decode(Colors.COLOR_ERROR), message.getChannel());
        messageSenderUtil.sendEmbedMessage("Testing warning", "This is my testing message. Hello :)", Color.decode(Colors.COLOR_WARNING), message.getChannel());
    }
}
