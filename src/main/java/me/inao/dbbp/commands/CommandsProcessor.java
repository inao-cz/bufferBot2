package me.inao.dbbp.commands;

import me.inao.dbbp.annotations.Permission;
import me.inao.dbbp.interfaces.ICommand;
import me.inao.dbbp.persistant.StorageUnit;
import org.javacord.api.entity.message.Message;

import java.util.Map;
import java.util.Optional;

public class CommandsProcessor {
    public boolean startCommandIfAny(Message msg, Object event, StorageUnit storageUnit){
        String finalCmd = msg.getContent().replace(String.valueOf(storageUnit.getConfig().getPrefix()), "").split(" ")[0];
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
            System.gc();
            return true;
        }
        return false;
    }
}
