package me.inao.discordbot.autoload.arguments;

import me.inao.dbbp.annotations.Argument;
import me.inao.dbbp.annotations.Autoload;
import me.inao.dbbp.enums.AutoloadType;
import me.inao.dbbp.interfaces.IArgument;
import org.javacord.api.util.DiscordRegexPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@Autoload(type = AutoloadType.ARGUMENT)
@Argument(type = String[].class, name = "user", usage = "user", aliases = {"usr", "user"})
public class User implements IArgument {
    @Override
    public Object getParsedValue(String value) {
        final List<String> parsedUsers = new ArrayList<>();
        String[] values = value.split("[,:;\\-_/\\s]");
        for (String val : values){
            String id = parseIdFromMention(val);
            if(id != null){
                parsedUsers.add(id);
            }
        }
        return parsedUsers.toArray();
    }

    private String parseIdFromMention(String value) {
        String val = null;
        if(value.matches(DiscordRegexPattern.USER_MENTION.pattern())){
            Matcher matcher = DiscordRegexPattern.USER_MENTION.matcher(value);
            if(matcher.find()) val = matcher.group("id");
        }
        return val;
    }
}
