package me.inao.dbbp.processing.commands;

import lombok.Setter;
import me.inao.dbbp.processing.persistant.StorageUnit;
import org.javacord.api.DiscordApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CommandsParser {
    @Setter
    private DiscordApi api;

    @Setter
    private StorageUnit loader;

    @Setter
    private String commandPrefix;

//    public List<Class<?>> getParsedValues(String[] parts){
//        List<Class<?>> parameters = new ArrayList<>();
//        if(loader.getParameterList() == null) return null;
//        for (String part : parts){
//            if(checkForCommandPair(part)) continue;
//            String[] partSplit = part.split("\\s");
//            Optional<Class<?>> parameter = loader.getParameterList().entrySet().stream().filter(listIParameterEntry -> listIParameterEntry.getKey().stream().anyMatch(entry -> entry.matches(partSplit[0]))).map(Map.Entry::getValue).findAny();
//            if(parameter.isPresent()){
//                String[] modifiedArray = Arrays.copyOfRange(partSplit, 1, partSplit.length);
//                IParameter param = parameter.get();
//                param.onParse(this.api, String.join(" ", Arrays.asList(modifiedArray)));
//                parameters.add(param);
//            }
//        }
//        return parameters;
//    }

    public String[] getParsedCommand(String message){
        return message.split("\\s-");
    }

    public boolean checkForParamPair(String splitMessage){
        return splitMessage.matches("^[\\-].+\\s.+$");
    }

    public boolean checkForCommandPair(String splitMessage){
        return splitMessage.matches("^" + commandPrefix + ".+(\\s.+)?$");
    }
}
