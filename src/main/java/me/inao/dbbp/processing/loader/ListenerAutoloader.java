package me.inao.dbbp.processing.loader;

import lombok.RequiredArgsConstructor;
import me.inao.dbbp.enums.AutoloadType;
import me.inao.dbbp.processing.annotations.Autoload;
import me.inao.dbbp.processing.injectors.InjectorHandler;
import me.inao.dbbp.processing.persistant.StorageUnit;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.listener.GloballyAttachableListener;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

@RequiredArgsConstructor
class ListenerAutoloader{

    private final DiscordApiBuilder apiBuilder;
    private final StorageUnit storageUnit;
    private final String prefix;

    public void start() {
        Reflections reflections = new Reflections(prefix);
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Autoload.class);
        for(Class<?> cls : classSet){
            if(cls.isAnnotationPresent(Autoload.class)){
                Autoload autoload = cls.getAnnotation(Autoload.class);
                if(autoload.type() == AutoloadType.LISTENER){
                    try{
                        Object clsInst = cls.getDeclaredConstructor(new Class[]{StorageUnit.class}).newInstance(storageUnit);
                        (new InjectorHandler(storageUnit)).injectionHandler(clsInst, null);
                        apiBuilder.addListener((GloballyAttachableListener) clsInst);
                    } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
