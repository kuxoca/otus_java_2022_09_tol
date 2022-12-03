package proxy;

public class MainRun {
    public static void main(String[] args) {
        TestLoggingInterface loggingInterface = Ioc.createTestLoggingClass();
        loggingInterface.calculation("p1");
        loggingInterface.calculation("p1", "p2");
        loggingInterface.calculation("p1", "p2", "p3");

        System.out.println("-----------");

        TestLoggingInterface loggingInterfaceSpeed = IocSpeed.createTestLoggingClass();
        loggingInterfaceSpeed.calculation("p1");
        loggingInterfaceSpeed.calculation("p1", "p2");
        loggingInterfaceSpeed.calculation("p1", "p2", "p3");
    }
}
