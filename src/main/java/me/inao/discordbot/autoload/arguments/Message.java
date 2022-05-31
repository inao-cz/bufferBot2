package me.inao.discordbot.autoload.arguments;

import me.inao.dbbp.annotations.Argument;
import me.inao.dbbp.annotations.Autoload;
import me.inao.dbbp.enums.AutoloadType;
import me.inao.dbbp.interfaces.IArgument;

@Autoload(type = AutoloadType.ARGUMENT)
@Argument(type = String.class, name = "message", usage = "message", aliases = {"m"})
public class Message implements IArgument {
    @Override
    public Object getParsedValue(String value) {
        return value;
    }
}
