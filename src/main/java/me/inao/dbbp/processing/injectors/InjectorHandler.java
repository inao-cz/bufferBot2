package me.inao.dbbp.processing.injectors;

import lombok.RequiredArgsConstructor;
import me.inao.dbbp.processing.annotations.Inject;
import me.inao.dbbp.processing.annotations.Stateful;
import me.inao.dbbp.processing.annotations.Stateless;
import me.inao.dbbp.processing.lentil.StatefulLentilContainer;
import me.inao.dbbp.processing.persistant.StorageUnit;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class InjectorHandler {

    private final StorageUnit storageUnit;

    public void injectionHandler(Object target, Object giver) {
        Arrays.stream(target.getClass().getDeclaredFields()).forEach(field -> {
            if (field.isAnnotationPresent(Inject.class)) {
                Inject injectData = field.getAnnotation(Inject.class);
                Object val = null;
                if (injectData.field().length() > 0) {
                    try {
                        Field giverField = giver.getClass().getField(injectData.field());
                        giverField.setAccessible(true);
                        val = giverField.get(giver);
                        giverField.setAccessible(false);
                    } catch (NoSuchFieldException noSuchFieldException) {
                        System.out.println("Field " + injectData.field() + " in " + field.getType() + " not found..");
                        noSuchFieldException.printStackTrace();
                    } catch (IllegalAccessException illegalAccessException) {
                        System.out.println("Illegal access to " + injectData.field() + " in " + field.getType());
                        illegalAccessException.printStackTrace();
                    }
                } else if (injectData.function().length() > 0) {
                    try {
                        Method method = giver.getClass().getMethod(injectData.function().replace("()", ""));
                        val = method.invoke(giver);
                    } catch (NoSuchMethodException noSuchMethodException) {
                        System.out.println("No such method as " + injectData.function() + "found in " + giver.getClass().getName());
                        noSuchMethodException.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException illegalAccessException) {
                        System.out.println("Illegal access to " + injectData.function() + " in " + giver.getClass().getName());
                        illegalAccessException.printStackTrace();
                    }
                } else {
                    try {
                        if (field.getType().isAnnotationPresent(Stateless.class)) {
                            val = field.getType().getDeclaredConstructor().newInstance();
                        } else if (field.getType().isAnnotationPresent(Stateful.class)) {
                            List<String> lentilContainers = storageUnit.getStatefulLentilsContainerMap().keySet().stream()
                                    .filter(lentilName -> lentilName.startsWith(field.getType().getSimpleName()))
                                    .collect(Collectors.toList());
                            if (lentilContainers.size() == 1) {
                                val = storageUnit.getStatefulLentilsContainerMap().get(lentilContainers.get(0)).getLentil();
                            } else if(lentilContainers.size() > 1 && injectData.sequenceNumber() > 1 && injectData.sequenceNumber() <= lentilContainers.size()){
                                val = storageUnit.getStatefulLentilsContainerMap().get(lentilContainers.get(injectData.sequenceNumber())).getLentil();
                            }
                            else {
                                System.out.println("There was a problem with injecting your stateful lentil. Most probably you've entered incorrect sequence number or your lentil doesn't exist");
                            }
                        }
                    } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException noSuchMethodException) {
                        noSuchMethodException.printStackTrace();
                    }
                }
                if (val != null) {
                    field.setAccessible(true);
                    try {
                        field.set(target, val);
                    } catch (IllegalAccessException illegalAccessException) {
                        illegalAccessException.printStackTrace();
                    }
                    field.setAccessible(false);
                }
            }
        });
    }
}
