package me.inao.discordbot.autoload.arguments;

import me.inao.dbbp.annotations.Argument;
import me.inao.dbbp.annotations.Autoload;
import me.inao.dbbp.enums.AutoloadType;
import me.inao.dbbp.interfaces.IArgument;

@Autoload(type = AutoloadType.ARGUMENT)
@Argument(type = Integer.class, name = "count", usage = "count", aliases = {"num", "number"})
public class Count implements IArgument {

    @Override
    public Object getParsedValue(String value) {
        if(Integer.parseInt(value) > (Integer.MAX_VALUE - 1)) return Long.parseLong(value);
        return Integer.parseInt(value);
    }
}