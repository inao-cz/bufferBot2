package me.inao.dbbp.processing.lentil;

import lombok.RequiredArgsConstructor;
import me.inao.dbbp.processing.annotations.Stateful;
import me.inao.dbbp.processing.persistant.StorageUnit;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Set;

@RequiredArgsConstructor
public class LentilHandler {

    private final StorageUnit unit;

    public void loadStatefulLentils(String packageName){
        Reflections refl = new Reflections(packageName);
        Set<Class<?>> lentils = refl.getTypesAnnotatedWith(Stateful.class);
        ArrayList<String> addedLentils = new ArrayList<>();
        lentils.forEach(cls -> {
            int x = 0;
            for (String addedLentil : addedLentils) {
                if (addedLentil.startsWith(cls.getSimpleName())) x++;
            }
            try{
                StatefulLentilContainer container = new StatefulLentilContainer();
                container.setLentil(cls.getDeclaredConstructor().newInstance());
                unit.getStatefulLentilsSoup().put(cls.getSimpleName() + x, container);
                addedLentils.add(cls.getSimpleName() + x);
            }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
                e.printStackTrace();
            }
        });
    }
}
