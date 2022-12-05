package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

class IocSpeed {

    private IocSpeed() {
    }

    static TestLoggingInterface createTestLoggingClass() {
        InvocationHandler handler = new TestLoggingInvocationHandler(new TestLoggingImp());
        return (TestLoggingInterface) Proxy.newProxyInstance(TestLoggingImp.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class},
                handler);
    }

    static class TestLoggingInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface testLoggingClass;
        private final List<Method> methodList;

        TestLoggingInvocationHandler(TestLoggingImp testLoggingClass) {
            this.testLoggingClass = testLoggingClass;
            this.methodList = Arrays.stream(testLoggingClass.getClass().getMethods())
                    .filter(el -> el.isAnnotationPresent(Log.class))
                    .toList();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            for (Method methodElement : methodList) {
                if (methodElement.getName().equals(method.getName())
                        && Arrays.equals(methodElement.getParameterTypes(), method.getParameterTypes())) {
                    System.out.println("executed method:" + method.getName() + ", " +
                            "param: " + Arrays.toString(args));
                }
            }
            return method.invoke(testLoggingClass, args);
        }
    }
}
