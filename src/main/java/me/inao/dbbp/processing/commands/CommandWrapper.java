package me.inao.dbbp.processing.commands;

import lombok.RequiredArgsConstructor;
import me.inao.dbbp.interfaces.ICommand;
import me.inao.dbbp.processing.injectors.InjectorHandler;
import me.inao.dbbp.processing.persistant.StorageUnit;

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
