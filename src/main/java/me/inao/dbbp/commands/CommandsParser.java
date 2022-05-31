package me.inao.dbbp.commands;

import lombok.Setter;
import me.inao.dbbp.interfaces.IArgument;
import me.inao.dbbp.persistant.StorageUnit;
import org.javacord.api.DiscordApi;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandsParser {
    @Setter
    private DiscordApi api;

    @Setter
    private String commandPrefix;

    public HashMap<Class<? extends IArgument>, Object> getParsedArguments(String message, StorageUnit storageUnit) {
        HashMap<Class<? extends IArgument>, Object> arguments = new HashMap<>();
        if(storageUnit.getArgumentsOverviewMap().isEmpty()) return null;
        String[] parts = getParsedCommand(message);

        for (String part : parts){
            if(checkForCommandPair(part)) continue;
            String[] partSplit = part.split("\\s");
            Optional<? extends Class<? extends IArgument>> parameter = storageUnit.getArgumentsOverviewMap().entrySet().stream()
                    .filter(listIParameterEntry -> Arrays.stream(listIParameterEntry.getKey()).anyMatch(entry -> entry.matches(partSplit[0])))
                    .map(Map.Entry::getValue).findAny();
            if(parameter.isPresent()){
                String[] modifiedArray = Arrays.copyOfRange(partSplit, 1, partSplit.length);
                try{
                    IArgument param = parameter.get().getDeclaredConstructor().newInstance();
                    arguments.put(parameter.get(), param.getParsedValue(String.join(" ", Arrays.asList(modifiedArray))));
                }catch (Exception ignored){}
            }
        }
        return arguments;
    }

    public HashMap<Class<? extends IArgument>, Object> getParsedSlashCommandsArguments(){
        return null;
    }

    private String[] getParsedCommand(String message){
        return message.split("\\s-");
    }

    private boolean checkForParamPair(String splitMessage){
        return splitMessage.matches("^[\\-].+\\s.+$");
    }

    private boolean checkForCommandPair(String splitMessage){
        return splitMessage.matches("^" + commandPrefix + ".+(\\s.+)?$");
    }
}
