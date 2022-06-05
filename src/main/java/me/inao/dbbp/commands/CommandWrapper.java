package me.inao.dbbp.commands;

import lombok.RequiredArgsConstructor;
import me.inao.dbbp.annotations.Command;
import me.inao.dbbp.interfaces.IArgument;
import me.inao.dbbp.interfaces.ICommand;
import me.inao.dbbp.injectors.InjectorHandler;
import me.inao.dbbp.persistant.StorageUnit;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.HashMap;

@RequiredArgsConstructor
public class CommandWrapper implements Runnable{
    private final Class<? extends ICommand> commandClass;
    private final Object event;
    private final StorageUnit storageUnit;
    @Override
    public void run() {
        try{
            ICommand command = this.commandClass.getDeclaredConstructor().newInstance();
            (new InjectorHandler(storageUnit)).injectionHandler(command, event);
            HashMap<Class<? extends IArgument>, Object> args = null;
            if(command.getClass().isAnnotationPresent(Command.class)){
                Command cmdAnnotation = command.getClass().getAnnotation(Command.class);
                if(cmdAnnotation.requiredArguments() != null && cmdAnnotation.requiredArguments().length > 0){
                    if(!storageUnit.getConfig().isSlashcommands()) args = new CommandsParser().getParsedArguments(((MessageCreateEvent)event).getMessageContent(), storageUnit);
                }
                if(cmdAnnotation.requiredArguments() != null && cmdAnnotation.requiredArguments().length > 0 && (args == null || cmdAnnotation.requiredArguments().length != args.size())){
                    ((MessageCreateEvent)event).getChannel().sendMessage("You need to provide all required arguments!");
                    return;
                }
            }
            boolean processed = command.process(args);
            if(processed) Thread.currentThread().interrupt();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
