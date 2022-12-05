package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

class Ioc {

    private Ioc() {
    }

    static TestLoggingInterface createTestLoggingClass() {
        InvocationHandler handler = new TestLoggingInvocationHandler(new TestLoggingImp());
        return (TestLoggingInterface) Proxy.newProxyInstance(TestLoggingImp.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class},
                handler);
    }

    static class TestLoggingInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface testLoggingClass;

        TestLoggingInvocationHandler(TestLoggingImp testLoggingClass) {
            this.testLoggingClass = testLoggingClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (testLoggingClass.getClass().getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(Log.class)) {
                System.out.println("executed method:" + method.getName() + ", " +
                        "param: " + Arrays.toString(args));
            }
            return method.invoke(testLoggingClass, args);
        }
    }
}
