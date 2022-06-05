package me.inao.discordbot.autoload.commands;

import me.inao.dbbp.Config;
import me.inao.dbbp.annotations.Autoload;
import me.inao.dbbp.annotations.Command;
import me.inao.dbbp.annotations.Inject;
import me.inao.dbbp.enums.AutoloadType;
import me.inao.dbbp.interfaces.IArgument;
import me.inao.dbbp.interfaces.ICommand;
import me.inao.dbbp.persistant.StorageUnit;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.util.HashMap;

@Autoload(type = AutoloadType.COMMAND)
@Command(name = "help", description = "Shows help for a command", optionalArguments = {me.inao.discordbot.autoload.arguments.Message.class})
public class Help implements ICommand {

    @Inject(outside = true)
    private Config config;

    @Inject
    private StorageUnit storageUnit;

    @Inject(function = "getMessage")
    private Message message;

    @Override
    public boolean process(HashMap<Class<? extends IArgument>, Object> arguments) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setDescription("Helping everyone is my pleasure, you know :eyes:").setTitle("Help");
        builder.setFooter("Prefix for commands is " + config.getPrefix());

        if(arguments == null) {
            storageUnit.getCommandOverviewMap().values().forEach(val -> {
                if(config.isCommandEnabled(val.getSimpleName())){
                    Command commandAnnotation = val.getAnnotation(Command.class);
                    builder.addField(commandAnnotation.name(), commandAnnotation.description(), false);
                }
            });
        }
        new MessageBuilder().setEmbed(builder).send(message.getChannel());
        return true;
    }
}
