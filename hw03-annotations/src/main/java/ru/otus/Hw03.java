package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Hw03 {

    public static void zapuskalka(String clazzName) throws ClassNotFoundException {
        runTestMethod(getAnnotatedMethods(clazzName), clazzName);
    }

    private static Map<Class, Set<Method>> getAnnotatedMethods(String clazzName) throws ClassNotFoundException {
        var methods = Class.forName(clazzName).getDeclaredMethods();

        Set<Method> setBefore = new HashSet<>();
        Set<Method> setAfter = new HashSet<>();
        Set<Method> setTest = new HashSet<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                setBefore.add(method);
            }
            if (method.isAnnotationPresent(After.class)) {
                setAfter.add(method);
            }
            if (method.isAnnotationPresent(Test.class)) {
                setTest.add(method);
            }
        }
        Map<Class, Set<Method>> map = new HashMap<>();

        map.put(Test.class, setTest);
        map.put(After.class, setAfter);
        map.put(Before.class, setBefore);
        return map;
    }

    private static void runTestMethod(Map<Class, Set<Method>> map, String clazzName) throws ClassNotFoundException {
        int countTestFinish = 0;
        int countTestException = 0;
        int countTestAll = 0;

        for (Method testMethod : map.get(Test.class)) {
            countTestAll++;
            System.out.println("--- start test circle for method: " + testMethod.getName());
            var instantiate = ReflectionHelper.instantiate(Class.forName(clazzName));
            for (Method method : map.get(Before.class)) {
                ReflectionHelper.callMethod(instantiate, method.getName());
            }
            try {
                ReflectionHelper.callMethod(instantiate, testMethod.getName());
                countTestFinish++;
                System.out.println("finished");
            } catch (RuntimeException e) {
                System.out.println("failed");
                countTestException++;
            }
            for (Method method : map.get(After.class)) {
                ReflectionHelper.callMethod(instantiate, method.getName());
            }
            System.out.println("--- end");
        }
        System.out.println("Всего тестов: " + countTestAll);
        System.out.println("Успешно пройдено тестов: " + countTestFinish);
        System.out.println("Не пройдено тестов: " + countTestException);
    }
}
