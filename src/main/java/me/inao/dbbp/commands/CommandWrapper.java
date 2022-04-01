package me.inao.dbbp.commands;

import lombok.RequiredArgsConstructor;
import me.inao.discordbot.interfaces.ICommand;
import me.inao.dbbp.injectors.InjectorHandler;
import me.inao.dbbp.persistant.StorageUnit;

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
            command.process();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
