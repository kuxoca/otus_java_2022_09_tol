package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.exception.MyException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws MyException {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) throws MyException {
        checkConfigClass(configClass);

        Object instance = null;
        try {
            instance = configClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new MyException();
        }
        var methods = configClass.getMethods();

        var sortedAnnotateMethods = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparing(method -> method.getAnnotation(AppComponent.class).order()))
                .toList();

        for (Method method : sortedAnnotateMethods) {
            var types = method.getParameterTypes();

            Object[] args = new Object[types.length];

            for (int i = 0; i < types.length; i++) {
                args[i] = getAppComponent(types[i]);
            }

            if (appComponentsByName.containsKey(method.getAnnotation(AppComponent.class).name())) {
                throw new MyException();
            }

            Object obj = null;
            try {
                obj = method.invoke(instance, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new MyException();
            }
            appComponents.add(obj);
            appComponentsByName.put(method.getAnnotation(AppComponent.class).name(), obj);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {

        Object component = null;
        for (Object appComponent : appComponents) {
            if (componentClass.isAssignableFrom(appComponent.getClass())) {
                if (component != null) {
                    throw new MyException();
                }
                component = appComponent;
            }
        }
        if (component != null) {
            return (C) component;
        }
        throw new MyException();
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
