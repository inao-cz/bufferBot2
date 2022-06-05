package me.inao.discordbot.autoload.commands;

import me.inao.dbbp.Config;
import me.inao.dbbp.annotations.Autoload;
import me.inao.dbbp.annotations.Command;
import me.inao.dbbp.annotations.Inject;
import me.inao.dbbp.annotations.Permission;
import me.inao.dbbp.enums.AutoloadType;
import me.inao.dbbp.interfaces.IArgument;
import me.inao.dbbp.interfaces.ICommand;
import me.inao.dbbp.persistant.StorageUnit;
import me.inao.discordbot.lentils.MessageSenderLentil;
import org.javacord.api.entity.channel.TextChannel;

import java.awt.*;
import java.util.HashMap;

@Permission(PermissionMask = 8)
@Autoload(type = AutoloadType.COMMAND)
@Command(name = "reload", description = "Reloads the bot config")
public class Reload implements ICommand {
    @Inject(outside = true)
    private Config config;

    @Inject
    private StorageUnit storageUnit;

    @Inject
    private MessageSenderLentil messageSenderLentil;

    @Inject(function = "getChannel")
    private TextChannel channel;

    @Override
    public boolean process(HashMap<Class<? extends IArgument>, Object> arguments) {
        config.loadConfig(storageUnit);
        messageSenderLentil.sendEmbedMessage("Reloaded config", "Success.", Color.GREEN, channel);
        return true;
    }
}
