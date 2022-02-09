package me.inao.dbbp.processing.commands;

import me.inao.dbbp.processing.annotations.Permission;
import me.inao.discordbot.interfaces.ICommand;
import me.inao.dbbp.processing.persistant.StorageUnit;
import org.javacord.api.entity.message.Message;

import java.util.Map;
import java.util.Optional;

public class CommandsProcessor {
    public boolean startCommandIfAny(Message msg, Object event, StorageUnit storageUnit){
//        CommandParser parser = new CommandParser();
//        parser.setApi(instance.getApi());
//        parser.setLoader(instance.getLoader());
//        parser.setCommandPrefix(String.valueOf(instance.getConfig().getPrefix()));
//        String[] parsed = parser.getParsedCommand(message.getContent());
//        List<IParameter> parameterList = parser.getParsedValues(parsed);
//
//        String cmd = "";
//        for(String parse : parsed){
//            if(parser.checkForCommandPair(parse)){
//                cmd = parse;
//                break;
//            }
//        }
        String finalCmd = msg.getContent().replace(String.valueOf(storageUnit.getConfig().getPrefix()), "");
        Optional<? extends Class<? extends ICommand>> command = storageUnit.getCommandOverviewMap().entrySet()
                .stream()
                .filter(entry -> finalCmd.equalsIgnoreCase(entry.getKey()))
                .map(Map.Entry::getValue)
                .findAny();

        if(command.isPresent()) {
            if(command.get().isAnnotationPresent(Permission.class)){
                if (!storageUnit.getPermissionCheck().checkPermission(msg, command.get())) return false;
            }
            new Thread(new CommandWrapper(command.get(), event, storageUnit)).start();
            return true;
        }
        return false;
    }
}
