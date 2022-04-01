package me.inao.dbbp.injectors;

import lombok.RequiredArgsConstructor;
import me.inao.dbbp.annotations.Inject;
import me.inao.dbbp.annotations.Stateful;
import me.inao.dbbp.annotations.Stateless;
import me.inao.dbbp.persistant.StorageUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class InjectorHandler {

    private final StorageUnit storageUnit;

    Logger logger = LogManager.getLogger(InjectorHandler.class);

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
                        logError("Field " + injectData.field() + " in " + field.getType() + " not found..", noSuchFieldException);
                    } catch (IllegalAccessException illegalAccessException) {
                        logError("Illegal access to " + injectData.field() + " in " + field.getType(), illegalAccessException);
                    }
                } else if (injectData.function().length() > 0) {
                    try {
                        Method method = giver.getClass().getMethod(injectData.function().replace("()", ""));
                        val = method.invoke(giver);
                    } catch (NoSuchMethodException noSuchMethodException) {
                        logError("No such method as " + injectData.function() + "found in " + giver.getClass().getName(), noSuchMethodException);
                    } catch (InvocationTargetException e) {
                        logError("Cannot invoke.", e);
                    } catch (IllegalAccessException illegalAccessException) {
                        logError("Illegal access to " + injectData.function() + " in " + giver.getClass().getName(), illegalAccessException);
                    }
                } else if (injectData.outside()) {
                    List<Field> fieldList = Arrays.stream(storageUnit.getClass().getDeclaredFields())
                            .filter(fl -> fl.getType().equals(field.getType()))
                            .collect(Collectors.toList());
                    try {
                        if (fieldList.size() == 1) {
                            fieldList.get(0).setAccessible(true);
                            val = fieldList.get(0).get(storageUnit);
                            fieldList.get(0).setAccessible(false);
                        } else if (fieldList.size() > 1 && injectData.sequenceNumber() != 0 && injectData.sequenceNumber() <= fieldList.size()) {
                            val = fieldList.get(injectData.sequenceNumber());
                        } else {
                            logger.log(Level.ERROR, "There was a problem with injecting your object outside of lentils system. Please, check if your field exists.");
                        }
                    } catch (IllegalAccessException illegalAccessException) {
                        illegalAccessException.printStackTrace();
                    }
                } else {
                    try {
                        if (field.getType().isAnnotationPresent(Stateless.class)) {
                            val = field.getType().getDeclaredConstructor().newInstance();
                            new InjectorHandler(storageUnit).injectionHandler(val, null);
                        } else if (field.getType().isAnnotationPresent(Stateful.class)) {
                            List<String> lentilContainers = storageUnit.getStatefulLentilsSoup().keySet().stream()
                                    .filter(lentilName -> lentilName.startsWith(field.getType().getSimpleName()))
                                    .collect(Collectors.toList());
                            if (lentilContainers.size() > 1) {
                                val = storageUnit.getStatefulLentilsSoup().get(lentilContainers.get(0)).getLentil();
                            } else if (injectData.sequenceNumber() >= 0 && injectData.sequenceNumber() <= lentilContainers.size()) {
                                val = storageUnit.getStatefulLentilsSoup().get(lentilContainers.get(injectData.sequenceNumber())).getLentil();
                            } else {
                                logger.log(Level.ERROR, "There was a problem with injecting your stateful lentil. Most probably you've entered incorrect sequence number or your lentil doesn't exist");
                            }
                        }
                    } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException noSuchMethodException) {
                        logger.log(Level.ERROR, noSuchMethodException);
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

    private void logError(String message, Throwable exception) {
        logger.log(Level.ERROR, exception);
        logger.log(Level.ERROR, message);
    }
}
