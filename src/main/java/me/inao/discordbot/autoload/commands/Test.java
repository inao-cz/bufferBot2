package me.inao.discordbot.autoload.commands;

import me.inao.dbbp.annotations.Command;
import me.inao.dbbp.interfaces.IArgument;
import me.inao.discordbot.lentils.MessageSenderLentil;
import me.inao.dbbp.annotations.Autoload;
import me.inao.dbbp.annotations.Inject;
import me.inao.dbbp.enums.AutoloadType;
import me.inao.dbbp.interfaces.ICommand;
import me.inao.dbbp.annotations.Permission;
import me.inao.discordbot.statics.Colors;
import org.javacord.api.entity.message.Message;

import java.awt.*;
import java.util.HashMap;

@Permission(PermissionMask = 8)
@Autoload(type = AutoloadType.COMMAND)
@Command(name = "test", description = "Testing command.")
public class Test implements ICommand {
    @Inject
    private MessageSenderLentil messageSenderLentil;
    @Inject(function = "getMessage")
    private Message message;

    @Override
    public boolean process(HashMap<Class<? extends IArgument>, Object> arguments) {
        messageSenderLentil.sendEmbedMessage("Testing random", "This is my random color from QRN Generator.", Color.decode(Colors.regenerateRandomColor()), message.getChannel());
        return false;
    }
}
