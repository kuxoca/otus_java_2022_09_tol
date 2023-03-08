package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        checkConfigClass(configClass);

        var instance = configClass.getConstructor().newInstance();
        var methods = configClass.getMethods();

        var sortedAnnotateMethods = Arrays.stream(methods).filter(method -> method.isAnnotationPresent(AppComponent.class)).sorted(Comparator.comparing(method -> method.getAnnotation(AppComponent.class).order())).toList();

        for (Method method : sortedAnnotateMethods) {
            var types = method.getParameterTypes();

            Object[] args = new Object[types.length];

            for (int i = 0; i < types.length; i++) {
                for (Object el : appComponents) {
                    if (types[i].isAssignableFrom(el.getClass())) {
                        args[i] = el;
                        break;
                    }
                }
                if (args[i] == null) {
                    throw new NoSuchMethodException();
                }
            }

            if (appComponentsByName.containsKey(method.getAnnotation(AppComponent.class).name())) {
                throw new NoSuchMethodException();
            }

            var obj = method.invoke(instance, args);
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
    public <C> C getAppComponent(Class<C> componentClass) throws NoSuchMethodException {

        Object component = null;
        for (Object appComponent : appComponents) {
            if (componentClass.isAssignableFrom(appComponent.getClass())) {
                if (component != null) {
                    throw new NoSuchMethodException();
                }
                component = appComponent;
            }
        }
        if (component != null) {
            return (C) component;
        }
        throw new NoSuchMethodException();
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
