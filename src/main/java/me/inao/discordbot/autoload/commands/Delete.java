package me.inao.discordbot.autoload.commands;

import me.inao.dbbp.annotations.Autoload;
import me.inao.dbbp.annotations.Command;
import me.inao.dbbp.annotations.Inject;
import me.inao.dbbp.annotations.Permission;
import me.inao.dbbp.enums.AutoloadType;
import me.inao.dbbp.interfaces.IArgument;
import me.inao.dbbp.interfaces.ICommand;
import me.inao.discordbot.autoload.arguments.Count;
import org.javacord.api.entity.message.Message;

import java.util.HashMap;

@Permission(PermissionMask = 8)
@Autoload(type = AutoloadType.COMMAND)
@Command(name = "delete", description = "Delete last n messages in the chat room", requiredArguments = {Count.class})
public class Delete implements ICommand {

    @Inject(function = "getMessage")
    private Message message;

    @Override
    public boolean process(HashMap<Class<? extends IArgument>, Object> arguments) {
        int count = (int) arguments.get(Count.class);
        message.getChannel().getMessages(count + 1).join().forEach(Message::delete);
        return false;
    }
}
