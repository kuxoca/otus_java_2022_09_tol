package proxy;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;


@State(Scope.Thread)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class IocTest {
    TestLoggingInterface loggingInterfaceSpeed;
    TestLoggingInterface loggingInterface;
    public static void main(String[] args) throws RunnerException {
        var opt = new OptionsBuilder().include(IocTest.class.getSimpleName()).forks(1).build();
        new Runner(opt).run();
    }
    @Setup
    public void setup() {
        loggingInterfaceSpeed = IocSpeed.createTestLoggingClass();
        loggingInterface = Ioc.createTestLoggingClass();
    }

    @Benchmark
    public void myTestSpeed() {
        for (int i = 0; i < 1000000; i++) {
            loggingInterfaceSpeed.calculation("p1");
            loggingInterfaceSpeed.calculation("p1", "p2");
            loggingInterfaceSpeed.calculation("p1", "p2", "p3");
        }
    }

    @Benchmark
    public void myTest() {
        for (int i = 0; i < 1000000; i++) {
            loggingInterface.calculation("p1");
            loggingInterface.calculation("p1", "p2");
            loggingInterface.calculation("p1", "p2", "p3");
        }
    }
}