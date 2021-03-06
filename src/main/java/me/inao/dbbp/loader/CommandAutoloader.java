package me.inao.dbbp.loader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.inao.dbbp.annotations.Autoload;
import me.inao.dbbp.enums.AutoloadType;
import me.inao.dbbp.interfaces.ICommand;
import me.inao.dbbp.persistant.StorageUnit;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Set;

@RequiredArgsConstructor
class CommandAutoloader{
    private final StorageUnit unit;
    private final String prefix;

    @Getter
    private final HashMap<String, Class<? extends ICommand>> map = new HashMap<>();

    public Runnable load() {
        return () -> {
            Reflections reflections = new Reflections(prefix);
            Set<Class<? extends ICommand>> classes = reflections.getSubTypesOf(ICommand.class);
            classes.forEach(commandClass -> {
                if(commandClass.isAnnotationPresent(Autoload.class)){
                    AutoloadType type = commandClass.getAnnotation(Autoload.class).type();
                    if(type == AutoloadType.COMMAND && unit.getConfig().isCommandEnabled(commandClass.getSimpleName())){
                        map.put(commandClass.getSimpleName(), commandClass);
                    }
                }
            });
            unit.setCommandOverviewMap(map);
        };
    }
}
