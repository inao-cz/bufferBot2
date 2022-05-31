package me.inao.dbbp.loader;

import lombok.RequiredArgsConstructor;
import me.inao.dbbp.annotations.Argument;
import me.inao.dbbp.annotations.Autoload;
import me.inao.dbbp.enums.AutoloadType;
import me.inao.dbbp.interfaces.IArgument;
import me.inao.dbbp.persistant.StorageUnit;
import org.reflections.Reflections;

import java.util.Set;

@RequiredArgsConstructor
public class ArgumentsHandler {
    private final StorageUnit unit;

    private final String packageName;

    public Runnable load() {
        return () -> {
            Reflections reflections = new Reflections(packageName);
            Set<Class<? extends IArgument>> classes = reflections.getSubTypesOf(IArgument.class);
            classes.forEach(argumentClass -> {
                if (argumentClass.isAnnotationPresent(Autoload.class) && argumentClass.isAnnotationPresent(Argument.class)) {
                    AutoloadType type = argumentClass.getAnnotation(Autoload.class).type();
                    if (type == AutoloadType.ARGUMENT && unit.getConfig().isArgumentEnabled(argumentClass.getSimpleName())) {
                        unit.getArgumentsOverviewMap().put(argumentClass.getAnnotation(Argument.class).aliases(), argumentClass);
                    }
                }
            });
        };
    }
}
