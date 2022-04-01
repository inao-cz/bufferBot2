package me.inao.discordbot.autoload.commands;

import me.inao.discordbot.lentils.MessageSenderLentil;
import me.inao.dbbp.annotations.Autoload;
import me.inao.dbbp.annotations.Inject;
import me.inao.dbbp.enums.AutoloadType;
import me.inao.discordbot.interfaces.ICommand;
import me.inao.dbbp.annotations.Permission;
import me.inao.discordbot.statics.Colors;
import org.javacord.api.entity.message.Message;

import java.awt.*;

@Permission(PermissionMask = 8)
@Autoload(type = AutoloadType.COMMAND)
public class Test implements ICommand {

    @Inject
    private MessageSenderLentil messageSenderLentil;

    @Inject(function = "getMessage")
    private Message message;

    @Override
    public void process() {
        messageSenderLentil.sendEmbedMessage("Testing random", "This is my random color from QRN Generator.", Color.decode(Colors.regenerateRandomColor()), message.getChannel());
    }
}
